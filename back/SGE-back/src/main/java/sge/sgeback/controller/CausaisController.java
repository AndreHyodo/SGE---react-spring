package sge.sgeback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sge.sgeback.model.Causais;
import sge.sgeback.model.Dados;
import sge.sgeback.repository.CausaisRepository;


@Controller
@CrossOrigin(origins = { "*" })
@RequestMapping(path="/causaisList")
public class CausaisController {

    @Autowired
    private CausaisRepository CausaisRepository;


    @GetMapping
    public @ResponseBody Iterable<Causais> getAllCausais(){
        return CausaisRepository.findAll();
    }

}
