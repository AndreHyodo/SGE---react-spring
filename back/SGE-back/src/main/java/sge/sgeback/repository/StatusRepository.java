package sge.sgeback.repository;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.repository.query.Param;
import sge.sgeback.model.Status;

import java.util.List;
import java.util.Optional;

public interface StatusRepository extends CrudRepository<Status, Integer> {
    Iterable<Status> findByIdBetween(int id, int id2);

    @Query("SELECT s FROM Status s WHERE s.id BETWEEN :id AND :id2 AND s.Status = 0")
    List<Status> findByIdBetweenAndStatusEmpty(@Param("id") int id, @Param("id2") int id2);
    Optional<Status> findStatusByTestCell(String testCell);

//    List<String> findAllTestCell();
//    Status findByNameAndPassword(String name, String password);
//    Status findByEmail(String email);
//    Status findByEmailAndPassword(String email, String password);
//    List<Status> findByFunction(String function);
}