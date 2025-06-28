package com.ipor.quimioterapia.recursos.personal.enfermera;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Enfermera {

    @Id
    private Long idPersona;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String nombreCompleto;
    @Column(nullable = false)
    private Boolean isActive;

}
