package sge.sgeback.repository;

//import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.CrudRepository;
import sge.sgeback.model.Registro_Causal;

import java.util.Optional;

public interface RegistroCausaisRepository extends CrudRepository<Registro_Causal, Integer> {
    Registro_Causal findTopByTestCellOrderByIdDesc(String TestCell);
    Iterable<Registro_Causal> findTop3ByTestCellOrderByIdDesc(String TestCell);
    Registro_Causal findById(int id);
//    Status findByNameAndPassword(String name, String password);
//    Status findByEmail(String email);
//    Status findByEmailAndPassword(String email, String password);
//    List<Status> findByFunction(String function);
}