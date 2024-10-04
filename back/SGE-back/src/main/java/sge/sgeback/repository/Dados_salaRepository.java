package sge.sgeback.repository;

import org.springframework.data.repository.CrudRepository;
import sge.sgeback.model.Dados_sala;

public interface Dados_salaRepository extends CrudRepository<Dados_sala, Integer> {

    Iterable<Dados_sala> findByTestCell(String TestCell);
//    Iterable<Dados_sala> findByTestCellAAndAndCampana(String TestCell, String camp);
//    Iterable<Dados_sala> findByTestCellAAndAndEixo(String TestCell, String eixo);
}
