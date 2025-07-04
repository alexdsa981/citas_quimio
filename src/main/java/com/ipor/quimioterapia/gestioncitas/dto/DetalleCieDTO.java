package com.ipor.quimioterapia.gestioncitas.dto;

import com.ipor.quimioterapia.gestioncitas.fichapaciente.diagnostico.detallecie.DetalleCie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleCieDTO {
    private Long id;
    private Long cieId;
    private String codigo;
    private String descripcion;

    public DetalleCieDTO(DetalleCie detalle) {
        this.id = detalle.getId();
        this.cieId = detalle.getCie().getId();
        this.codigo = detalle.getCie().getCodigo();
        this.descripcion = detalle.getCie().getDescripcion();
    }
}
