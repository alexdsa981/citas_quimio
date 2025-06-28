package com.ipor.quimioterapia.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionesVitalesDTO {
    private Integer presionSistolica;
    private Integer presionDiastolica;
    private Integer frecuenciaRespiratoria;
    private Integer frecuenciaCardiaca;
    private Integer saturacionOxigeno;
    private Double temperatura;
    private Double peso;
    private Double talla;
    private Double superficieCorporal;
    private Long idFicha;
}
