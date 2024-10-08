package sge.sgeback.model;

import jakarta.persistence.*;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name= "\"campanas\"")
public class Campanas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String local;
    private LocalDate dataEntrada; // Mude para LocalDate
    private LocalDate dataRevisao; // Mude para LocalDate
    private LocalDate dataSaida; // Mude para LocalDate
    private int horaRodagem;
    private String obs;
    private int status;

    public Campanas() {
    }

    public Campanas(Integer id, String nome, String local, LocalDate dataRevisao, LocalDate dataEntrada, LocalDate dataSaida, Integer horaRodagem, String obs, Integer status) {
        this.id = id;
        this.nome = nome;
        this.local = local;
        this.dataRevisao = dataRevisao;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.horaRodagem = horaRodagem;
        this.obs = obs;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public LocalDate getDataRevisao() {
        return dataRevisao;
    }

    public void setDataRevisao(LocalDate dataRevisao) {
        this.dataRevisao = dataRevisao;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }

    public int getHoraRodagem() {
        return horaRodagem;
    }

    public void setHoraRodagem(int horaRodagem) {
        this.horaRodagem = horaRodagem;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
