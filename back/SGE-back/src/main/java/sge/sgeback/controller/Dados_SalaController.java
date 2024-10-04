package sge.sgeback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sge.sgeback.model.Dados;
import sge.sgeback.model.Dados_sala;
import sge.sgeback.model.Status;
import sge.sgeback.repository.Dados_salaRepository;

import java.util.Optional;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path="/Dados_sala")
public class Dados_SalaController {
    @Autowired
    public Dados_salaRepository dadosSalaRepository;

    @GetMapping
    public @ResponseBody Iterable<Dados_sala> getAllDados() {
        // This returns a JSON or XML with the users
        return dadosSalaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Dados_sala> createDados(@RequestBody Dados_sala dados) {
        Dados_sala newDados = dadosSalaRepository.save(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDados);
    }

    @GetMapping(path="/{testCell}")
    public @ResponseBody Iterable<Dados_sala> getDadosByTestCell(@PathVariable String testCell) {
        return dadosSalaRepository.findByTestCell(testCell);
    }

//    @GetMapping(path="/{testCell}/campana/{camp}")
//    public @ResponseBody Iterable<Dados_sala> getDadosByTestCellAndCampana(@PathVariable String testCell, @PathVariable String camp) {
//        return dadosSalaRepository.findByTestCellAAndAndCampana(testCell,camp);
//    }
//
//    @GetMapping(path="/{testCell}/eixo/{eixo}")
//    public @ResponseBody Iterable<Dados_sala> getDadosByTestCellAndEixo(@PathVariable String testCell, @PathVariable String eixo) {
//        return dadosSalaRepository.findByTestCellAAndAndEixo(testCell,eixo);
//    }



}
