package sge.sgeback.model;

import jakarta.persistence.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name="\"dados_eff\"")
public class Dados_Eff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int Eff;
    private String testCell;
    private int turno;
    private Time Hora;
    private Date data;

    public Dados_Eff(){}

    public Dados_Eff(int id, int eff, String testCell, int turno, Time hora, Date data) {
        this.id = id;
        Eff = eff;
        this.testCell = testCell;
        this.turno = turno;
        Hora = hora;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEff() {
        return Eff;
    }

    public void setEff(int eff) {
        Eff = eff;
    }

    public String getTestCell() {
        return testCell;
    }

    public void setTestCell(String testCell) {
        this.testCell = testCell;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public Time getHora() {
        return Hora;
    }

    public void setHora(Time hora) {
        Hora = hora;
    }

    public Date getDate() {
        return data;
    }

    public void setDate(Date data) {
        this.data= data;
    }
}