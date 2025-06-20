package com.ipor.quimioterapia.spring.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CieDTO {
    private Long id;
    private String codigoCiePadre;
    private String codigoCie;
    private String nombre;
    private String descripcion;
}
