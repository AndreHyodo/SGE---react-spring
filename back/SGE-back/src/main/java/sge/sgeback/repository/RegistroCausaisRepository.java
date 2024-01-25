package sge.sgeback.repository;

//import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.CrudRepository;
import sge.sgeback.model.Registro_Causal;

public interface RegistroCausaisRepository extends CrudRepository<Registro_Causal, Integer> {
    Registro_Causal findTopByTestCellOrderByCausalDesc(String TestCell);
//    Status findBySala(String sala);
//    Status findByNameAndPassword(String name, String password);
//    Status findByEmail(String email);
//    Status findByEmailAndPassword(String email, String password);
//    List<Status> findByFunction(String function);
}