package sge.sgeback.controller;

import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.font.GlyphJustificationInfo;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import sge.sgeback.model.Dados;
import sge.sgeback.model.Registro_Causal;
import sge.sgeback.model.Status;
import sge.sgeback.repository.CausaisRepository;
import sge.sgeback.repository.RegistroCausaisRepository;
import sge.sgeback.repository.StatusRepository;

import javax.xml.crypto.Data;


@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path="/Status")
public class StatusController {

    @Autowired
    public StatusRepository statusRepository;

    @Autowired
    public RegistroCausaisRepository causaisRepository;

    @GetMapping
    public @ResponseBody Iterable<Status> getAllStatus() {
        return statusRepository.findAll();
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Status> getStatus() {
        return statusRepository.findByIdBetween(1, 18);
    }

    @GetMapping(path="/allStopStatus")
    public @ResponseBody Iterable<Status> getStopStatus() {
        return statusRepository.findByIdBetweenAndStatusEmpty(1, 18);
    }

//    @GetMapping
//    public List<Status> getAllTestCell() {
//        return statusRepository.findAll();
//    }

    @GetMapping(path="/{id}")
    public @ResponseBody Optional<Status> getStatus(@PathVariable Integer id) {
        return statusRepository.findById(id);
    }

    @GetMapping(path="/get/{testCell}")
    public @ResponseBody Optional<Status> getStatusTestCell(@PathVariable String testCell){
        return statusRepository.findStatusByTestCell(testCell);
    }

    @PutMapping(path="/update/{id}")
    public ResponseEntity<Status> updateStatus(@PathVariable Integer id, @RequestBody Status causalStatus) {
        Optional<Status> statusData = statusRepository.findById(id);

        if (statusData.isPresent()) {
            Status _status = statusData.get();
            _status.setTestCell(causalStatus.getTestCell());
            _status.setStatus(causalStatus.getStatus());
            return new ResponseEntity<>(statusRepository.save(_status), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path="/update_Eff")
    public ResponseEntity<Void> updateStatusEff() {

        Iterable<Status> statuses = getAllStatus();

        long turnoTime=0;

        LocalTime inicioTime;
        LocalTime finalTime;
        LocalTime hora_atual = LocalTime.now();

        // Horários de início e fim dos intervalos
        LocalTime inicio1turno = LocalTime.of(6, 0, 0);
        LocalTime inicio2turno = LocalTime.of(15, 48, 0);
        LocalTime finalDia = LocalTime.of(23, 59, 59);
        LocalTime inicioDia = LocalTime.of(0, 0, 0); //inicio 3 turno = inicio dia

        if(hora_atual.isAfter(inicio1turno) && hora_atual.isBefore(inicio2turno)){
            turnoTime = Duration.between(inicio1turno, hora_atual).toSeconds(); // 1 TURNO
        } else if (hora_atual.isAfter(inicio2turno) && hora_atual.isBefore(finalDia)) {
            turnoTime = Duration.between(inicio2turno, hora_atual).toSeconds(); // 2 TURNO
        } else if(hora_atual.isAfter(inicioDia) && hora_atual.isBefore(inicio1turno)){
            turnoTime = Duration.between(inicioDia, hora_atual).toSeconds();
        }

        for (Status status : statuses) {
            Float totalTime = causaisRepository.findTempoTotalSec(status.getTestCell());
            if(totalTime == null){
                totalTime = 0.0f;
            }
            status.setEff((int) (((turnoTime - Math.round(totalTime))*100)/turnoTime));
            statusRepository.save(status);
        }

        // Return after processing all statuses
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path="/update_StopTime")
    public ResponseEntity<Void> updateTotalStop() throws ParseException {

        Iterable<Status> Stop_statuses = getStatus();

        for (Status stopStatus : Stop_statuses) {
            if (stopStatus.getStatus() == 0){
                Float totalStop = causaisRepository.findTotalStop(stopStatus.getTestCell());
                Float currentStop = causaisRepository.findCurrentStop(stopStatus.getTestCell());

                Time StopTime = TimeConverter(totalStop);
                Time CurrentStop = TimeConverter(currentStop);

                stopStatus.setParada_atual(CurrentStop);
                stopStatus.setParada_total(StopTime);
                statusRepository.save(stopStatus);
            } else if (stopStatus.getStatus()==1) {
                LocalTime midnightLocalTime = LocalTime.MIDNIGHT;

                stopStatus.setParada_atual(Time.valueOf(midnightLocalTime));
                statusRepository.save(stopStatus);
            }

        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Time TimeConverter(Float time) throws ParseException {
        String formattedTime;
        long hours=0;
        long minutes=0;
        long remainingSeconds=0;

        if(time==null){
            time=0.0f;
        }

        if(time>0){

            if(time>3600){
                hours = TimeUnit.SECONDS.toHours(time.longValue());
                minutes = TimeUnit.SECONDS.toMinutes(time.longValue()) - TimeUnit.HOURS.toMinutes(hours);
            }else{
                minutes = TimeUnit.SECONDS.toMinutes(time.longValue());
            }

            remainingSeconds = time.longValue() - TimeUnit.MINUTES.toSeconds(minutes) - TimeUnit.HOURS.toSeconds(hours);
        }else {
            remainingSeconds = TimeUnit.MINUTES.toSeconds(minutes) - TimeUnit.HOURS.toSeconds(hours);
        }

        formattedTime = String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        Date date = sdf.parse(formattedTime);

        return new Time(date.getTime());
    }


    @PutMapping(path="/updateCausal/{id}")
    public ResponseEntity<Status> updateStatusCausal(@PathVariable Integer id, @RequestBody Registro_Causal causalStatus) {
        Optional<Status> statusDataCausal = statusRepository.findById(id);

        if (statusDataCausal.isPresent()) {
            Status _status = statusDataCausal.get();
            _status.setTestCell(causalStatus.getTestCell());
            _status.setCausal(causalStatus.getCausal());
            _status.setCode(causalStatus.getCode());
            _status.setDate(causalStatus.getData());
            _status.setTime(causalStatus.getHora_inicio());
            return new ResponseEntity<>(statusRepository.save(_status), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path="/update/{id}/{status}")
    public ResponseEntity<Status> updateStatus(@PathVariable Integer id, @PathVariable int status) {
        Optional<Status> statusData = statusRepository.findById(id);

        if (statusData.isPresent()) {
            Status _status = statusData.get();
            _status.setStatus(status);
            return new ResponseEntity<>(statusRepository.save(_status), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path="/updateTestCell/{testCell}/{status}")
    public ResponseEntity<Status> updateStatusWithTestCell(@PathVariable String testCell, @PathVariable int status) {
        Optional<Status> statusData = statusRepository.findStatusByTestCell(testCell);

        if (statusData.isPresent()) {
            Status _status = statusData.get();
            _status.setStatus(status);
            return new ResponseEntity<>(statusRepository.save(_status), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path="/update/{testCell}/{motor}/{projeto}/{teste}")
    public ResponseEntity<Status> updateData(@PathVariable String testCell, @PathVariable int motor, @PathVariable String projeto, @PathVariable String teste) {
        Optional<Status> statusData = statusRepository.findStatusByTestCell(testCell);

        if (statusData.isPresent()) {
            Status _status = statusData.get();
            _status.setMotor(motor);
            _status.setProjeto(projeto);
            _status.setTeste(teste);
//            System.out.println("Atualizando DB : "+ motor + " , " + projeto + " e " + teste);
            return new ResponseEntity<>(statusRepository.save(_status), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Status> createStatus(@RequestBody Status status) {
        Status newstatus = statusRepository.save(status);
        return ResponseEntity.status(HttpStatus.CREATED).body(newstatus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTestCell(@PathVariable Integer id) {
        if (statusRepository.existsById(id)) {
            statusRepository.deleteById(id);
            return ResponseEntity.ok("Sala com ID " + id + " excluída com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sala com ID " + id + " não encontrada.");
        }
    }

    @PutMapping(path="/updateDados/{testCell}")
    public ResponseEntity<Status> updateStatusDados(@PathVariable String testCell, @RequestBody Dados dadosTeste) {
        Optional<Status> statusDataCausal = statusRepository.findStatusByTestCell(testCell);

        if (statusDataCausal.isPresent() && dadosTeste.getTeste()!=null && dadosTeste.getProjeto()!=null && dadosTeste.getMotor()!=null) {
            Status _status = statusDataCausal.get();
            _status.setMotor(dadosTeste.getMotor());
            _status.setProjeto(dadosTeste.getProjeto());
            _status.setTeste(dadosTeste.getTeste());
//            System.out.println("Atualizando dados " + _status.getTestCell() + " -> " +dadosTeste.getMotor() + " - " + dadosTeste.getProjeto() + " - " + dadosTeste.getTeste());
            return new ResponseEntity<>(statusRepository.save(_status), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}

