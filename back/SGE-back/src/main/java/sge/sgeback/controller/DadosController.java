package sge.sgeback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sge.sgeback.model.Dados;
import sge.sgeback.repository.DadosRepository;


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


}
