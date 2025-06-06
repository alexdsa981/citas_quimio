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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellidoP;
    @Column(nullable = false)
    private String apellidoM;
    @Column(nullable = false)
    private Boolean isActive;

}
