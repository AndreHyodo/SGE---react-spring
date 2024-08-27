package sge.sgeback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sge.sgeback.model.Files;
import sge.sgeback.repository.FilesRepository;

import java.util.Optional;

@Service
public class FileService {

    @Autowired
    private FilesRepository filesRepository;

    public Files saveFile(MultipartFile file){
        String docName = file.getOriginalFilename();

        try{
            Files file_ = new Files(docName,file.getContentType(),file.getBytes());
            return filesRepository.save(file_);

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Optional<Files> getFile(Integer id){
        return filesRepository.findById(id);
    }

    public Iterable<Files> getListFiles(){
        return filesRepository.findAll();
    }
}
