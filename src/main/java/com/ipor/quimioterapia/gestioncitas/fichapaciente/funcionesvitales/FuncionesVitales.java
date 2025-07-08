package com.ipor.quimioterapia.gestioncitas.fichapaciente.funcionesvitales;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPaciente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class FuncionesVitales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@JsonIgnore
    //@ManyToOne
    //@JoinColumn(name = "id_ficha_paciente")
    //private FichaPaciente fichaPaciente;


    private LocalDate fecha;
    private LocalTime hora;

    private Integer presionSistolica;
    private Integer presionDiastolica;
    private Integer frecuenciaCardiaca;
    private Integer frecuenciaRespiratoria;
    private Integer saturacionOxigeno;

    private Double temperatura;
    private Double pesoKg;
    private Double tallaCm;
    private Double superficieCorporal;

}
