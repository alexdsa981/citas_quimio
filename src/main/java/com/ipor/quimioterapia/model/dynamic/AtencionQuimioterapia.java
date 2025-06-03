package com.ipor.quimioterapia.model.dynamic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ipor.quimioterapia.model.fixed.Cubiculo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class AtencionQuimioterapia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_cubiculo")
    private Cubiculo cubiculo;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_ficha_paciente")
    private FichaPaciente fichaPaciente;

    @ManyToOne
    @JoinColumn(name = "id_enfermera")
    private Enfermera enfermera;
    @ManyToOne
    @JoinColumn(name = "id_medico")
    private Medico medico;


    //@Column(nullable = false)
    private Integer duracionMinutosProtocolo;

    //@Column(nullable = false)
    private LocalTime horaInicio;
    //@Column(nullable = false)
    private LocalTime horaFin;

    @Transient
    public Integer getHorasProtocolo() {
        if (duracionMinutosProtocolo == null) return 0;
        return duracionMinutosProtocolo / 60;
    }

    @Transient
    public Integer getMinutosRestantesProtocolo() {
        if (duracionMinutosProtocolo == null) return 0;
        return duracionMinutosProtocolo % 60;
    }


}
