package sge.sgeback.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.web.multipart.MultipartFile;
import sge.sgeback.model.Dados;
import sge.sgeback.model.Files;

public interface FilesRepository extends CrudRepository<Files, Integer> {

}