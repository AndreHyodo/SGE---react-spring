package sge.sgeback.Component;


import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sge.sgeback.controller.DadosController;
import sge.sgeback.controller.DadosEffController;
import sge.sgeback.controller.RegistroCausaisController;
import sge.sgeback.controller.StatusController;
import sge.sgeback.repository.DadosEffRepository;

@Component
public class Scheduler {

    @Autowired
    private StatusController statusController;

    @Autowired
    private DadosController dadosController;

    @Autowired
    private RegistroCausaisController causaisController;

    @Autowired
    private DadosEffRepository dadosEffRepository;

    @Autowired
    private DadosEffController dadosEffController;

    private static final Logger log = LoggerFactory.getLogger(Scheduler.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "59 59 5 * * *") // Ação agendada para 05:59:59 todos os dias
    @Scheduled(cron = "30 47 15 * * *") // Ação agendada para 15:47:59 todos os dias
    @Scheduled(cron = "00 59 23 * * *") // Ação agendada para 23:59:57 todos os dias
    public void scheduleAtualizarDadosEff() {
        dadosEffController.atualizarDadosEff(); // Call your existing controller method
    }

    @Scheduled(cron = "50 59 23 * * *") // Ação agendada para 23:58:59 todos os dias
    public void scheduleDailyEff() {
        dadosEffController.atualizarDailyEff();
    }

    @Scheduled(cron = "59 59 5 * * *") // Ação agendada para 05:59:59 todos os dias
    @Scheduled(cron = "59 47 15 * * *") // Ação agendada para 15:47:59 todos os dias
    @Scheduled(cron = "59 59 23 * * *") // Ação agendada para 23:59:57 todos os dias
    @Scheduled(cron = "59 08 01 * * *") // Ação agendada para 01:08:59 todos os dias
    @Scheduled(cron = "00 04 17 * * *") // Ação agendada para 01:08:59 todos os dias
    public void scheduleAtualizarCausaisTurno() {
        causaisController.AutalizarTurno(); // Call your existing controller method
    }

    @Scheduled(cron = "0 * * * * *") // Ação agendada para uma vez a cada minuto
    public void scheduleAtualizarEff(){
        statusController.updateStatusEff();
    }

    @Scheduled(cron = "*/1 * * * * ?") // Ação agendada para uma vez a cada segundo
    public void scheduleAtualizarStopTime() throws ParseException {
        statusController.updateTotalStop();
    }

    @Scheduled(cron = "0 */10 * * * ?") // Ação agendada para uma vez a cada 5 minutos
    public void scheduleAtualizaDados() throws IOException {
        dadosController.atualizaExcelDados();
    }


}