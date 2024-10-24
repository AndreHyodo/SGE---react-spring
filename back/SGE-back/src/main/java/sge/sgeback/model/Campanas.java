package sge.sgeback.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sge.sgeback.Component.enums.StatusComponentsRole;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name= "\"campanas\"")
public class Campanas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String nome;
    String local;
    LocalDate dataEntrada; // Mude para LocalDate
    LocalDate dataRevisao; // Mude para LocalDate
    LocalDate dataSaida; // Mude para LocalDate
    int horaRodagem;
    String obs;
    StatusComponentsRole status;

}
