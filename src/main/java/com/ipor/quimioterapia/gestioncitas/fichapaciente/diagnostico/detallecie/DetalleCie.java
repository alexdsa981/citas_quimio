package com.ipor.quimioterapia.gestioncitas.fichapaciente.diagnostico.detallecie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPaciente;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.diagnostico.cie.Cie;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class DetalleCie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_cie")
    private Cie cie;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_ficha_paciente")
    private FichaPaciente fichaPaciente;

}
