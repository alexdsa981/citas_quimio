package com.ipor.quimioterapia.spring.usuario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UsuarioSpringDTO {
    private String usuario;
    private String nombre;
    private String clave;
}
