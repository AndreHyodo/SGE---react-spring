package sge.sgeback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sge.sgeback.model.Registro_Causal;
import sge.sgeback.model.Status;
import sge.sgeback.repository.RegistroCausaisRepository;
import sge.sgeback.repository.StatusRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;


@Controller
@CrossOrigin(origins = { "*" })
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
    public @ResponseBody Iterable<String> getAllLastCausais() {
        List<String> testCells = StreamSupport.stream(statusController.getStatus().spliterator(), false)
                .map(Status::getTestCell)
                .toList();

        List<String> latestCausals = new ArrayList<>();

        for (String testCell : testCells) {
//            String causal = CausaisRepository.findTopByTestCellOrderByCausalDesc(testCell).toString();
            Registro_Causal causal = CausaisRepository.findTopByTestCellOrderByCausalDesc(testCell);
            if (causal != null) {
                String ultimo = causal.getCausal();
                latestCausals.add(ultimo);
            }
        }
        return latestCausals;
    }

//    @GetMapping
//    public List<Status> getAllTestCell() {
//        return StatusRepository.findAll();
//    }

    @GetMapping(path="/{name}")
    public @ResponseBody Registro_Causal getCausal(@PathVariable String name) {
        return CausaisRepository.findTopByTestCellOrderByCausalDesc(name);
    }

    //    @PutMapping(path="/update/{id}")
//    public ResponseEntity<Status> updateStatus(@PathVariable Integer id, @RequestBody Status status) {
//        Optional<Status> statusData = StatusRepository.findById(id);
//
//        if (statusData.isPresent()) {
//            Status _status = statusData.get();
//            _status.setSala(status.getSala());
//            _status.setStatus(status.getStatus());
//            return new ResponseEntity<>(StatusRepository.save(_status), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

//    @PostMapping
//    public ResponseEntity<Status> createStatus(@RequestBody Status status) {
//        Status newstatus = StatusRepository.save(status);
//        return ResponseEntity.status(HttpStatus.CREATED).body(newstatus);
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteSala(@PathVariable Integer id) {
//        if (StatusRepository.existsById(id)) {
//            StatusRepository.deleteById(id);
//            return ResponseEntity.ok("Sala com ID " + id + " excluída com sucesso.");
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sala com ID " + id + " não encontrada.");
//        }
//    }

}

