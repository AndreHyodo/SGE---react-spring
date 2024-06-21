package sge.sgeback.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import sge.sgeback.model.Registro_Causal;
import sge.sgeback.model.Status;
import sge.sgeback.repository.StatusRepository;

import javax.xml.crypto.Data;


@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path="/Status")
public class StatusController {

    @Autowired
    public StatusRepository statusRepository;

    @GetMapping
    public @ResponseBody Iterable<Status> getAllStatus() {
        // This returns a JSON or XML with the users
        return statusRepository.findAll();
    }

//    @GetMapping(path="/SpmList")
//    public @ResponseBody List<String> getAllTestCells() {
//        // This returns a JSON or XML with the users
//        return statusRepository.findAllTestCell();
//    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Status> getStatus() {
        return statusRepository.findByIdBetween(1, 18);
    }

//    @GetMapping
//    public List<Status> getAllTestCell() {
//        return statusRepository.findAll();
//    }

    @GetMapping(path="/{id}")
    public @ResponseBody Optional<Status> getStatus(@PathVariable Integer id) {
        return statusRepository.findById(id);
    }

    @GetMapping(path="/get/{testCell}")
    public @ResponseBody Status getStatusTestCell(@PathVariable String testCell){
        return statusRepository.findStatusByTestCell(testCell);
    }

    @PutMapping(path="/update/{id}")
    public ResponseEntity<Status> updateStatus(@PathVariable Integer id, @RequestBody Status causalStatus) {
        Optional<Status> statusData = statusRepository.findById(id);

        if (statusData.isPresent()) {
            Status _status = statusData.get();
            _status.setTestCell(causalStatus.getTestCell());
            _status.setStatus(causalStatus.getStatus());
            return new ResponseEntity<>(statusRepository.save(_status), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path="/updateCausal/{id}")
    public ResponseEntity<Status> updateStatusCausal(@PathVariable Integer id, @RequestBody Registro_Causal causalStatus) {
        Optional<Status> statusDataCausal = statusRepository.findById(id);

        System.out.println("Autualizando causal da sala : " + causalStatus.getTestCell());

        if (statusDataCausal.isPresent()) {
            Status _status = statusDataCausal.get();
            _status.setTestCell(causalStatus.getTestCell());
            _status.setCausal(causalStatus.getCausal());
            _status.setCode(causalStatus.getCode());
            _status.setDate(causalStatus.getData());
            _status.setTime(causalStatus.getHora_inicio());
            return new ResponseEntity<>(statusRepository.save(_status), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path="/update/{id}/{status}")
    public ResponseEntity<Status> updateStatus(@PathVariable Integer id, @PathVariable int status) {
        Optional<Status> statusData = statusRepository.findById(id);

        if (statusData.isPresent()) {
            Status _status = statusData.get();
            _status.setStatus(status);
            return new ResponseEntity<>(statusRepository.save(_status), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Status> createStatus(@RequestBody Status status) {
        Status newstatus = statusRepository.save(status);
        return ResponseEntity.status(HttpStatus.CREATED).body(newstatus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTestCell(@PathVariable Integer id) {
        if (statusRepository.existsById(id)) {
            statusRepository.deleteById(id);
            return ResponseEntity.ok("Sala com ID " + id + " excluída com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sala com ID " + id + " não encontrada.");
        }
    }

}

