package com.ipor.quimioterapia.model.other;

import java.time.LocalDate;
import java.time.LocalTime;

public class FichaPacienteDTO {
    public LocalDate fechaCita;
    public Long idCubiculo;
    public LocalTime horaProgramada;
    public Integer horasProtocolo;
    public Integer minutosProtocolo;

    //public Long idTipoPaciente;
    //public Long idAseguradora;
    public Long idContratante;
    public Long tipoEntradaId;


    public String nroHistoria;
    public String numeroDocumento;
    public String apellidoPaterno;
    public String apellidoMaterno;
    public String nombres;
    public LocalDate fechaNacimiento;
    //public Integer edad;
    public String sexo;
    public Long idTipoDocIdentidad;
    public String telefono;

    public Double peso;
    public Double talla;
    public Double presionSistolica;
    public Double presionDiastolica;
    public Double frecuenciaCardiaca;
    public Double frecuenciaRespiratoria;
    public Double temperatura;
    public Double saturacionOxigeno;
    public Double superficieCorporal;
}
