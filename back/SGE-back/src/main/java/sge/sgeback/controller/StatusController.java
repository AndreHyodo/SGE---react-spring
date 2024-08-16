package sge.sgeback.controller;

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
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
        // This returns a JSON or XML with the users
        return statusRepository.findAll();
    }

//    @GetMapping(path="/SpmList")
//    public @ResponseBody List<String> getAllTestCells() {
//        // This returns a JSON or XML with the users
//        return statusRepository.findAllTestCell();
//    }

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
        Integer turnoTime = 35280;

        for (Status status : statuses) {
            Float totalTime = causaisRepository.findTempoTotalSec(status.getTestCell());
            if(totalTime == null){
                totalTime = 0.0f;
            }
            status.setEff(((turnoTime - Math.round(totalTime))*100)/turnoTime);
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

        System.out.println("Autualizando causal da sala : " + causalStatus.getTestCell());

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




}

