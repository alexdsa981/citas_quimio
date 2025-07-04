package com.ipor.quimioterapia.gestioncitas.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ObtenerDatosCitaDTO {
    private String numDoc;
    private String tipoDoc;
    private LocalDate fechaCita;
    private LocalTime horaCita;
    private Long medicoId;
    private String nombrePaciente;
    private int duracion;
    private String aseguradora;
    private String tratamiento;
    private String observaciones;
    private String medicina;
}
