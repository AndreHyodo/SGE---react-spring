package sge.sgeback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sge.sgeback.model.Files;
import sge.sgeback.service.FileService;

import java.util.List;


@Controller
@CrossOrigin(origins = { "*" })
@RequestMapping(path="/files")
public class FilesController {

    @Autowired
    private FileService fileService;


    //TESTAR
//    @GetMapping("/")
//    public String get(Model model){
//        Iterable<Files> files_ = fileService.getListFiles();
//        model.addAllAttributes("files", files_);
//        return "files";
//    }

}
