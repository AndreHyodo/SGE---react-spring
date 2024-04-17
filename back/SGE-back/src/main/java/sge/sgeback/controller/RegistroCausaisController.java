package sge.sgeback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.stream.StreamSupport;


import static org.springframework.web.bind.annotation.RequestMethod.POST;


@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path="/causais")
public class RegistroCausaisController {

    @Autowired
    private RegistroCausaisRepository CausaisRepository;

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



    @GetMapping(path="/AtualizaTurno")
    public @ResponseBody void AutalizarTurno() {
        List<String> testCells = StreamSupport.stream(statusController.getStatus().spliterator(), false)
                .map(Status::getTestCell)
                .toList();

//        LocalTime hora_atual = LocalTime.now();
        LocalDate Data_atual = LocalDate.now();

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

//    @PostMapping(path="/attCausal")
//    public ResponseEntity<Registro_Causal> AttCausal(@RequestBody Registro_Causal registroCausal) {
//
//
//
//        String hora_atual_string = LocalTime.now().toString();
//        LocalTime hora_atual = LocalTime.parse(hora_atual_string, DateTimeFormatter.ofPattern("HH:mm:ss"));
//
//        LocalTime zero = LocalTime.parse("00:00:00", DateTimeFormatter.ofPattern("HH:mm:ss"));
//
//        Registro_Causal newCausal = new Registro_Causal();
//
//        newCausal.setTestCell(registroCausal.getTestCell());
//        newCausal.setCode(registroCausal.getCode());
//        newCausal.setCausal(registroCausal.getCausal());
//        newCausal.setHora_inicio(Time.valueOf(hora_atual));
//        newCausal.setHora_final(zero);
//        newCausal.setData(registroCausal.getData());
//        newCausal.setObs(registroCausal.getObs());
//
//
//        Registro_Causal newRegistro = CausaisRepository.save(newCausal);
//        return ResponseEntity.status(HttpStatus.CREATED).body(newRegistro);
//    }


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

    @PostMapping(path="/insertCausal")
    public ResponseEntity<Registro_Causal> createStatus(@RequestBody Registro_Causal registroCausal) {

        Registro_Causal lastCausal = CausaisRepository.findTopByTestCellOrderByIdDesc(registroCausal.getTestCell());


        LocalTime hora_atual = LocalTime.now().minusSeconds(1);

        LocalTime zero = LocalTime.parse("00:00:00", DateTimeFormatter.ofPattern("HH:mm:ss"));
        registroCausal.setHora_final(zero); //Ajusta hora_final do novo causal para 00:00:00 para evitar valor null


        if(lastCausal.getHora_final()==null || lastCausal.getHora_final()==zero){
            lastCausal.setHora_final(hora_atual);
            ResponseEntity<Registro_Causal> response = updateCausal(lastCausal.getId());
        }

        Registro_Causal newRegistro = CausaisRepository.save(registroCausal);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRegistro);
    }


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
            String time = (String) result[0];

            Map<String, Long> causalData = new HashMap<>();
            causalData.put(time, seconds);

            causalCountsByDate.put(causal, seconds);
        }

        return ResponseEntity.ok(causalCountsByDate);
    }

}

