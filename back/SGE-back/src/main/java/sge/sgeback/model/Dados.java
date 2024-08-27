package sge.sgeback.model;

import jakarta.persistence.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name="\"dados_prova\"")
public class Dados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String testCell;
    private Integer Motor;
    private String Projeto;
    private String Teste;
    private Time Hora_inicio;
    private Date DATE;

    public Dados(){}

    public Dados(int id, String testCell, Integer motor, String projeto, String teste, Date DATE, Time Hora_inicio) {
        this.id = id;
        this.testCell = testCell;
        Motor = motor;
        Projeto = projeto;
        Teste = teste;
        this.DATE = DATE;
        this.Hora_inicio = Hora_inicio;
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

    public void setTestCell(String testCell) {
        this.testCell = testCell;
    }

    public Integer getMotor() {
        return Motor;
    }

    public void setMotor(Integer motor) {
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

    public Date getDATE() {
        return DATE;
    }

    public void setDATE(Date DATE) {
        this.DATE = DATE;
    }

    public Time getHora_inicio() {
        return Hora_inicio;
    }

    public void setHora_inicio(Time hora_inicio) {
        Hora_inicio = hora_inicio;
    }
}