package com.ipor.quimioterapia.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class DuplicarCitaDTO {
    Long idFichaPaciente;
    LocalDate fecha;
    LocalTime HoraProgramada;
    Long idMedico;
}
