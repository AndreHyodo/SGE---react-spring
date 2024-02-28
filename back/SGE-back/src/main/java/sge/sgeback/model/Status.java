package sge.sgeback.model;

import jakarta.persistence.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.core.SpringVersion;
import org.xml.sax.helpers.AttributesImpl;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name="\"Status\"")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String testCell;
    private Boolean Status;
    private Float Code;
    private String Causal;
    private int Motor;
    private String Projeto;
    private String Teste;
    private Date date;
    private Time time;

    public Status(){}

    public Status(int id, String testCell, Boolean status, Float code, String causal, int motor, String projeto, String teste, Date date, Time time) {
        this.id = id;
        this.testCell = testCell;
        Status = status;
        Code = code;
        Causal = causal;
        Motor = motor;
        Projeto = projeto;
        Teste = teste;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTestCell() {
        return testCell;
    }

    public void setTestCell(String sala) {
        testCell = sala;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public Float getCode() {
        return Code;
    }

    public void setCode(Float code) {
        Code = code;
    }

    public String getCausal() {
        return Causal;
    }

    public void setCausal(String causal) {
        Causal = causal;
    }

    public int getMotor() {
        return Motor;
    }

    public void setMotor(int motor) {
        Motor = motor;
    }

    public String getProjeto() {
        return Projeto;
    }

    public void setProjeto(String projeto) {
        Projeto = projeto;
    }

    public String getTeste() {
        return Teste;
    }

    public void setTeste(String teste) {
        Teste = teste;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "{" +
                " Sala='" + getTestCell() + "'" +
                ", Status='" + getStatus() + "'" +
                "}";
    }

}