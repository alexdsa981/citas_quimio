package com.ipor.quimioterapia.model.other.DTO;

import java.time.LocalDate;

public class ActualizarPacienteDTO {
    public Long idTipoDocIdentidad;
    public String numeroDocumento;

    public String apellidoPaterno;
    public String apellidoMaterno;
    public String nombres;
    public LocalDate fechaNacimiento;
    public String edad; // si la manejas como texto
    public String sexo;
    public String telefono;

    public Long idTipoPaciente;
    public Long idAseguradora;
    public Long idContratante;
}
