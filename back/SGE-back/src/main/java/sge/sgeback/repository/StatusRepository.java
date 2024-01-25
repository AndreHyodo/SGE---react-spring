package sge.sgeback.repository;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import sge.sgeback.model.Status;

import java.util.List;

public interface StatusRepository extends CrudRepository<Status, Integer> {
    Iterable<Status> findByIdBetween(int id, int id2);

//    List<String> findAllTestCell();
//    Status findByNameAndPassword(String name, String password);
//    Status findByEmail(String email);
//    Status findByEmailAndPassword(String email, String password);
//    List<Status> findByFunction(String function);
}