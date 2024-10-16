//package sge.sgeback.testeAdvantech;
//
//import Automation.BDaq.*;
//
//public class testeAdvantech {
//
//    private InstantDiCtrl instantDiCtrl;
//    private byte[] data;
//    private int portCount;
//
//    public testeAdvantech() {
//        // Inicializa o controlador DI
//        instantDiCtrl = new InstantDiCtrl();
//        data = null;
//    }
//
//    public void initialize(String deviceName) {
//        try {
//            instantDiCtrl.setSelectedDevice(new DeviceInformation(deviceName));
//            portCount = instantDiCtrl.getPortCount();
//            System.out.println("Número de portas: " + portCount);
//            data = new byte[portCount];
//        } catch (Exception e) {
//            System.out.println("Erro ao inicializar o dispositivo: " + e.getMessage());
//        }
//    }
//
//    public void readDigitalSignals() {
//        while (true) {
//            // Lê os sinais digitais
//            ErrorCode errorCode = instantDiCtrl.Read(0, portCount, data);
//            if (!Global.BioFaild(errorCode)) {
//                for (int i = 0; i < portCount; i++) {
//                    // Imprime o estado de cada porta
//                    System.out.println("Estado da porta " + (i + 1) + ": " + ((data[i] == 1) ? "Alto" : "Baixo"));
//                }
//            } else {
//                System.out.println("Erro ao ler os sinais digitais. Código de erro: " + errorCode.toString());
//            }
//            try {
//                // Aguarda 1 segundo antes da próxima leitura
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                System.out.println("Erro no thread sleep: " + e.getMessage());
//            }
//        }
//    }
//
//    public void cleanup() {
//        if (instantDiCtrl != null) {
//            instantDiCtrl.Cleanup();
//        }
//    }
//
//    testeAdvantechreader = new testeAdvantech();
//
//    // Insira o nome do dispositivo conforme configurado
//    String deviceName = "SeuDispositivo"; // Exemplo: "USB-4716"
//        reader.initialize(deviceName);
//
//    // Inicia a leitura dos sinais digitais
//        reader.readDigitalSignals();
//
//    // Limpeza ao finalizar (nunca será alcançado devido ao loop infinito)
//    // reader.cleanup();
//
//}
