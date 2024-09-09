package sge.sgeback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sge.sgeback.model.Dados_Eff;
import sge.sgeback.model.Registro_Causal;
import sge.sgeback.model.Status;
import sge.sgeback.repository.RegistroCausaisRepository;
import sge.sgeback.repository.StatusRepository;

import javax.print.attribute.standard.MediaSize;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.StreamSupport;


import static org.springframework.web.bind.annotation.RequestMethod.POST;


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

        for (String testCell : testCells) {
            Registro_Causal causal = CausaisRepository.findTopByTestCellOrderByIdDesc(testCell);
            if (causal != null) {
                latestCausals.add(causal);
            }
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
                newCausal.setData(causal.getData());
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
            String causal = (String) result[2];
            Long seconds = ((BigDecimal) result[1]).longValue();
            Time time_original = (Time) result[0];
            String time = time_original.toString();

            Map<String, Long> causalData = new HashMap<>();
            causalData.put(time, seconds);

            causalCountsByDate.put(causal, seconds);
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



}

