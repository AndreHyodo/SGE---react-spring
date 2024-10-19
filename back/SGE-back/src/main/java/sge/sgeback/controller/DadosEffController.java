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

import javax.swing.plaf.PanelUI;
import java.math.BigDecimal;
import java.security.PublicKey;
import java.sql.Time;
import java.time.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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
        return dadosEffRepository.findTop10ByTestCellOrderByIdDesc(name);
    }

    @GetMapping(path="/effTeste/{name}")
    public int GetEffTeste(@PathVariable String name){
        return dadosEffRepository.findByTestCellAndData(name);
    }

    @GetMapping(path="/top10/{turno}/{name}")
    public @ResponseBody List<Dados_Eff> findTop10ByTestCellOrderByIdDesc(@PathVariable int turno, @PathVariable String name) {
        // Fetch data using the existing logic
        List<Dados_Eff> data = dadosEffRepository.findTop10ByTestCellAndTurnoOrderByIdDesc(name, turno);

        // Invert the order of the data
        Collections.reverse(data);

        return data;
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

        LocalDate today = LocalDate.now();
        LocalDate yesterday = LocalDate.now().minusDays(1);

        Date hoje = java.sql.Date.valueOf(LocalDate.now());

        Date ontem = java.sql.Date.valueOf(yesterday);

        DayOfWeek dayOfWeek = today.getDayOfWeek();


        int turno;

        if ((agora.isAfter(LocalTime.of(6, 0)))&&(agora.isBefore(LocalTime.of(15, 47,59)))) {
            turno = 1;
        } else if ((agora.isAfter(LocalTime.of(15, 48)))&&(agora.isBefore(LocalTime.of(23, 59, 59)))) {
            turno = 2;
        } else if ((agora.isAfter(LocalTime.of(0, 0)))&&(agora.isBefore(LocalTime.of(5, 59,59)))){
            turno = 3;
        }else{
            turno = 100;
        }


        for (Status status : statuses) {
            if(turno == 3 || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY){
                if(status.getType().equals("dur")){
                    Dados_Eff dadosEff = new Dados_Eff();
                    dadosEff.setTestCell(status.getTestCell());
                    dadosEff.setEff(status.getEff());
                    dadosEff.setHora(status.getTime()); // Hora atual
                    if(turno == 3){
                        dadosEff.setDate(ontem);
                    }else{
                        dadosEff.setDate(hoje);
                    }
                    dadosEff.setTurno(turno);

                    createDadosEff(dadosEff);
                }
            }else{
                Dados_Eff dadosEff = new Dados_Eff();
                dadosEff.setTestCell(status.getTestCell());
                dadosEff.setEff(status.getEff());
                dadosEff.setHora(status.getTime()); // Hora atual
                if(turno == 2){
                    dadosEff.setDate(ontem);
                }else{
                    dadosEff.setDate(hoje);
                }
                dadosEff.setTurno(turno);

                createDadosEff(dadosEff);
            }
        }
    }

    @PostMapping(path="/DailyEff")
    public void atualizarDailyEff() {
        Iterable<Status> status_daily = statusController.getStatus();

        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();


        for (Status status : status_daily) {
            if(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY){
                if(status.getType().equals("dur")){
                    Dados_Eff dadosEff = new Dados_Eff();
                    dadosEff.setTestCell(status.getTestCell());
                    dadosEff.setEff(dadosEffRepository.findByTestCellAndData(status.getTestCell()));
                    dadosEff.setHora(status.getTime()); // Hora atual
                    dadosEff.setDate(status.getDate());
                    dadosEff.setTurno(0);

                    System.out.println(dadosEff.getTestCell() + " --- " + dadosEff.getEff() + " --- " + dadosEff.getHora() + " --- " + dadosEff.getDate() + " --- " + dadosEff.getTurno());
                    createDadosEff(dadosEff);
                }
            }else{
                Dados_Eff dadosEff = new Dados_Eff();
                dadosEff.setTestCell(status.getTestCell());
                dadosEff.setEff(dadosEffRepository.findByTestCellAndData(status.getTestCell()));
                dadosEff.setHora(status.getTime()); // Hora atual
                dadosEff.setDate(status.getDate());
                dadosEff.setTurno(0);

                System.out.println(dadosEff.getTestCell() + " --- " + dadosEff.getEff() + " --- " + dadosEff.getHora() + " --- " + dadosEff.getDate() + " --- " + dadosEff.getTurno());
                createDadosEff(dadosEff);
            }
        }
    }


    @PostMapping
    public ResponseEntity<Dados_Eff> createDadosEff(@RequestBody Dados_Eff dados) {
//        System.out.println("salvando: " + dados.getTestCell() + " --- " + dados.getEff() + " --- " + dados.getHora() + " --- " + dados.getDate() + " --- " + dados.getTurno());
        Dados_Eff newDados = dadosEffRepository.save(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDados);
    }

}