package sge.sgeback.model;

import jakarta.persistence.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

@Entity
@Table(name= "\"registro_causal\"")
public class Registro_Causal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String testCell;
    private Float Code;
    private String causal;
    private Time hora_inicio;
    private LocalTime hora_final;
    private Date data;
    private String obs;

    public Registro_Causal(){}

    public Registro_Causal(int id, String testCell, Float code, String causal, Time hora_inicio, LocalTime hora_final, Date data, String obs) {
        this.id = id;
        this.testCell = testCell;
        Code = code;
        this.causal = causal;
        this.hora_inicio = hora_inicio;
        this.hora_final = hora_final;
        this.data = data;
        this.obs = obs;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTestCell() {
        return testCell;
    }

    public void setTestCell(String testCell) {
        this.testCell = testCell;
    }

    public Float getCode() {
        return Code;
    }

    public void setCode(Float code) {
        Code = code;
    }

    public String getCausal() {
        return causal;
    }

    public void setCausal(String causal) {
        this.causal = causal;
    }

    public Time getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(Time hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public LocalTime getHora_final() {
        return hora_final;
    }

    public void setHora_final(LocalTime hora_final) {
        this.hora_final = hora_final;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}