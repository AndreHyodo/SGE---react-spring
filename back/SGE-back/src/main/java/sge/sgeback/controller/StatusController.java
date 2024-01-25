package sge.sgeback.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

import sge.sgeback.model.Status;
import sge.sgeback.repository.StatusRepository;


@Controller
@CrossOrigin(origins = { "*" })
@RequestMapping(path="/Status")
public class StatusController {

    @Autowired
    private StatusRepository StatusRepository;

    @GetMapping
    public @ResponseBody Iterable<Status> getAllStatus() {
        // This returns a JSON or XML with the users
        return StatusRepository.findAll();
    }

//    @GetMapping(path="/SpmList")
//    public @ResponseBody List<String> getAllTestCells() {
//        // This returns a JSON or XML with the users
//        return StatusRepository.findAllTestCell();
//    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Status> getStatus() {
        return StatusRepository.findByIdBetween(1, 18);
    }

//    @GetMapping
//    public List<Status> getAllTestCell() {
//        return StatusRepository.findAll();
//    }

    @GetMapping(path="/{id}")
    public @ResponseBody Optional<Status> getStatus(@PathVariable Integer id) {
        return StatusRepository.findById(id);
    }

    @PutMapping(path="/update/{id}")
    public ResponseEntity<Status> updateStatus(@PathVariable Integer id, @RequestBody Status status) {
        Optional<Status> statusData = StatusRepository.findById(id);

        if (statusData.isPresent()) {
            Status _status = statusData.get();
            _status.setTestCell(status.getTestCell());
            _status.setStatus(status.getStatus());
            return new ResponseEntity<>(StatusRepository.save(_status), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Status> createStatus(@RequestBody Status status) {
        Status newstatus = StatusRepository.save(status);
        return ResponseEntity.status(HttpStatus.CREATED).body(newstatus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTestCell(@PathVariable Integer id) {
        if (StatusRepository.existsById(id)) {
            StatusRepository.deleteById(id);
            return ResponseEntity.ok("Sala com ID " + id + " excluída com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sala com ID " + id + " não encontrada.");
        }
    }

}

