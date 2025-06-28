package com.ipor.quimioterapia.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CieGuardarListaDTO {
    private Long idFicha;
    private List<Long> cieIds;

}
