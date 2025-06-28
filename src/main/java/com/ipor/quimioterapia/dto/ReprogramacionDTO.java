package com.ipor.quimioterapia.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Setter
public class ReprogramacionDTO {
    private Long idFicha;
    private LocalDate fecha;
    private LocalTime hora;
    private Long idMedico;
}
