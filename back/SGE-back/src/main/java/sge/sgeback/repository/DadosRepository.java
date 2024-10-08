package sge.sgeback.repository;

import org.springframework.data.repository.CrudRepository;
import sge.sgeback.model.Dados;

public interface DadosRepository extends CrudRepository<Dados, Integer> {

    Dados findFirstByTestCellOrderByIdDesc(String testCell);


}