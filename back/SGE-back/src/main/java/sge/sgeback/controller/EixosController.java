package sge.sgeback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sge.sgeback.Component.enums.StatusComponentsRole;
import sge.sgeback.model.Eixos;
import sge.sgeback.model.Dados_sala;
import sge.sgeback.repository.EixosRepository;

import java.util.Optional;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path="/eixos")
public class EixosController {

    @Autowired
    EixosRepository eixosRepository;

    @GetMapping
    public @ResponseBody Iterable<Eixos> getAll(){
        return eixosRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Eixos> createCampana(@RequestBody Eixos eixos) {
        Eixos newCampana = eixosRepository.save(eixos);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCampana);
    }

    @PutMapping(path="/update/{nome}")
    public ResponseEntity<Eixos> updateCampana(@PathVariable String nome, @RequestBody Dados_sala dadosSala) {
        Optional<Eixos> eixo = eixosRepository.findByName(nome);


        if (eixo.isPresent()) {
            Eixos _eixo = eixo.get();
            _eixo.setLocal(dadosSala.getTestCell());
            _eixo.setDataEntrada(dadosSala.getData());
            _eixo.setDataSaida(null);
            _eixo.setStatus(StatusComponentsRole.EM_USO);
            return new ResponseEntity<>(eixosRepository.save(_eixo), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path="/update_id/{id}")
    public ResponseEntity<Eixos> updateCampana(@PathVariable Integer id, @RequestBody Eixos eixo_req) {
        Optional<Eixos> eixo = eixosRepository.findById(id);

        if (eixo.isPresent()) {
            Eixos _eixo = eixo.get();
            _eixo.setName(eixo_req.getName());
            _eixo.setLocal(eixo_req.getLocal());
            _eixo.setDataEntrada(eixo_req.getDataEntrada());
            _eixo.setDataRevisao(eixo_req.getDataRevisao());
            _eixo.setDataSaida(eixo_req.getDataSaida());
            _eixo.setHoraRodagem(eixo_req.getHoraRodagem());
            _eixo.setObs(eixo_req.getObs());
            _eixo.setStatus(eixo_req.getStatus());
            return new ResponseEntity<>(eixosRepository.save(_eixo), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
