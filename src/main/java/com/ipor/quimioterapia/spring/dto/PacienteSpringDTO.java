package com.ipor.quimioterapia.spring.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PacienteSpringDTO {
    private Long idPersona;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private String nombreCompleto;
    private String tipoDocumento;
    private String tipoDocumentoNombre;
    private String documento;
    private String fechaNacimiento;
    private Integer edad;
    private String codigoSexo;
    private String sexo;
    private String telefono;
    private String celular;

}
