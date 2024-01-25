//package sge.sgeback.model;
//
//import jakarta.persistence.*;
//
//import jakarta.persistence.Test;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//
//import sge.sgeback.repository.StatusRepository;
//import sge.sgeback.controller.StatusController;
//
//import Automation.BDaq.DeviceInformation;
//import Automation.BDaq.ErrorCode;
//import Automation.BDaq.InstantDiCtrl;
//import Common.Global;
//
//@Override
//public class testUpdateStatus{
//
////    StatusController controller = new StatusController();
////
////    Status status = new Status();
////
////    try {
////        instantDiCtrl.setSelectedDevice(new DeviceInformation("DemoDevice,BID#0"));
////    } catch (Exception ex) {
////        ShowMessage("Sorry, there're some errors occured: " + ex.getMessage());
////    }
////
////    portCount = instantDiCtrl.getPortCount();
////    DiPorts = new DioPortUI[portCount];
////
////    if (data == null || data.length < portCount) {
////        data = new byte[portCount];
////    }
////    int portCountAct;
////    ErrorCode errorCode = instantDiCtrl.Read(0, portCount, data);
////
////    if (!Global.BioFaild(errorCode)) {
////        for (int b = 0; b < portCount; b++) {
////            portCountAct = b+1;
////            DiPorts[b].setState2(data[b],portCountAct);
////        }
////
////        for (int i = 17; i >= 0; i--) {
////            if(i<4) {
////                if(actStateLow[i+4]== (byte)0) {
////                    controller.updateStatus(roomName[room], false);
////                }else{
////                    controller.updateStatus(roomName[room], true);
////                }
////
////                aux2++;
////            }else if(i>=4 && i<6){
////                if(actStateHigh[i-4]==(byte)0) {
////                    controller.updateStatus(roomName[room], false);
////                }else{
////                    controller.updateStatus(roomName[room], true);
////                }
////            }else if(i>11){
////                if(actStateHigh[i-10]==(byte)0) {
////                    controller.updateStatus(roomName[room], false);
////                }else{
////                    controller.updateStatus(roomName[room], true);
////                }
////            }
////
////        room++;
////        }
////    } else {
////    ShowMessage("Sorry, there're some errors occred, ErrorCode: " + errorCode.toString());
////    return;
////    }
//
//}