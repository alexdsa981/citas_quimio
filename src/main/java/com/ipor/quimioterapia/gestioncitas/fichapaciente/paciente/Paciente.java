package com.ipor.quimioterapia.gestioncitas.fichapaciente.paciente;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Paciente {

    @Id
    private Long idPaciente;

    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String nombreCompleto;

    private String fechaNacimiento;

    private String sexo;

    private String numCelular;

    private String tipoDocumentoNombre;
    private String numDocIdentidad;
    private String telefono;
    private Integer edad;

}

