package com.ipor.quimioterapia.gestioncitas.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class DuplicarCitaDTO {
    private Long idFichaPaciente;
    private LocalDate fecha;
    private LocalTime HoraProgramada;
    private Integer duracionMinutos;
    private Long idMedico;

    //DATOS DETALLE QUIMIO
    public String medicamentos;
    public String observaciones;
    public String tratamiento;

}
