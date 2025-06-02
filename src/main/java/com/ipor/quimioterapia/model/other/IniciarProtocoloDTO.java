package com.ipor.quimioterapia.model.other;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class IniciarProtocoloDTO {
    private Long idFicha;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaInicio;
}
