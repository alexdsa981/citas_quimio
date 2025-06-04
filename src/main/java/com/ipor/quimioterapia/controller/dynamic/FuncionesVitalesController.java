package com.ipor.quimioterapia.controller.dynamic;


import com.ipor.quimioterapia.model.dynamic.FichaPaciente;
import com.ipor.quimioterapia.model.dynamic.FuncionesVitales;
import com.ipor.quimioterapia.model.other.DTO.FuncionesVitalesDTO;
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
@RequestMapping("/app/funciones-vitales")
public class FuncionesVitalesController {

    @Autowired
    FichaPacienteService fichaPacienteService;
    @Autowired
    FuncionesVitalesService funcionesVitalesService;

    @PostMapping("/guardar")
    public ResponseEntity<Map<String, Object>> guardarSignosVitales(@RequestBody FuncionesVitalesDTO dto) {
        FichaPaciente fichaPaciente = fichaPacienteService.getPorID(dto.getIdFicha());

        // Si no hay funciones vitales, se crea una
        if (fichaPaciente.getFuncionesVitales().isEmpty()) {
            funcionesVitalesService.crear(fichaPaciente);
        }

        funcionesVitalesService.setDatoAFicha(dto, fichaPaciente);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Signos vitales guardados correctamente");
        return ResponseEntity.ok(response);
    }


}
