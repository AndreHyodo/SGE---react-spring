package sge.sgeback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sge.sgeback.model.Dados_Eff;
import sge.sgeback.model.Registro_Causal;
import sge.sgeback.model.Status;
import sge.sgeback.repository.DadosEffRepository;
import sge.sgeback.repository.DadosRepository;
import sge.sgeback.repository.StatusRepository;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;


@Controller
@CrossOrigin(origins = { "*" })
@RequestMapping(path="/dadosEff")
public class DadosEffController {

    @Autowired
    private StatusController statusController;

    @Autowired
    private DadosEffRepository dadosEffRepository;

    @Autowired
    private StatusRepository statusRepository;

    @GetMapping(path="/top10/{name}")
    public @ResponseBody Iterable<Dados_Eff> findTop10ByTestCellOrderByIdDesc(@PathVariable String name) {
        return dadosEffRepository.findTop10ByTestCellOrderByIdAsc(name);
    }
    @GetMapping(path="/top10/{turno}/{name}")
    public @ResponseBody Iterable<Dados_Eff> findTop10ByTestCellOrderByIdDesc(@PathVariable int turno, @PathVariable String name) {
        return dadosEffRepository.findTop10ByTestCellAndTurnoOrderByIdDesc(name,turno);
    }


//    @PostMapping(path="/atualizaTurno")
//    public void createStatus(){
//        Iterable<Status> StatusAtual = statusRepository.findByIdBetween(1, 18);
//        LocalTime atual = LocalTime.now();
//        Time sqlTime = Time.valueOf(atual);
//
//        for (Status status_atual : StatusAtual) {
//            Dados_Eff dadosEff = new Dados_Eff();
//            dadosEff.setTestCell(status_atual.getTestCell());
//            dadosEff.setEff(status_atual.getEff());
//            dadosEff.setHora(sqlTime); // Hora atual
//            dadosEff.setDate(status_atual.getDate());
//
//            DadosEffRepository.save(dadosEff);
//        }
//    }

    @PostMapping(path="/atualizaDadosEff")
    public void atualizarDadosEff() {
//        statusController = new StatusController();
        Iterable<Status> statuses = statusController.getStatus();
        LocalTime agora = LocalTime.now();

        int turno;

        if ((agora.isAfter(LocalTime.of(6, 0)))&&(agora.isBefore(LocalTime.of(15, 48)))) {
            turno = 1;
        } else if ((agora.isAfter(LocalTime.of(15, 48)))&&(agora.isBefore(LocalTime.of(23, 59)))) {
            turno = 2;
        } else if ((agora.isAfter(LocalTime.of(0, 0)))&&(agora.isBefore(LocalTime.of(1, 9)))){
            turno = 2;
        } else {
            turno = 3;
        }

        for (Status status : statuses) {
            Dados_Eff dadosEff = new Dados_Eff();
            dadosEff.setTestCell(status.getTestCell());
            dadosEff.setEff(status.getEff());
            dadosEff.setHora(status.getTime()); // Hora atual
            dadosEff.setDate(status.getDate());
            dadosEff.setTurno(turno);

           createDadosEff(dadosEff);
        }
    }

    @PostMapping(path="/DailyEff")
    public void atualizarDailyEff() {
        Iterable<Status> status_daily = statusController.getStatus();

        for (Status status : status_daily) {
            Dados_Eff dadosEff = new Dados_Eff();
            dadosEff.setTestCell(status.getTestCell());
            dadosEff.setEff(dadosEffRepository.findByTestCellAndData(status.getTestCell()));
            dadosEff.setHora(status.getTime()); // Hora atual
            dadosEff.setDate(status.getDate());
            dadosEff.setTurno(0);

            createDadosEff(dadosEff);
        }
    }

    @PostMapping
    public ResponseEntity<Dados_Eff> createDadosEff(@RequestBody Dados_Eff dados) {
        Dados_Eff newDados = dadosEffRepository.save(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDados);
    }

}