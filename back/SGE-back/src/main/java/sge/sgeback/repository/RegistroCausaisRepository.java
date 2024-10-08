package sge.sgeback.repository;

//import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import sge.sgeback.model.Registro_Causal;
import sge.sgeback.projection.SearchDataProjection;

import java.util.Date;
import java.util.List;

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

    @Query("SELECT\n" +
            "  rc.causal,\n" +
            "sum(time_to_sec(TIMEDIFF(CASE WHEN rc.hora_final IS NULL OR TIME(rc.hora_final) BETWEEN '00:00:00' AND '00:00:01'\n" +
            "                                        THEN CURTIME()\n" +
            "                                        ELSE rc.hora_final \n" +
            "                                    END,rc.hora_inicio)))as sec_time,\n" +
            "sec_to_time(sum(time_to_sec(TIMEDIFF(CASE WHEN rc.hora_final IS NULL OR TIME(rc.hora_final) BETWEEN '00:00:00' AND '00:00:01'\n" +
            "                                        THEN CURTIME()\n" +
            "                                        ELSE rc.hora_final \n" +
            "                                    END,rc.hora_inicio)))) as time_in_time\n" +
            "FROM Registro_Causal rc\n" +
            "WHERE rc.testCell = :testCell \n" +
            "  AND rc.data = :date \n" +
            "  AND rc.hora_inicio IS NOT NULL\n" +
            "group by rc.causal\n" +
            "order by sec_time desc\n")
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

    @Query("SELECT\n" +
            "\trc.Code,\n" +
            "    rc.testCell,\n" +
            "    rc.causal,\n" +
            "    month(rc.data) as mes,\n" +
            "    SUM(time_to_sec(TIMEDIFF(COALESCE(rc.hora_final, CAST(CURRENT_TIMESTAMP AS time)), rc.hora_inicio))) as total_time,\n" +
            "    count(*) AS contagem\n" +
            "FROM Registro_Causal rc\n" +
            "WHERE\n" +
            "\trc.Code BETWEEN :codeMin and :codeMax\n" +
            "    and\n" +
            "    month(rc.data) = :mes\n" +
            "GROUP BY\n" +
            "    rc.testCell,\n" +
            "    rc.causal,\n" +
            "    mes\n" +
            "HAVING\n" +
            "    COUNT(*) > 1\n" +
            "ORDER BY\n" +
            "    rc.testCell,\n" +
            "    rc.causal")
    List<Object[]> findSumCausalEachTestCellandMonth(@Param("codeMin") Float codeMin, @Param("codeMax") Float codeMax, @Param("mes") String mes);

    @Query("SELECT\n" +
            "    rc.testCell as testCell,\n" +
            "    rc.causal as causal,\n" +
            "    MONTH(rc.data) AS mes,\n" +
            "\tCASE\n" +
            "\t\tWHEN HOUR(rc.hora_inicio) * 3600 + MINUTE(rc.hora_inicio)*60 BETWEEN 1 AND 21599 THEN 1\n" +
            "\t\tWHEN HOUR(rc.hora_inicio) * 3600 + MINUTE(rc.hora_inicio)*60 BETWEEN 56879 AND 86399 THEN 2\n" +
            "\t\tELSE 1\n" +
            "\tEND AS turno,\n" +
            "    SUM(TIME_TO_SEC(TIMEDIFF(COALESCE(rc.hora_final, CAST(CURRENT_TIMESTAMP AS time)), rc.hora_inicio))) AS totalTime,\n" +
            "    COUNT(*) AS contagem\n" +
            "FROM\n" +
            "    Registro_Causal rc\n" +
            "WHERE\n" +
            "    rc.Code BETWEEN :codeMin and :codeMax\n" +
            "    AND MONTH(rc.data) = :mes\n" +
            "GROUP BY\n" +
            "    rc.testCell,\n" +
            "    rc.causal,\n" +
            "    mes,\n" +
            "    turno\n" +
            "HAVING\n" +
            "    COUNT(*) > 0\n" +
            "ORDER BY\n" +
            "    rc.testCell,\n" +
            "    rc.causal,\n" +
            "    turno")
    List<SearchDataProjection> findSumCausalEachTestCellandMonthOrderByShift(@Param("codeMin") Float codeMin, @Param("codeMax") Float codeMax, @Param("mes") String mes);

}