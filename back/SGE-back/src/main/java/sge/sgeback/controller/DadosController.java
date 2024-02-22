package sge.sgeback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sge.sgeback.model.Dados;
import sge.sgeback.model.Status;
import sge.sgeback.repository.DadosRepository;

import java.util.Optional;


@Controller
@CrossOrigin(origins = { "*" })
@RequestMapping(path="/dadosList")
public class DadosController {

    @Autowired
    private DadosRepository DadosRepository;

    private Dados dados;


    @GetMapping
    public @ResponseBody Iterable<Dados> getAll(){
        return DadosRepository.findAll();
    }

    @GetMapping(path="/last/{testCell}")
    public @ResponseBody Iterable<Dados> getData(@PathVariable String testCell) {
        return DadosRepository.findFirstByTestCellOrderByIdDesc(testCell);
    }

}
