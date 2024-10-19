//package sge.sgeback.testeAdvantech;
//
//
//import Automation.BDaq.*;  // Certifique-se de que esta biblioteca está no classpath
//import Automation.BDaq.DeviceInformation;
//
//public class testeAdvantech {
//
//    private InstantDiCtrl device;
//
//    public void initializeDevice() throws Exception {
//        // Inicialize o dispositivo com DeviceInformation
//        DeviceInformation deviceInfo = new DeviceInformation("DEMO");
//        device = new InstantDiCtrl();
//        device.setSelectedDevice(deviceInfo);
//
//        // Configuração do dispositivo
//        device.loadProfile(""); // Carregar o perfil padrão ou especificar um caminho de perfil
//    }
//
//    public int[] readDigitalInputs() throws Exception {
//        if (device == null) {
//            throw new IllegalStateException("Device not initialized. Call initializeDevice() first.");
//        }
//
//        // Leia os dados dos sinais digitais DI
//        int startPort = 0; // Porta inicial, ajuste conforme necessário
//        int portCount = 1; // Número de portas, ajuste conforme necessário
//        byte[] data = new byte[portCount];
//
//        device.Read(startPort, portCount, data);
//
//        // Converta os bytes para int e retorne
//        int[] intData = new int[data.length];
//        for (int i = 0; i < data.length; i++) {
//            intData[i] = data[i];
//        }
//        return intData;
//    }
//
//    public void closeDevice() throws Exception {
//        if (device != null) {
//            device.Cleanup();
//            device.Dispose();
//        }
//    }
//
//    public static void main(String[] args) {
//        try {
//            testeAdvantech advantech = new testeAdvantech();
//            advantech.initializeDevice();
//            int[] data = advantech.readDigitalInputs();
//
//            for (int value : data) {
//                System.out.println("Valor do DI: " + value);
//            }
//
//            advantech.closeDevice();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
