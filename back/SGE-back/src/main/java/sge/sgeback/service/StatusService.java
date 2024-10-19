package sge.sgeback.service;


import Automation.BDaq.*; // Importa a biblioteca da Advantech
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sge.sgeback.controller.RegistroCausaisController;
import sge.sgeback.controller.StatusController;
import sge.sgeback.model.Status;

import java.util.Optional;

@Service
public class StatusService {

    private InstantDiCtrl instantDiCtrl = new InstantDiCtrl();

    @Autowired
    private RegistroCausaisController registroCausaisController;

    @Autowired
    private StatusController statusController;
    private String spm[] = {"A01", "A02", "A03", "A04", "A05", "A10", "B01", "B02", "B03", "B04", "B05","B06"};


    public void AtualizaStatusTempoReal() throws DaqException {
        DeviceInformation devInfo = new DeviceInformation("USB-4750");
//        DeviceInformation devInfo = new DeviceInformation("Demo");
        InstantDiCtrl instantDiCtrl = new InstantDiCtrl();

        // Set the selected device
        instantDiCtrl.setSelectedDevice(devInfo);

        // Read profile to configure device
        ErrorCode ret = instantDiCtrl.LoadProfile("C:\\Advantech\\DAQNavi\\Driver\\Demo");

        // Leitura das portas disponíveis e o Tipo que são (Input / Output)
        PortDirection[] portDirs = instantDiCtrl.getPortDirection();
        if (portDirs != null) {
            // Get port direction and print the direction information
            DioPortDir currentDir = portDirs[0].getDirection();
//            System.out.println("\n\nCurrent Direction of Port[" + 0 + "] = " + currentDir.toString());
            currentDir = portDirs[1].getDirection();
//            System.out.println("Current Direction of Port[" + 1 + "] = " + currentDir.toString());
        } else {
            System.out.println("There is no DIO port of the selected device can set direction!\n");
        }

        // Leia o estado das portas DI
        byte[] portData = new byte[2]; // Para 12 canais, 2 bytes são suficientes
        int portStart = 0;
        int portCount = 2;
        ret = instantDiCtrl.Read(portStart, portCount, portData);
        if (ret != ErrorCode.Success) {
            System.out.println("Erro ao ler os dados DI: " + ret.toString());
            return;
        }

        Optional<Status> status_ = Optional.of(new Status());

        // Processa e exibe os estados dos 12 canais DI
        for (int i = 0; i < 12; i++) {
            int byteIndex = i / 8; // Determina qual byte contém o bit do canal
            int bitIndex = i % 8;  // Determina a posição do bit dentro do byte
            boolean estado = ((portData[byteIndex] >> bitIndex) & 0x01) == 1;
            status_ = statusController.getStatusTestCell(spm[i]);
            Integer actStat;
            actStat = estado ? 0 : 1; //valores de actStat são invertidos 0->rodando e 1->parado
            if(status_.get().getStatus()==1 && actStat==1){
                registroCausaisController.createAguardandoCausal(spm[i]);
                statusController.updateStatusWithTestCell(spm[i],(estado ? 0 : 1));
            }else if(status_.get().getStatus()==0 && actStat==0){
                registroCausaisController.updateAguardandoCausal(spm[i]);
                statusController.updateStatusWithTestCell(spm[i],(estado ? 0 : 1));
            }
        }
    }
}
