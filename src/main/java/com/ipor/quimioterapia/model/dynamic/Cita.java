package com.ipor.quimioterapia.model.dynamic;


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
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column(nullable = false)
    private LocalDate fecha;
    //@Column(nullable = false)
    private LocalTime horaProgramada;

    private LocalTime horaCreacion;

    private Long numPresupuesto;
    private String tipoPaciente;
    private String aseguradora;
    private String contratante;


    @Enumerated(EnumType.STRING)
    //@Column(nullable = false)
    private EstadoCita estado;


    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_medico")
    private Medico medicoConsulta;



}
