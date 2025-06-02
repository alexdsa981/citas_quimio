package com.ipor.quimioterapia.model.dynamic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ipor.quimioterapia.model.fixed.TipoEntrada;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class FichaPaciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaCreacion;
    private LocalTime horaCreacion;


    @OneToOne
    @JoinColumn(name = "id_cita")
    private Cita cita;

    @ManyToOne
    @JoinColumn(name = "id_tipo_entrada")
    private TipoEntrada tipoEntrada;

    @ManyToOne
    @JoinColumn(name = "id_enfermera")
    private Enfermera enfermera;
    @ManyToOne
    @JoinColumn(name = "id_medico")
    private Medico medico;


    @OneToMany(mappedBy = "fichaPaciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FuncionesVitales> funcionesVitales;


    @OneToMany(mappedBy = "fichaPaciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConjuntoCie> conjuntoCies;


    @OneToMany(mappedBy = "fichaPaciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AtencionQuimioterapia> atencionQuimioterapiaList;


}
