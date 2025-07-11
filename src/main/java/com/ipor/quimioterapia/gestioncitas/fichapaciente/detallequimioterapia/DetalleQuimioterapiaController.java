package com.ipor.quimioterapia.gestioncitas.fichapaciente.detallequimioterapia;


import com.ipor.quimioterapia.gestioncitas.dto.DetalleQuimioterapiaDTO;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPaciente;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/app/detalle-quimioterapia")
public class DetalleQuimioterapiaController {

    @Autowired
    FichaPacienteService fichaPacienteService;
    @Autowired
    DetalleQuimioterapiaService detalleQuimioterapiaService;

    @PostMapping("/guardar")
    public ResponseEntity<Map<String, Object>> guardarDetalleQuimioterapia(@RequestBody DetalleQuimioterapiaDTO dto) {
        FichaPaciente fichaPaciente = fichaPacienteService.getPorID(dto.getIdFicha());

        boolean esNuevo = (fichaPaciente.getDetalleQuimioterapia() == null);

        DetalleQuimioterapia detalle = detalleQuimioterapiaService.guardar(dto, fichaPaciente);

        if (esNuevo) {
            fichaPaciente.setDetalleQuimioterapia(detalle);
            fichaPacienteService.save(fichaPaciente);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Detalle guardado correctamente");
        return ResponseEntity.ok(response);
    }



}
