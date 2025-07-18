package com.ipor.quimioterapia.gestioncitas.fichapaciente.cita;


import com.ipor.quimioterapia.gestioncitas.fichapaciente.paciente.Paciente;
import com.ipor.quimioterapia.recursos.personal.medico.Medico;
import com.ipor.quimioterapia.usuario.Usuario;
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
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column(nullable = false)
    private LocalDate fecha;
    //@Column(nullable = false)
    private LocalTime horaProgramada;

    @ManyToOne
    @JoinColumn(name = "id_usuario_creador", nullable = false)
    private Usuario usuarioCreacion;

//    private Long numPresupuesto;
//    private String tipoPaciente;
//    private String aseguradora;
//    private String contratante;


    @Enumerated(EnumType.STRING)
    //@Column(nullable = false)
    private EstadoCita estado;

    @Column(nullable = false)
    private String aseguradora;


    @ManyToOne
    @JoinColumn(name = "id_medico")
    private Medico medicoConsulta;




    //@Column(nullable = false)
    private Integer duracionMinutosProtocolo;
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
