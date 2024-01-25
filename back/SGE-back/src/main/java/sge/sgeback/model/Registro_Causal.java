package sge.sgeback.model;

import jakarta.persistence.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Table(name= "\"registro_causal\"")
public class Registro_Causal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String testCell;
    private String causal;

    public Registro_Causal() {
    }

    public Registro_Causal(int id, String TestCell, String causal) {

        this.id = id;
        this.testCell = TestCell;
        this.causal = causal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSala() {
        return testCell;
    }

    public void setSala(String TestCell) {
        TestCell = TestCell;
    }

    public String getCausal() {
        return causal;
    }

    public void setCausal(String causal) {
        this.causal = causal;
    }
}