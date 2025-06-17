package com.ipor.quimioterapia.model.other.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpleadoCrearDTO {
    private Long id;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String nombreCompleto;
}