package sge.sgeback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sge.sgeback.model.Files;
import sge.sgeback.service.FileService;

import java.util.List;


@Controller
@CrossOrigin(origins = { "*" })
@RequestMapping(path="/files")
public class FilesController {

    @Autowired
    private FileService fileService;


    @GetMapping("/")
    public String get(Model model){
        List<Files> files_ = (List<Files>) fileService.getListFiles();
        model.addAttribute("file", files_);
        return "file";
    }

//    @PostMapping("/uploadFiles")
//    public String uploadFiles(@RequestParam("file")MultipartFile[] files){
//        for(MultipartFile file: files){
//            fileService.saveFile(file);
//        }
//        return "redirect:/";
//    }

    @PostMapping("/uploadFiles")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file){
        String fileName = file.getOriginalFilename();

        fileService.saveFile(file);

//        try {
//            file.transferTo(new Files("C:\\upload\\" + fileName));
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
        return ResponseEntity.ok("File uploaded successfully");
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer fileId){
        Files file = fileService.getFile(fileId).get();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\" "+file.getFileName()+"\" ")
                .body(new ByteArrayResource(file.getData()));
    }

}

//@Controller
//@CrossOrigin(origins = { "*" })
//@RequestMapping(path="/files")
//public class FileUploadController {
//
//    private final FileService storageService;
//
//    @Autowired
//    public FileUploadController(FileService storageService) {
//        this.storageService = storageService;
//    }
//
//    @GetMapping("/")
//    public String returnHTML(Model model, HttpServletResponse response ) throws IOException{
//        System.out.println("Inside GET");
//        List<String> output =  storageService.loadAll().map(
//                        path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
//                                "serveFile", path.getFileName().toString()).build().toUri().toString())
//                .collect(Collectors.toList());
//
//        for(String files:output)
//        {
//            System.out.println(files);
//        }
//
//        //response.setHeader();
//        return "uploadForm.html";
//    }
//
//    @GetMapping("/files/{filename:.+}")
//    @ResponseBody
//    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
//
//        Resource file = storageService.loadAsResource(filename);
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
//    }
//
//    @PostMapping("/")
//    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
//
//        System.out.println("Inside POST");
//
//        System.out.println(" Files " + file.getOriginalFilename());
//        storageService.store(file);
//
//
//        return "redirect:/";
//    }
//
//    @ExceptionHandler(FileNotFoundException.class)
//    public ResponseEntity<?> handleStorageFileNotFound(FileNotFoundException exc) {
//        return ResponseEntity.notFound().build();
//    }
//
//}