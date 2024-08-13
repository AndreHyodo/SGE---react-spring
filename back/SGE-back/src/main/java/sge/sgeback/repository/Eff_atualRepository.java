package sge.sgeback.repository;

//import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import sge.sgeback.model.Causais;
import sge.sgeback.model.Eff_atual;

public interface Eff_atualRepository extends CrudRepository<Causais, Integer> {

    @Query(value = "SELECT SUM(CASE WHEN hora_final = '00:00:00' THEN TIMESTAMPDIFF(SECOND, hora_inicio, CURRENT_TIME) / 60.0 ELSE TIMESTAMPDIFF(SECOND, hora_inicio, hora_final) / 60.0 END) AS tempo_total_min " +
            "FROM Registro_Causal rc " +
            "WHERE rc.data = CURRENT_DATE " +
            "AND rc.testCell = :testCell " +
            "AND (" +
            "CURRENT_TIME >= '06:00:00' AND CURRENT_TIME < '15:48:00' AND hora_final >= '06:00:02' AND hora_final < '15:48:00' " +
            "OR " +
            "CURRENT_TIME >= '15:48:00' AND CURRENT_TIME < '23:59:59' AND hora_final >= '15:48:00' AND hora_final < '23:59:59' " +
            "OR " +
            "CURRENT_TIME >= '00:00:00' AND CURRENT_TIME < '01:09:00' AND hora_final >= '00:00:00' AND hora_final < '01:09:00' " +
            "OR " +
            "CURRENT_TIME >= '01:09:00' AND CURRENT_TIME < '06:00:00' AND hora_final >= '01:09:00' AND hora_final < '06:00:00' " +
            "OR " +
            "hora_final = '00:00:00' " +
            ")", nativeQuery = true)
    Eff_atual findTempoTotalSecByRoomName(@Param("testCell") String testCell);
}