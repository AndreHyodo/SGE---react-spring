package sge.sgeback.controller;

import com.sun.tools.jconsole.JConsoleContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sge.sgeback.Component.enums.StatusComponentsRole;
import sge.sgeback.model.Campanas;
import sge.sgeback.model.Dados_sala;
import sge.sgeback.model.Registro_Causal;
import sge.sgeback.repository.CampanasRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path="/campanas")
public class CampanasController {

    @Autowired
    CampanasRepository campanasRepository;

    @GetMapping
    public @ResponseBody Iterable<Campanas> getAll(){
        return campanasRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Campanas> createCampana(@RequestBody Campanas campanas) {
        Campanas newCampana = campanasRepository.save(campanas);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCampana);
    }

    @PutMapping(path="/update/{nome}")
    public ResponseEntity<Campanas> updateCampana(@PathVariable String nome, @RequestBody Dados_sala dadosSala) {
        Optional<Campanas> campana = campanasRepository.findByNome(nome);


        if (campana.isPresent()) {
            Campanas _campana = campana.get();
            _campana.setLocal(dadosSala.getTestCell());
            _campana.setDataEntrada(dadosSala.getData());
            _campana.setDataSaida(null);
            _campana.setStatus(StatusComponentsRole.EM_USO);
            return new ResponseEntity<>(campanasRepository.save(_campana), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path="/update_id/{id}")
    public ResponseEntity<Campanas> updateCampana(@PathVariable Integer id, @RequestBody Campanas campana_req) {
        Optional<Campanas> campana = campanasRepository.findById(id);

        if (campana.isPresent()) {
            Campanas _campana = campana.get();
            _campana.setNome(campana_req.getNome());
            _campana.setLocal(campana_req.getLocal());
            _campana.setDataEntrada(campana_req.getDataEntrada());
            _campana.setDataRevisao(campana_req.getDataRevisao());
            _campana.setDataSaida(campana_req.getDataSaida());
            _campana.setHoraRodagem(campana_req.getHoraRodagem());
            _campana.setObs(campana_req.getObs());
            _campana.setStatus(campana_req.getStatus());
            return new ResponseEntity<>(campanasRepository.save(_campana), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
