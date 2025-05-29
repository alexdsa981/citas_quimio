package com.ipor.quimioterapia.model.dynamic;

import com.ipor.quimioterapia.model.fixed.Cubiculo;
import com.ipor.quimioterapia.repository.dynamic.MedicoRepository;
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
    @Column(nullable = false)
    private LocalDate fecha;
    @Column(nullable = false)
    private LocalTime horaProgramada;
    @Column(nullable = false)
    private LocalTime horaProtocolo;
    @Column(nullable = false)
    private LocalTime horaInicio;
    @Column(nullable = false)
    private LocalTime horaFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCita estado;


    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;
    @ManyToOne
    @JoinColumn(name = "id_cubiculo")
    private Cubiculo cubiculo;
    @ManyToOne
    @JoinColumn(name = "id_enfermera")
    private Enfermera enfermera;
    @ManyToOne
    @JoinColumn(name = "id_medico")
    private Medico medico;

}
