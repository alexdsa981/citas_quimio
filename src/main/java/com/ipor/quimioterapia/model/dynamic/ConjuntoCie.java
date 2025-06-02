package com.ipor.quimioterapia.model.dynamic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ipor.quimioterapia.model.fixed.Cie;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConjuntoCie {
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
