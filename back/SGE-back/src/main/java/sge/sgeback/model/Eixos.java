package sge.sgeback.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sge.sgeback.Component.enums.StatusComponentsRole;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "\"eixos\"")
public class Eixos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String local;
    LocalDate dataEntrada; // Mude para LocalDate
    LocalDate dataRevisao; // Mude para LocalDate
    LocalDate dataSaida; // Mude para LocalDate
    int horaRodagem;
    String obs;
    StatusComponentsRole status;
}
