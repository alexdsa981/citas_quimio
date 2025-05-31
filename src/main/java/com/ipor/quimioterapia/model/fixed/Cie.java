package com.ipor.quimioterapia.model.fixed;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Cie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String codigo;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private Boolean isActive;
    @Column(nullable = false)
    private LocalDate fechaActualizacion;
    @Column(nullable = false)
    private LocalTime horaActualizacion;
}
