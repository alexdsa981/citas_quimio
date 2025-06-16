package com.ipor.quimioterapia.model.dynamic;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
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
    @JoinColumn(name = "id_atencion_quimioterapia")
    private AtencionQuimioterapia atencionQuimioterapia;
    @ManyToOne
    @JoinColumn(name = "id_detalle_quimioterapia")
    private DetalleQuimioterapia detalleQuimioterapia;

    @ManyToOne
    @JoinColumn(name = "id_conjunto_cie")
    private ConjuntoCie conjuntoCie;

    @OneToMany(mappedBy = "fichaPaciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FuncionesVitales> funcionesVitales;





}
