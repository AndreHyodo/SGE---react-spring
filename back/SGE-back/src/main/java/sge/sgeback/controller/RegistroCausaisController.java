package sge.sgeback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sge.sgeback.model.Registro_Causal;
import sge.sgeback.model.Status;
import sge.sgeback.projection.SearchDataProjection;
import sge.sgeback.repository.RegistroCausaisRepository;
import sge.sgeback.repository.StatusRepository;
import sge.sgeback.service.RegistroCausais_service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.StreamSupport;


@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path="/causais")
public class RegistroCausaisController {

    @Autowired
    private RegistroCausaisRepository CausaisRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private StatusController statusController;

    @Autowired
    private RegistroCausais_service registroCausaisService;

    private Registro_Causal causal;

    private Status status;

    @GetMapping
    public @ResponseBody Iterable<Registro_Causal> getAll(){
        return CausaisRepository.findAll();
    }

    @GetMapping(path="/LastList")
    public @ResponseBody Iterable<Registro_Causal> getAllLastCausais() {
        List<String> testCells = StreamSupport.stream(statusController.getStatus().spliterator(), false)
                .map(Status::getTestCell)
                .toList();

        List<Registro_Causal> latestCausals = new ArrayList<>();

        int aux = 1;

        for (String testCell : testCells) {
            Registro_Causal causal = CausaisRepository.findTopByTestCellOrderByIdDesc(testCell);
            if (causal != null) {
                causal.setId(aux);
                latestCausals.add(causal);
            }
            aux++;
        }
        return latestCausals;
    }


    @GetMapping(path="/{name}")
    public @ResponseBody Registro_Causal getCausal(@PathVariable String name) {
        return CausaisRepository.findTopByTestCellOrderByIdDesc(name);
    }

    @GetMapping(path="/top3/{name}")
    public @ResponseBody Iterable<Registro_Causal> getLast3CausalTestCell(@PathVariable String name) {
        return CausaisRepository.findTop3ByTestCellOrderByIdDesc(name);
    }

//    @GetMapping(path="/lastAll")
//    public @ResponseBody Iterable<Registro_Causal> getLastCausalAll(@PathVariable String name) {
//        return CausaisRepository.findTop3ByTestCellOrderByIdDesc(name);
//    }

    @GetMapping(path="/last/{name}")
    public @ResponseBody Registro_Causal getLastCausalTestCell(@PathVariable String name) {
        return CausaisRepository.findTopByTestCellOrderByIdDesc(name);
    }


    @GetMapping(path="/lastNotWaiting/{name}")
    public @ResponseBody Registro_Causal getLastRealCausal(@PathVariable String name) {
        return CausaisRepository.findTopByTestCellAndCausalNotEmpty(name);
    }



    @GetMapping(path="/AtualizaTurno")
    public @ResponseBody void AutalizarTurno() {
        List<String> testCells = StreamSupport.stream(statusController.getStatus().spliterator(), false)
                .map(Status::getTestCell)
                .toList();

//        LocalTime hora_atual = LocalTime.now();
        LocalDate Data_atual = LocalDate.now();
        //default time zone
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date date = Date.from(Data_atual.atStartOfDay(defaultZoneId).toInstant());

        LocalTime hora_atual = LocalTime.now().plusSeconds(1);

        LocalTime zero = LocalTime.parse("00:00:00", DateTimeFormatter.ofPattern("HH:mm:ss"));

        LocalTime ajusteZero = LocalTime.parse("00:00:30", DateTimeFormatter.ofPattern("HH:mm:ss"));

        for (String testCell : testCells) {
            Registro_Causal causal = CausaisRepository.findTopByTestCellOrderByIdDesc(testCell);
            if (causal.getHora_final() == LocalTime.of(0,0,0)) {
                System.out.println("Ajustando causal sala: "+ causal.getTestCell() + "sendo hora_final = " + causal.getHora_final());
                Registro_Causal newCausal = new Registro_Causal();

                newCausal.setTestCell(causal.getTestCell());
                newCausal.setCode(causal.getCode());
                newCausal.setCausal(causal.getCausal());
                newCausal.setHora_inicio(Time.valueOf(hora_atual));
                newCausal.setHora_final(zero);
                if(hora_atual.isAfter(zero) && hora_atual.isBefore(ajusteZero)){
                    Date data = causal.getData();

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(data);
                    calendar.add(Calendar.DAY_OF_MONTH, 1);

                    Date dataAtt = calendar.getTime();

                    java.sql.Date DataAttSql = new java.sql.Date(dataAtt.getTime());

                    newCausal.setData(DataAttSql);
                }else{
                    newCausal.setData(causal.getData());
                }
                newCausal.setObs(causal.getObs());

                createStatus(newCausal);
            }
        }
    }


    @PutMapping(path="/updateHour/{id}")
    public ResponseEntity<Registro_Causal> updateCausal(@PathVariable Integer id) {
        Optional<Registro_Causal> Causal = CausaisRepository.findById(id);

        LocalTime hora_atual = LocalTime.now().minusSeconds(1);


        if (Causal.isPresent()) {
            Registro_Causal _causal = Causal.get();
            _causal.setHora_final(hora_atual);
            return new ResponseEntity<>(CausaisRepository.save(_causal), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping(path="/updateCausal")
    public ResponseEntity<Registro_Causal> updateAguardandoCausal(@PathVariable Integer id,@RequestBody Registro_Causal registroCausal) {
        Optional<Registro_Causal> Causal = CausaisRepository.findById(id);

        LocalTime hora_atualLocalTime = LocalTime.now().minusSeconds(1);

        Time hora_atual = Time.valueOf(hora_atualLocalTime);


        if (Causal.isPresent()) {
            Registro_Causal _causal = Causal.get();
            _causal.setCode(registroCausal.getCode());
            _causal.setCausal(registroCausal.getCausal());
//            _causal.setHora_inicio(hora_atual);
            _causal.setObs(registroCausal.getObs());
            return new ResponseEntity<>(CausaisRepository.save(_causal), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path="/updateCausalSalaRodando/{testCell}")
    public ResponseEntity<Registro_Causal> updateAguardandoCausal(@PathVariable String testCell) {

        Registro_Causal lastCausal = CausaisRepository.findTopByTestCellOrderByIdDesc(testCell);

        Optional<Status> status_ = statusRepository.findStatusByTestCell(testCell);

        LocalTime hora_final = LocalTime.now();

        LocalTime zero = LocalTime.parse("00:00:00", DateTimeFormatter.ofPattern("HH:mm:ss"));

        Float codeAusencia = 2.1F;

        Registro_Causal attCausal = new Registro_Causal();

        if(lastCausal.getCausal().equals("Aguardando Causal")){
            lastCausal.setCausal("Ausência de Operador");
            lastCausal.setCode(codeAusencia);
            lastCausal.setHora_final(hora_final);
            lastCausal.setObs("Operador não registrou causal");
            statusController.updateStatusCausal(status_.get().getId(),lastCausal);
            return new ResponseEntity<>(CausaisRepository.save(lastCausal), HttpStatus.OK);
        }else{
            lastCausal.setHora_final(hora_final);
            statusController.updateStatusCausal(status_.get().getId(),lastCausal);
            return new ResponseEntity<>(CausaisRepository.save(lastCausal), HttpStatus.OK);
        }
    }

    @PostMapping(path="/insertAguardando/{testCell}")
    public ResponseEntity<Registro_Causal> createAguardandoCausal(@PathVariable String testCell) {

        LocalTime hora_atual = LocalTime.now();

        LocalTime zero = LocalTime.parse("00:00:00", DateTimeFormatter.ofPattern("HH:mm:ss"));

        LocalDate Data_atual = LocalDate.now();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        java.util.Date date = Date.from(Data_atual.atStartOfDay(defaultZoneId).toInstant());
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        Optional<Status> status_ = statusRepository.findStatusByTestCell(testCell);

        Registro_Causal registroCausal = new Registro_Causal();

        registroCausal.setCausal("Aguardando Causal");
        registroCausal.setCode(null);
        registroCausal.setHora_inicio(Time.valueOf(hora_atual));
        registroCausal.setTestCell(testCell);
        registroCausal.setHora_final(zero);
        registroCausal.setData(sqlDate);
        Registro_Causal newRegistro = CausaisRepository.save(registroCausal);
        statusController.updateStatusCausal(status_.get().getId(),registroCausal);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRegistro);

    }



    @PostMapping(path="/insertCausal")
    public ResponseEntity<Registro_Causal> createStatus(@RequestBody Registro_Causal registroCausal) {

        Registro_Causal lastCausal = CausaisRepository.findTopByTestCellOrderByIdDesc(registroCausal.getTestCell());


        LocalTime hora_final = LocalTime.now().minusSeconds(1);
        LocalTime hora_atual = LocalTime.now().plusSeconds(1);

        LocalTime zero = LocalTime.parse("00:00:00", DateTimeFormatter.ofPattern("HH:mm:ss"));
        registroCausal.setHora_final(zero); //Ajusta hora_final do novo causal para 00:00:00 para evitar valor null

        if(lastCausal.getCausal().equals("Aguardando causal")){
            Optional<Status> status_ = statusRepository.findStatusByTestCell(registroCausal.getTestCell());
            System.out.println("Procurando status para : " + status_.get().getTestCell());
            statusController.updateStatusCausal(status_.get().getId(),registroCausal);
            return updateAguardandoCausal(lastCausal.getId(),registroCausal);
        }else{
            if(lastCausal.getHora_final()==null || lastCausal.getHora_final()==zero){
                lastCausal.setHora_final(hora_final);
                ResponseEntity<Registro_Causal> response = updateCausal(lastCausal.getId());
            }

            registroCausal.setHora_inicio(Time.valueOf(hora_atual));
            Registro_Causal newRegistro = CausaisRepository.save(registroCausal);
            statusController.updateStatusCausal(registroCausal.getId(),registroCausal);
            return ResponseEntity.status(HttpStatus.CREATED).body(newRegistro);
        }

    }



//    @PutMapping(path="/newCausalUpdate")
//    public ResponseEntity<Registro_Causal> newCausalUpdate(@RequestBody Registro_Causal registroCausal) {
//
//        Registro_Causal lastCausal = CausaisRepository.findTopByTestCellOrderByIdDesc(registroCausal.getTestCell());
//
//
//        LocalTime hora_atual = LocalTime.now().minusSeconds(1);
//
//        LocalTime zero = LocalTime.parse("00:00:00", DateTimeFormatter.ofPattern("HH:mm:ss"));
//        registroCausal.setHora_final(zero); //Ajusta hora_final do novo causal para 00:00:00 para evitar valor null
//
//
//        if(lastCausal.getHora_final()==null || lastCausal.getHora_final()==zero){
//            lastCausal.setHora_final(hora_atual);
//            ResponseEntity<Registro_Causal> response = updateCausal(lastCausal.getId());
//        }
//
//        Registro_Causal newRegistro = CausaisRepository.save(registroCausal);
//        statusController.updateStatusCausal(registroCausal.getId(),registroCausal);
//        return ResponseEntity.status(HttpStatus.CREATED).body(newRegistro);
//    }


    @GetMapping(path="/count/{name}")
    public ResponseEntity<Map<String, Long>> findCausalCount(@PathVariable String name) {
        List<Object[]> results = CausaisRepository.findCausalCountByTestCellOrderBycontagem(name);

        Map<String, Long> causalCounts = new HashMap<>();
        for (Object[] result : results) {
            String causal = (String) result[1];
            Long contagem = (Long) result[0];
            causalCounts.put(causal, contagem);
        }

        return ResponseEntity.ok(causalCounts);
    }

    @GetMapping(path="/count/{name}/{date}")
    public ResponseEntity<Map<String, Long>> findCausaisByDate(@PathVariable String name,@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {

        List<Object[]> results = CausaisRepository.findCausalCountByTestCellDateOrderBycontagem(name,date);

        Map<String, Long> causalCountsByDate = new HashMap<>();
        for (Object[] result : results) {
            String causal = (String) result[1];
            Long contagem = (Long) result[0];
            causalCountsByDate.put(causal, contagem);
        }

        return ResponseEntity.ok(causalCountsByDate);
    }

    @GetMapping(path="/countHour/{name}/{date}")
    public ResponseEntity<Map<String, Long>> findHourCausaisByDate(@PathVariable String name, @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {

        List<Object[]> results = CausaisRepository.findCausalSumCountByTestCellOrderBycontagem(name, date);

        Map<String, Long> causalCountsByDate = new HashMap<>();
        for (Object[] result : results) {
            String causal = (String) result[0];
            Long seconds = ((BigDecimal) result[1]).longValue();
            Time time_original = (Time) result[2];
            String time = time_original.toString();

            Map<String, Long> causalData = new HashMap<>();
            causalData.put(causal, seconds);

            causalCountsByDate.put(causal, seconds);
        }

        return ResponseEntity.ok(causalCountsByDate);
    }

    @GetMapping(path="/countHourFormatted/{name}/{date}")
    public ResponseEntity<Map<String, String>> findHourFormattedCausaisByDate(@PathVariable String name, @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {

        List<Object[]> results = CausaisRepository.findCausalSumCountByTestCellOrderBycontagem(name, date);

        Map<String, String> causalCountsByDate = new HashMap<>();
        for (Object[] result : results) {
            String causal = (String) result[0];
            Long seconds = ((BigDecimal) result[1]).longValue();
            Time time_original = (Time) result[2];
            String time = time_original.toString();

            Map<String, String> causalData = new HashMap<>();
            causalData.put(causal, time);

            causalCountsByDate.put(causal, time);
        }

        return ResponseEntity.ok(causalCountsByDate);
    }

//    @GetMapping(path="/Eff/{testCell}")
//    public ResponseEntity<Float> findTotalTimeInSecs(@PathVariable String testCell) {
//
//        return ResponseEntity.ok(CausaisRepository.findTempoTotalSec(testCell));
//    }

    @GetMapping(path="/Eff/{testCell}")
    public ResponseEntity<TotalTimeResponse> findTotalTimeInSecs(@PathVariable String testCell) {
        Float totalTime = CausaisRepository.findTempoTotalSec(testCell);
        TotalTimeResponse response = new TotalTimeResponse(totalTime);
        return ResponseEntity.ok(response);
    }

    static class TotalTimeResponse {
        private Float totalTime;

        public TotalTimeResponse(Float totalTime) {
            this.totalTime = totalTime;
        }

        public Float getTotalTime() {
            return totalTime;
        }
    }


    @GetMapping(path="/TotalStop/{testCell}")
    public ResponseEntity<String> findTotalStop(@PathVariable String testCell) {
        Float totalStop = CausaisRepository.findTotalStop(testCell);
        String formattedTime;
        long hours=0;
        long minutes=0;
        long remainingSeconds=0;

        if(totalStop>0){

            if(totalStop>3600){
                hours = TimeUnit.SECONDS.toHours(totalStop.longValue());
                minutes = TimeUnit.SECONDS.toMinutes(totalStop.longValue()) - TimeUnit.HOURS.toMinutes(hours);
            }else{
                minutes = TimeUnit.SECONDS.toMinutes(totalStop.longValue());
            }

        }

        remainingSeconds = totalStop.longValue() - TimeUnit.MINUTES.toSeconds(minutes) - TimeUnit.HOURS.toSeconds(hours);

        formattedTime = String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);

        return ResponseEntity.ok(formattedTime);
    }


    @GetMapping(path="/CurrentStop/{testCell}")
    public ResponseEntity<String> findCurrentStop(@PathVariable String testCell) {
        Float CurrentStop = CausaisRepository.findCurrentStop(testCell);
        String formattedTime;
        long hours=0;
        long minutes=0;
        long remainingSeconds=0;

        if(CurrentStop>0 && CurrentStop!=null){

            if(CurrentStop>3600){
                hours = TimeUnit.SECONDS.toHours(CurrentStop.longValue());
                minutes = TimeUnit.SECONDS.toMinutes(CurrentStop.longValue()) - TimeUnit.HOURS.toMinutes(hours);
            }else{
                minutes = TimeUnit.SECONDS.toMinutes(CurrentStop.longValue());
            }
            remainingSeconds = CurrentStop.longValue() - TimeUnit.MINUTES.toSeconds(minutes) - TimeUnit.HOURS.toSeconds(hours);
        }else{
            remainingSeconds = TimeUnit.MINUTES.toSeconds(minutes) - TimeUnit.HOURS.toSeconds(hours);
        }

        formattedTime = String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);

        return ResponseEntity.ok(formattedTime);
    }


    @GetMapping(path="/causaisMonth/{code}/{mes}")
    public ResponseEntity<Map<String, Long>> findCausalCount(@PathVariable Float code,@PathVariable String mes) {
        return ResponseEntity.ok(registroCausaisService.findCausalCount(code, mes));
    }

    @GetMapping(path="/causaisMonthTurnos/{code}/{mes}")
    public ResponseEntity<List<SearchDataProjection>> findCausalCountAllShifts(@PathVariable Float code, @PathVariable String mes) {
        return ResponseEntity.ok(registroCausaisService.findCausalCountAllShifts(code,mes));
    }


}

