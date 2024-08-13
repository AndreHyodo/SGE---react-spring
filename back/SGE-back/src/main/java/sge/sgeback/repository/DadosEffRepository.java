package sge.sgeback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import sge.sgeback.model.Dados_Eff;

import java.math.BigDecimal;
import java.util.List;

public interface DadosEffRepository extends CrudRepository<Dados_Eff, Integer> {

    Iterable<Dados_Eff> findTop10ByTestCellOrderByIdDesc(String TestCell);
    List<Dados_Eff> findTop10ByTestCellAndTurnoOrderByIdDesc(String TestCell, int turno);

    @Query("SELECT AVG(dados.Eff) AS media_eff " +
            "FROM Dados_Eff dados " +
            "WHERE dados.testCell = :testCell " +
            "AND DATE(dados.data) = DATE(CURRENT_DATE()) " +
            "GROUP BY DATE(dados.data)")
    int findByTestCellAndData(@Param("testCell") String testCell);



}
