package sge.sgeback.Component;


import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sge.sgeback.controller.DadosEffController;
import sge.sgeback.controller.StatusController;
import sge.sgeback.repository.DadosEffRepository;

@Component
public class Scheduler {

    @Autowired
    private StatusController statusController;

    @Autowired
    private DadosEffRepository dadosEffRepository;

    @Autowired
    private DadosEffController dadosEffController;

    private static final Logger log = LoggerFactory.getLogger(Scheduler.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "59 59 5 * * *") // Ação agendada para 05:59:59 todos os dias
    @Scheduled(cron = "59 47 15 * * *") // Ação agendada para 15:47:59 todos os dias
    @Scheduled(cron = "59 08 1 * * *") // Ação agendada para 01:08:59 todos os dias
    public void scheduleAtualizarDadosEff() {
        dadosEffController.atualizarDadosEff(); // Call your existing controller method
    }

    @Scheduled(cron = "59 58 23 * * *") // Ação agendada para 23:58:59 todos os dias
    public void scheduleDailyEff() {
        dadosEffController.atualizarDailyEff();
    }


}