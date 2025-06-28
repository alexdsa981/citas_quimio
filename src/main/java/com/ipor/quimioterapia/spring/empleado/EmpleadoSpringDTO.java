package com.ipor.quimioterapia.spring.empleado;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmpleadoSpringDTO {
    private Long Persona;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private String nombreCompleto;
}
