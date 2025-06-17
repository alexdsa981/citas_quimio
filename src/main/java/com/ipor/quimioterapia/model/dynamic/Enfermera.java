package com.ipor.quimioterapia.model.dynamic;

import com.ipor.quimioterapia.model.fixed.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
