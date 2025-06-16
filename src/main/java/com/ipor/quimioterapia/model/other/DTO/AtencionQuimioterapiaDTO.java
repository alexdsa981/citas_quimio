package com.ipor.quimioterapia.model.other.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
public class AtencionQuimioterapiaDTO {
    private Long idEnfermera;
    private Long idMedico;
    private Long idCubiculo;
    private Integer duracionMinutos;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Long idFicha;

}
