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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


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
    public @ResponseBody Iterable<Registro_Causal> getLastCausalTestCell(@PathVariable String name) {
        return CausaisRepository.findTop3ByTestCellOrderByIdDesc(name);
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

    @PostMapping(path="/insertCausal")
    public ResponseEntity<Registro_Causal> createStatus(@RequestBody Registro_Causal registroCausal) {
        Registro_Causal newRegistro = CausaisRepository.save(registroCausal);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRegistro);
    }

//    @RequestMapping(method = POST)
//    public String processForm(Registro_Causal causal, Model model) {
//
//        model.addAttribute("firstname", causal.getCausal());
//        model.addAttribute("lastname", causal.getCode());
//
//
//
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

