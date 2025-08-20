package com.ipor.quimioterapia.gestioncitas.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Setter
public class EdicionDTO {
    private Long idFicha;
    private LocalDate fecha;
    private LocalTime hora;
    private Long idMedico;
    private Integer duracionMinutos;

    private String aseguradora;

    //DATOS DETALLE QUIMIO
    public String medicamentos;
    public String observaciones;
    public String tratamiento;



}
