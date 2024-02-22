package sge.sgeback.repository;

import org.springframework.data.repository.CrudRepository;
import sge.sgeback.model.Dados;

public interface DadosRepository extends CrudRepository<Dados, Integer> {

    Iterable<Dados> findFirstByTestCellOrderByIdDesc(String testCell);

}