package sge.sgeback.Component;


import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;

import Automation.BDaq.DeviceInformation;
import Automation.BDaq.DeviceTreeNode;
import Automation.BDaq.ErrorCode;
import Automation.BDaq.InstantDiCtrl;
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
import sge.sgeback.service.StatusService;

@Component
public class Scheduler {

    @Autowired
    private StatusController statusController;

    private StatusService statusService;

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

    @Scheduled(cron = "0 59 5 * * *") // Ação agendada para 05:59:00 todos os dias - Marca Eff do 3º turno
    @Scheduled(cron = "0 47 15 * * *") // Ação agendada para 15:47:00 todos os dias - Marca Eff do 1º turno
    @Scheduled(cron = "0 59 23 * * *") // Ação agendada para 23:59:00 todos os dias - Marca Eff do 2º turno
    public void scheduleAtualizarDadosEff() {
        dadosEffController.atualizarDadosEff(); // Call your existing controller method
    }

    @Scheduled(cron = "50 59 23 * * *") // Ação agendada para 23:59:50 todos os dias - Marca Eff do geral
    public void scheduleDailyEff() {
        dadosEffController.atualizarDailyEff();
    }

    @Scheduled(cron = "59 59 5 * * *") // Ação agendada para 05:59:59 todos os dias
    @Scheduled(cron = "59 47 15 * * *") // Ação agendada para 15:47:59 todos os dias
    @Scheduled(cron = "59 59 23 * * *") // Ação agendada para 23:59:59 todos os dias
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

    @Scheduled(cron = "0 */10 * * * ?") // Ação agendada para uma vez a cada 10 minutos
    public void scheduleAtualizaDados() throws IOException {
        dadosController.atualizaTxtDados(); //Atualiza com dados dos arquivos .txt
    }

    @Scheduled(cron = "*/1 * * * * ?") // Ação agendada para uma vez a cada segundo
    public void testeAdvantech() throws ParseException {
        // Cria uma instância do controlador de entrada digital
        InstantDiCtrl instantDiCtrl = new InstantDiCtrl();

        try {
            InstantDiCtrl instantDiCtrlList = new InstantDiCtrl();
            ArrayList<DeviceTreeNode> installedDevice = instantDiCtrlList.getSupportedDevices();

            System.out.println(installedDevice);



            // Seleciona o dispositivo DemoDevice,BID#0
            DeviceInformation deviceInfo = new DeviceInformation("DemoDevice,BID#0");
            instantDiCtrl.setSelectedDevice(deviceInfo);

            // Prepara um array de bytes para armazenar os estados dos canais DI
            byte[] diData = new byte[2]; // Para 12 canais, 2 bytes são suficientes

            // Lê os dados de entrada digital
            ErrorCode errorCode = instantDiCtrl.Read(0, 2, diData);
            if (errorCode != ErrorCode.Success) {
                System.out.println("Erro ao ler os dados DI: " + errorCode.toString());
                return;
            }

            // Processa e exibe os estados dos 12 canais DI
            for (int i = 0; i < 12; i++) {
                int byteIndex = i / 8; // Determina qual byte contém o bit do canal
                int bitIndex = i % 8;  // Determina a posição do bit dentro do byte
                boolean estado = ((diData[byteIndex] >> bitIndex) & 0x01) == 1;
                System.out.println("Canal DI " + (i + 1) + ": " + (estado ? "Alto" : "Baixo"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Libera recursos e fecha a conexão
            instantDiCtrl.Dispose();
        }
    }


}