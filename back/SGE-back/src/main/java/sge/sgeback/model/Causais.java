package sge.sgeback.model;

import jakarta.persistence.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Table(name= "\"causais\"")
public class Causais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String Type;
    private String causal;
    private String description;
    private float Code;

    public Causais() {
    }

    public Causais(String Type, String causal, String description, float Code) {

        this.Type = Type;
        this.causal = causal;
        this.description = description;
        this.Code = Code;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getCausal() {
        return causal;
    }

    public void setCausal(String causal) {
        this.causal = causal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getCode() {
        return Code;
    }

    public void setCode(float code) {
        Code = code;
    }
}