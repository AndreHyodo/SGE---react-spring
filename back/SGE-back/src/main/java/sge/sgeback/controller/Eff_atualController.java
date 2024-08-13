package sge.sgeback.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sge.sgeback.model.Eff_atual;
import sge.sgeback.repository.Eff_atualRepository;

import java.util.Optional;

@Controller
@CrossOrigin(origins = { "*" })
@RequestMapping(path="/EffAtual")
public class Eff_atualController {

    Eff_atualRepository effAtualRepository;

    @GetMapping(path="/effTeste/{name}")
    public double GetEffTeste(@PathVariable String name){
        Eff_atual dto = effAtualRepository.findTempoTotalSecByRoomName(name);
        return dto.getTempoTotalSec();
    }

}
