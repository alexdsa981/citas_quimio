package com.ipor.quimioterapia.gestioncitas.dto;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class AtencionQuimioterapiaDTO {
    private Long idEnfermera;
    private Long idMedico;
    private Long idCubiculo;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Long idFicha;

}
