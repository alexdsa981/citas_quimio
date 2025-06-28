package com.ipor.quimioterapia.gestioncitas.fichapaciente.atencionquimioterapia;

import com.ipor.quimioterapia.recursos.cubiculo.Cubiculo;
import com.ipor.quimioterapia.recursos.personal.enfermera.Enfermera;
import com.ipor.quimioterapia.recursos.personal.medico.Medico;
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

    @ManyToOne
    @JoinColumn(name = "id_cubiculo")
    private Cubiculo cubiculo;


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
