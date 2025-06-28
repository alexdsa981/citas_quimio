package com.ipor.quimioterapia.gestioncitas.fichapaciente.diagnostico.cie;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Cie {
    @Id
    private Long id;
    private String codigo;
    private String descripcion;
}
