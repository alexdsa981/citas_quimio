package com.ipor.quimioterapia.model.fixed;

import com.ipor.quimioterapia.model.dynamic.Medico;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Aseguradora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private Boolean isActive;
    @ManyToOne
    @JoinColumn(name = "id_tipo_paciente")
    private TipoPaciente tipoPaciente;

}