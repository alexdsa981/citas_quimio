package com.ipor.quimioterapia.SpringDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PacienteDesdeGarantiaDTO {
    private Long numeroPresupuesto;
    private Long idPaciente;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private String tipoDocumento;
    private String tipoDocumentoNombre;
    private String documento;
    private String fechaNacimiento;
    private Integer edad;
    private String codigoSexo;
    private String sexo;
    private String telefono;
    private String celular;
    private Integer idTipoPaciente;
    private String tipoPaciente;
    private Long idEmpresaAseguradora;
    private String empresaAseguradora;
    private Long idEmpresaEmpleadora;
    private String empresaEmpleadora;
}
