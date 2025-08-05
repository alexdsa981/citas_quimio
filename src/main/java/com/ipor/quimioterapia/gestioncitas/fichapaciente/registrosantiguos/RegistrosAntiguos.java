package com.ipor.quimioterapia.gestioncitas.fichapaciente.registrosantiguos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class RegistrosAntiguos {

    @Id
    @Column(nullable = false)
    private Long idQuimioterapia;

    @Column(length = 50, nullable = false)
    private String establecimiento;

    @Column(length = 50, nullable = false)
    private String servicio;

    @Column(length = 15)
    private String historia;

    @Column(length = 100, nullable = false)
    private String apellido_p;

    @Column(length = 100, nullable = false)
    private String apellido_m;

    @Column(length = 200, nullable = false)
    private String nombres;

    @Column(nullable = false)
    private LocalDate fecNacimiento;

    private Integer edad;

    @Column(length = 25, nullable = false)
    private String sexo;

    @Column(length = 50)
    private String docIdentidad;

    @Column(length = 50)
    private String noDocIdentidad;

    @Column(length = 50)
    private String telefono;

    @Column(length = 50)
    private String entrada;

    @Column(length = 10)
    private String codcie1;

    @Column(length = 10)
    private String codcie2;

    @Column(length = 250)
    private String cie1;

    @Column(length = 250)
    private String cie2;

    @Column(length = 150)
    private String tipoPaciente;

    @Column(length = 150)
    private String aseguradora;

    @Column(length = 150)
    private String contratante;

    private LocalDate fecIngreso;
    private LocalDate fecRegistro;

    @Column(length = 200)
    private String medico;

    @Column(length = 50)
    private String cmp;

    @Column(length = 200)
    private String enfermera;

    @Column(length = 50)
    private String rne;

    private LocalTime horaInicio;
    private LocalTime horaFin;
    private LocalTime horaProtocolo;
    private LocalTime horaReal;

    @Column(length = 100)
    private String cama;

    @Column(length = 200)
    private String medicoRef;

    @Column(length = 999)
    private String medicinas;

    @Column(length = 999)
    private String observaciones;

    @Column(length = 999)
    private String anamnesis;

    @Column(length = 999)
    private String examFisico;

    @Column(length = 999)
    private String examAuxiliar;

    @Column(length = 999)
    private String tratamiento;

    @Column(length = 999)
    private String evolucion;

    private Integer presArterial;
    private Integer frecCardiaca;
    private Double temperatura;
    private Integer saturacion;
    private Integer frecRespiratoria;
    private Double peso;
    private Double talla;
    private Double supCorporal;

    @Column(length = 20)
    private String estado;

    @Column(length = 20)
    private String usuario;

    @Column(length = 20)
    private String usuarioUp;

    private LocalDate fecUp;
    private Integer presionMax;
}
