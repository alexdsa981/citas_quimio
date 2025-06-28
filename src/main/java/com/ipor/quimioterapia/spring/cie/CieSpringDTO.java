package com.ipor.quimioterapia.spring.cie;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CieSpringDTO {
    private Long id;
    private String codigoCiePadre;
    private String codigoCie;
    private String nombre;
    private String descripcion;
}
