package com.ipor.quimioterapia.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class CitaCreadaDTO {
    //DATOS CITA
    public LocalDate fechaCita;
    public LocalTime horaProgramada;
    public Long medicoId;
    public String tipoPaciente;
    public String aseguradora;
    public String contratante;
    public Long numeroPresupuesto;


    //DATOS PACIENTE
    public Long idPaciente;
    public String tipoDocumento;
    public String numeroDocumento;

    public String apellidoPaterno;
    public String apellidoMaterno;
    public String nombres;
    public String nombreCompleto;

    public String fechaNacimiento;
    public Integer edad;
    public String sexo;

    public String celular;
    public String telefono;

}
