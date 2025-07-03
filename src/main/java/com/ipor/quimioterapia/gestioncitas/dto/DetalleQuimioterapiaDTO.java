package com.ipor.quimioterapia.gestioncitas.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleQuimioterapiaDTO {
    private String medicinas;
    private String observaciones;
    private String anamnesis;
    private String examenFisico;
    private String examenesAuxiliares;
    private String tratamiento;
    private String evolucion;
    private Long idFicha;
}
