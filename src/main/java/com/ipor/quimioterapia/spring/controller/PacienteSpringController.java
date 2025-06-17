package com.ipor.quimioterapia.spring.controller;

import com.ipor.quimioterapia.spring.dto.PacienteDesdeGarantiaDTO;
import com.ipor.quimioterapia.spring.service.PacienteSpringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/paciente")
public class PacienteSpringController {

    @Autowired
    private PacienteSpringService pacienteSpringService;


    @GetMapping("/desde-garantia/{id}")
    public ResponseEntity<PacienteDesdeGarantiaDTO> getPaciente(@PathVariable Long id) {
        PacienteDesdeGarantiaDTO paciente = pacienteSpringService.obtenerPacienteDesdeGarantia(id);
        if (paciente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(paciente);
    }

}
