package sge.sgeback.repository;

//import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import sge.sgeback.model.Registro_Causal;

import java.sql.Time;
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
            "    COALESCE(SUM(TIMEDIFF(COALESCE(NULLIF(rc.hora_final,0), CURRENT_TIME()), rc.hora_inicio)), 0),\n" +
            "    '%H:%i:%s') AS contagem, " +
            "SUM(TIMEDIFF(COALESCE(NULLIF(rc.hora_final,0), CURRENT_TIME()), rc.hora_inicio)) AS contagem_sec, " +
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

    @Query("SELECT rc from Registro_Causal rc " +
            "WHERE rc.testCell = :TestCell " +
            "AND rc.causal != 'Aguardando causal' " +
            "order by rc.id desc LIMIT 1")
    Registro_Causal findTopByTestCellAndCausalNotEmpty(String TestCell);


    @Query(value = "SELECT CAST(SUM(CASE WHEN hora_final = '00:00:00' THEN TIMESTAMPDIFF(SECOND, hora_inicio, TIME(NOW())) ELSE TIMESTAMPDIFF(SECOND, hora_inicio, hora_final) END) AS FLOAT) AS tempo_total_sec FROM Registro_Causal rc WHERE rc.testCell = :testCell AND RC.data = DATE(NOW()) AND ((TIME(NOW()) >= '06:00:00' AND TIME(NOW()) < '15:48:00' AND hora_final >= '06:00:02' AND hora_final < '15:48:00') OR ((TIME(NOW()) >= '15:48:00' AND TIME(NOW()) < '23:59:59') AND (hora_final >= '15:48:00' AND hora_final<'23:59:59')) OR ((TIME(NOW()) >= '00:00:00' AND TIME(NOW()) < '01:09:00') AND (hora_final >= '00:00:00' AND hora_final<'01:09:00')) OR ((TIME(NOW()) >= '01:09:00' AND TIME(NOW()) < '06:00:00') AND (hora_final >= '01:09:00' AND hora_final < '06:00:00')) OR hora_final = '00:00:00')", nativeQuery = true)
    Float findTempoTotalSec(String testCell);

    @Query(value = "SELECT CAST(SUM(CASE WHEN rc.hora_final = '00:00:00' THEN  " +
            "TIMESTAMPDIFF(SECOND, rc.hora_inicio, CURRENT_TIME) ELSE  " +
            "TIMESTAMPDIFF(SECOND, rc.hora_inicio, rc.hora_final) END) AS FLOAT) AS tempoTotalSec " +
            "FROM registro_causal rc " +
            "WHERE rc.testCell = :testCell " +
            "AND rc.data = CURRENT_DATE " +
            "AND ( " +
            "(CURRENT_TIME BETWEEN '06:00:00' AND '15:48:00' AND rc.hora_final BETWEEN '06:00:02' AND '15:48:00') " +
            "OR " +
            "(CURRENT_TIME BETWEEN '15:48:00' AND '23:59:59' AND rc.hora_final BETWEEN '15:48:00' AND '23:59:59') " +
            "OR " +
            "(CURRENT_TIME BETWEEN '00:00:00' AND '01:09:00' AND rc.hora_final BETWEEN '00:00:00' AND '01:09:00') " +
            "OR " +
            "(CURRENT_TIME BETWEEN '01:09:00' AND '06:00:00' AND rc.hora_final BETWEEN '01:09:00' AND '06:00:00') " +
            "OR " +
            "rc.hora_final = '00:00:00' " +
            ")", nativeQuery = true)
    Float findTotalStop(String testCell);

    @Query(value = "SELECT " +
            "IF(rc.hora_final = '00:00:00', " +
            "TIMESTAMPDIFF(SECOND, rc.hora_inicio, CURTIME()), " +
            "TIMESTAMPDIFF(SECOND, rc.hora_inicio, rc.hora_final)) AS tempoAtual " +
            "FROM registro_causal rc " +
            "WHERE rc.data = CURDATE() " +
            "AND rc.testCell = :roomName " +
            "AND rc.hora_final = '00:00:00' " +
            "ORDER BY rc.id DESC LIMIT 1", nativeQuery = true)
    Float findCurrentStop(String roomName);

}