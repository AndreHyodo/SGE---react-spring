package sge.sgeback.repository;

//import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import sge.sgeback.model.Registro_Causal;

import java.util.List;
import java.util.Optional;

public interface RegistroCausaisRepository extends CrudRepository<Registro_Causal, Integer> {
    Registro_Causal findTopByTestCellOrderByIdDesc(String TestCell);
    Iterable<Registro_Causal> findTop3ByTestCellOrderByIdDesc(String TestCell);
<<<<<<< HEAD
    Registro_Causal findById(int id);
    @Query("SELECT COUNT(rc.id) AS contagem, rc.causal, rc.testCell " +
            "FROM Registro_Causal rc " +
            "WHERE rc.testCell = :testCell " +
            "GROUP BY rc.causal")
    List<Object[]> findCausalCountByTestCell(@Param("testCell") String testCell);
=======
>>>>>>> fcb4ef45c01b214c963ff76b7761651a763b8376
//    Status findByNameAndPassword(String name, String password);
//    Status findByEmail(String email);
//    Status findByEmailAndPassword(String email, String password);
//    List<Status> findByFunction(String function);
}