package sge.sgeback.service;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sge.sgeback.projection.SearchDataProjection;
import sge.sgeback.repository.RegistroCausaisRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegistroCausais_service {

    @Autowired
    private RegistroCausaisRepository registroCausaisRepository;

    public Map<String, Long> findCausalCount(Float code, String mes) {
        Float codeMax = (float) (code + 0.01);
        Float codeMin = (float) (code - 0.01);
        Long time;

        List<Object[]> results = registroCausaisRepository.findSumCausalEachTestCellandMonth(codeMin, codeMax, mes);

        Map<String, Long> map = new HashMap<>();
        for (Object[] result : results) {
            String testCell = (String) result[1];
            BigDecimal total_time = (BigDecimal) result[4];
            if(total_time.longValue()>0){
                time = total_time.longValue();
            }else{
                time = (long) 0;
            }
            map.put(testCell, time);
        }

        return map;
    }

    public List<SearchDataProjection>findCausalCountAllShifts(Float code, String mes_query) {
        Float codeMax = (float) (code + 0.01);
        Float codeMin = (float) (code - 0.01);

        List<SearchDataProjection> results = registroCausaisRepository.findSumCausalEachTestCellandMonthOrderByShift(codeMin, codeMax, mes_query);

        for (SearchDataProjection result : results) {
            String testCell = result.getTestCell();
            String causal = result.getCausal();
            Integer mes = result.getMes();
            Integer turno = result.getTurno();
            Integer totalTime = result.getTotalTime();
            Integer contagem  = result.getContagem();
        }

        return results;
    }


}
