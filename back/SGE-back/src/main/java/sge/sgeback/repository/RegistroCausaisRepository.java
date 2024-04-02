package sge.sgeback.repository;

//import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import sge.sgeback.model.Registro_Causal;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RegistroCausaisRepository extends CrudRepository<Registro_Causal, Integer> {
    Registro_Causal findTopByTestCellOrderByIdDesc(String TestCell);
    Iterable<Registro_Causal> findTop3ByTestCellOrderByIdDesc(String TestCell);
    Registro_Causal findById(int id);
    @Query("SELECT COUNT(rc.id) AS contagem, rc.causal, rc.testCell " +
            "FROM Registro_Causal rc " +
            "WHERE rc.testCell = :testCell " +
            "AND MONTH(rc.data) = MONTH(CURRENT_DATE()) " +
            "GROUP BY rc.causal " +
            "ORDER BY contagem DESC")
    List<Object[]> findCausalCountByTestCellOrderBycontagem(@Param("testCell") String testCell);

    @Query("SELECT TIME_FORMAT(\n" +
            "    COALESCE(SUM(TIMEDIFF(COALESCE(rc.hora_final, CURRENT_TIME()), rc.hora_inicio)), 0),\n" +
            "    '%H:%i:%s') AS contagem, " +
            "SUM(TIMEDIFF(COALESCE(rc.hora_final, CURRENT_TIME()), rc.hora_inicio)) AS contagem_sec, " +
            "rc.causal, rc.testCell " +
            "FROM Registro_Causal rc " +
            "WHERE rc.testCell = :testCell " +
            "AND DATE(rc.data) = :date " +
            "GROUP BY rc.causal "+
            "ORDER BY contagem_sec DESC")
    List<Object[]> findCausalSumCountByTestCellOrderBycontagem(@Param("testCell") String testCell, @Param("date") @DateTimeFormat(pattern= "yyyy-MM-dd") Date date);

    @Query("SELECT COUNT(rc.id) AS contagem, rc.causal, rc.testCell " +
            "FROM Registro_Causal rc " +
            "WHERE rc.testCell = :testCell " +
            "AND DATE(rc.data) = :date " +
            "GROUP BY rc.causal " +
            "ORDER BY contagem DESC")
    List<Object[]> findCausalCountByTestCellDateOrderBycontagem(@Param("testCell") String testCell, @Param("date") @DateTimeFormat(pattern= "yyyy-MM-dd") Date date);

}