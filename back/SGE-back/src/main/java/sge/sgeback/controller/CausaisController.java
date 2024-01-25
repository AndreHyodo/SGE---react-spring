package sge.sgeback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sge.sgeback.model.Causais;
import sge.sgeback.repository.CausaisRepository;


@Controller
@CrossOrigin(origins = { "*" })
@RequestMapping(path="/causaisList")
public class CausaisController {

    @Autowired
    private CausaisRepository CausaisRepository;

    private Causais causal;


    @GetMapping
    public @ResponseBody Iterable<Causais> getAll(){
        return CausaisRepository.findAll();
    }

}
