package com.ipor.quimioterapia.gestioncitas.fichapaciente.detallequimioterapia;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class DetalleQuimioterapia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaModificacion;
    private LocalTime horaModificacion;

    private String medicinas;
    private String observaciones;
    private String anamnesis;
    private String examenFisico;
    private String examenesAuxiliares;
    private String tratamiento;
    private String evolucion;
}
