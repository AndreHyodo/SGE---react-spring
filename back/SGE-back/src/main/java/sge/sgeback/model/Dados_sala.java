package sge.sgeback.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="\"dados_sala\"")
public class Dados_sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String testCell;
    String dina;
    String carrinho;
    @OneToOne
    @JoinColumn(name = "campana_nome")
    Campanas campana;
    @OneToOne
    @JoinColumn(name = "eixo_nome")
    Eixos eixo;
    LocalDate data;
    Time hora;
    String operador;

    
}
