package sge.sgeback.controller;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sge.sgeback.model.Dados;
import sge.sgeback.model.Registro_Causal;
import sge.sgeback.model.Status;
import sge.sgeback.repository.DadosRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Controller
@CrossOrigin(origins = { "*" })
@RequestMapping(path="/dadosList")
public class DadosController {

    @Autowired
    private DadosRepository DadosRepository;

    @Autowired StatusController statusController;

    private Dados dados;


    @GetMapping
    public @ResponseBody Iterable<Dados> getAll(){
        return DadosRepository.findAll();
    }

    @GetMapping(path="/last/{testCell}")
    public @ResponseBody Iterable<Dados> getData(@PathVariable String testCell) {
        return DadosRepository.findFirstByTestCellOrderByIdDesc(testCell);
    }

    public void atualizaExcelDados() throws IOException {
        Iterable<Status> testCellStatuses = statusController.getStatus();

        for (Status testCellStatus : testCellStatuses){
            getExcelData(testCellStatus.getTestCell());
        }
    }

    @GetMapping(path="/dadosExcel/{testCell}")
    public @ResponseBody void getExcelData(@PathVariable String testCell) throws IOException {

//        String filePath = "C:/Users/SC22381/Desktop/NEW SGE/SGE - react + spring/back/SGE-back/src/main/ArquivosTeste/"+ testCell +".xlsx"; //teste-PC Hyodo
        String filePath = "C:/Users/CENTRAL/Desktop/SGE/Controle_dados_teste/"+ testCell +".xlsx"; //Oficial PC Central

        System.out.println(filePath);

        try {
            // Abrindo o arquivo e recuperando a planilha
            FileInputStream file = new FileInputStream(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);

            int rows = sheet.getLastRowNum();
            int cols = sheet.getRow(1).getLastCellNum();

            Dados dados_prova = new Dados();

//            System.out.println("\nlinhas: " + rows + "\ncolunas: "+ cols);

            for (int r=1;r<=rows;r++){  // r=1 para desconsiderar a linha da Legenda
                XSSFRow row = sheet.getRow(r);
                for(int c=0;c<cols;c++){
                    XSSFCell cell =  row.getCell(c);
                    if(cell!=null){
//                        switch (cell.getCellType())
//                        {
//                            case STRING:
//                                System.out.println(cell.getStringCellValue());
//                                break;
//                            case NUMERIC:
//                                System.out.println(cell.getNumericCellValue());
//                                break;
//                        }
                        switch (c)
                        {
                            case 0:
                                dados_prova.setTestCell(cell.getStringCellValue());
                                break;
                            case 1:
                                dados_prova.setMotor((int)cell.getNumericCellValue());
                                break;
                            case 2:
                                dados_prova.setProjeto(cell.getStringCellValue());
                                break;
                            case 3:
                                dados_prova.setTeste(cell.getStringCellValue());
                                break;
                        }
                    }else{
                        System.out.println(testCell + ": essa celula (" + r + ", "+ c + ") está vazia");
                    }
                }
            }

            Date now = new Date();
            SimpleDateFormat data_formatada = new SimpleDateFormat("dd/MM/yyyy");
            LocalTime hora_atual = LocalTime.now();


            dados_prova.setHora_inicio(Time.valueOf(hora_atual));
            dados_prova.setDATE(data_formatada.parse(data_formatada.format(now)));
            insertDados(dados_prova);

            file.close();
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(path="/insertDados")
    public ResponseEntity<Dados> insertDados(@RequestBody Dados dados) {

        Dados newDados = DadosRepository.save(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDados);

    }

}
