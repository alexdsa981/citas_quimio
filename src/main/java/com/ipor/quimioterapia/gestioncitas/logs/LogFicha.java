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
//@Entity
public class LogFicha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaHora;
    private FichaPaciente fichaPaciente;
    private Usuario usuario;
    private String seccion; //especificar seccion cambiante ej: FuncionesVitales
    private String valorActual;
    private String valorNuevo;
    private String descripcion; // Opcional

    @PrePersist
    public void prePersist() {
        this.fechaHora = LocalDateTime.now();
    }
}
