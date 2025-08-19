package com.ipor.quimioterapia.gestioncitas.botones.reprogramar;

import com.ipor.quimioterapia.gestioncitas.fichapaciente.cita.Cita;
import com.ipor.quimioterapia.recursos.cubiculo.Cubiculo;
import com.ipor.quimioterapia.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Reprogramacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "id_motivo_reprogramacion")
    private MotivoReprogramacion motivoReprogramacion;

    @OneToOne
    @JoinColumn(name = "id_cita")
    private Cita cita;

    @ManyToOne
    @JoinColumn(name = "id_usuario_creador")
    private Usuario usuario;

    @Column(nullable = false)
    private Boolean isActive;
}
