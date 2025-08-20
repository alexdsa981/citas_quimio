package com.ipor.quimioterapia.gestioncitas.botones.reprogramar;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReprogramacionCitaDTO {
    private Long idFichaSeleccionada;
    private Long idMotivo;
    private String descripcion;
}