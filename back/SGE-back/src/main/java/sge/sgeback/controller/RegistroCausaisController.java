package sge.sgeback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sge.sgeback.model.Registro_Causal;
import sge.sgeback.model.Status;
import sge.sgeback.repository.RegistroCausaisRepository;
import sge.sgeback.repository.StatusRepository;

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




    @PostMapping(path="/insertCausal")
    public ResponseEntity<Registro_Causal> createStatus(@RequestBody Registro_Causal registroCausal) {

        Registro_Causal lastCausal = CausaisRepository.findTopByTestCellOrderByIdDesc(registroCausal.getTestCell());


        LocalTime hora_atual = LocalTime.now();

        LocalTime zero = LocalTime.parse("00:00:00", DateTimeFormatter.ofPattern("HH:mm:ss"));

        if(lastCausal.getHora_final()==null || lastCausal.getHora_final()==zero){
            lastCausal.setHora_final(hora_atual);
        }


        Registro_Causal newRegistro = CausaisRepository.save(registroCausal);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRegistro);
    }



}

