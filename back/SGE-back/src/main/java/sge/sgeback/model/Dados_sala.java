package sge.sgeback.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="\"dados_sala\"")
public class Dados_sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String testCell;
    private String dina;
    private String carrinho;
    private String campana;
    private String eixo;
    private LocalDate data;
    private Time hora;
    private String operador;

    public Dados_sala() {
    }

    public Dados_sala(Integer id, String testCell, String dina, String carrinho, String campana, String eixo, LocalDate data, Time hora, String operador) {
        this.id = id;
        this.testCell = testCell;
        this.dina = dina;
        this.carrinho = carrinho;
        this.campana = campana;
        this.eixo = eixo;
        this.data = data;
        this.hora = hora;
        this.operador = operador;
    }

    public String getTestCell() {
        return testCell;
    }

    public void setTestCell(String testCell) {
        this.testCell = testCell;
    }

    public String getDina() {
        return dina;
    }

    public void setDina(String dina) {
        this.dina = dina;
    }

    public String getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(String carrinho) {
        this.carrinho = carrinho;
    }

    public String getCampana() {
        return campana;
    }

    public void setCampana(String campana) {
        this.campana = campana;
    }

    public String getEixo() {
        return eixo;
    }

    public void setEixo(String eixo) {
        this.eixo = eixo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public Integer getId_dados() {
        return id;
    }

    public void setId_dados(Integer id_dados) {
        this.id = id_dados;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }
}
