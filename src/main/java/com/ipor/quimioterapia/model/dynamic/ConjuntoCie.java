package com.ipor.quimioterapia.model.dynamic;

import com.ipor.quimioterapia.model.fixed.Cie;
import com.ipor.quimioterapia.model.fixed.TipoDocIdentidad;
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
    @ManyToOne
    @JoinColumn(name = "id_funciones_vitales")
    private FuncionesVitales funcionesVitales;

}
