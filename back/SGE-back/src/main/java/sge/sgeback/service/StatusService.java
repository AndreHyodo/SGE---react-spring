package sge.sgeback.service;

import org.springframework.beans.factory.annotation.Autowired;
import sge.sgeback.model.Status;
import sge.sgeback.repository.StatusRepository;

import java.util.List;

//import Automation.BDaq.*;

public class StatusService {
//    private InstantDiCtrl instantDiCtrl = new InstantDiCtrl();

    public void AtualizaStatusTempoReal() {
//        // Cria uma instância do controlador de entrada digital
//        InstantDiCtrl instantDiCtrl = new InstantDiCtrl();
//
//        try {
//            // Seleciona o dispositivo DemoDevice,BID#0
//            DeviceInformation deviceInfo = new DeviceInformation("DemoDevice,BID#0");
//            instantDiCtrl.setSelectedDevice(deviceInfo);
//
//            // Prepara um array de bytes para armazenar os estados dos canais DI
//            byte[] diData = new byte[2]; // Para 12 canais, 2 bytes são suficientes
//
//            // Lê os dados de entrada digital
//            ErrorCode errorCode = instantDiCtrl.Read(0, 2, diData);
//            if (errorCode != ErrorCode.Success) {
//                System.out.println("Erro ao ler os dados DI: " + errorCode.toString());
//                return;
//            }
//
//            // Processa e exibe os estados dos 12 canais DI
//            for (int i = 0; i < 12; i++) {
//                int byteIndex = i / 8; // Determina qual byte contém o bit do canal
//                int bitIndex = i % 8;  // Determina a posição do bit dentro do byte
//                boolean estado = ((diData[byteIndex] >> bitIndex) & 0x01) == 1;
//                System.out.println("Canal DI " + (i + 1) + ": " + (estado ? "Alto" : "Baixo"));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            // Libera recursos e fecha a conexão
//            instantDiCtrl.Dispose();
//        }
    }
}
