package com.ipor.quimioterapia.controller.dynamic;


import com.ipor.quimioterapia.model.dynamic.FichaPaciente;
import com.ipor.quimioterapia.model.dynamic.FuncionesVitales;
import com.ipor.quimioterapia.model.other.DTO.DetalleQuimioterapiaDTO;
import com.ipor.quimioterapia.model.other.DTO.FuncionesVitalesDTO;
import com.ipor.quimioterapia.service.dynamic.DetalleQuimioterapiaService;
import com.ipor.quimioterapia.service.dynamic.FichaPacienteService;
import com.ipor.quimioterapia.service.dynamic.FuncionesVitalesService;
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

        fichaPaciente.setDetalleQuimioterapia(detalleQuimioterapiaService.guardar(dto, fichaPaciente));

        fichaPacienteService.guardar(fichaPaciente);
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Signos vitales guardados correctamente");
        return ResponseEntity.ok(response);
    }


}
