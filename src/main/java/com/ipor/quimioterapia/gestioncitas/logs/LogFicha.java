package com.ipor.quimioterapia.gestioncitas.logs;

import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPaciente;
import com.ipor.quimioterapia.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class LogFicha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "id_ficha_paciente", nullable = false)
    private FichaPaciente fichaPaciente;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    private String accion; //especificar seccion cambiante ej: FuncionesVitales

    @Column(columnDefinition = "TEXT")
    private String valorActual;
    @Column(columnDefinition = "TEXT")
    private String valorNuevo;

    private String descripcion; // Opcional

    @PrePersist
    public void prePersist() {
        this.fechaHora = LocalDateTime.now();
    }
}
