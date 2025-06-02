package com.ipor.quimioterapia.model.dynamic;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class FuncionesVitales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_ficha_paciente")
    private FichaPaciente fichaPaciente;

    @Column(nullable = false)
    private Boolean estado;
    @Column(nullable = false)
    private LocalDate fecha;
    @Column(nullable = false)
    private LocalTime hora;
    @Column(nullable = false)
    private Double presionArterial;
    @Column(nullable = false)
    private Double presionArterialMax;
    @Column(nullable = false)
    private Double frecuenciaCardiaca;
    @Column(nullable = false)
    private Double temperatura;
    @Column(nullable = false)
    private Double saturacion;
    @Column(nullable = false)
    private Double frecuenciaRespiratoria;
    @Column(nullable = false)
    private Double pesoKg;
    @Column(nullable = false)
    private Double tallaCm;
    @Column(nullable = false)
    private Double superficieCorporal;

}
