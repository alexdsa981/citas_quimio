package com.ipor.quimioterapia.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class FinalizarProtocoloDTO {
    private Long idFicha;
    private LocalTime horaFin;
    private Long idMedico;
}
