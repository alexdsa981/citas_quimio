package com.ipor.quimioterapia.controller.dynamic;

import com.ipor.quimioterapia.SpringDTO.PacienteDesdeGarantiaDTO;
import com.ipor.quimioterapia.model.dynamic.Paciente;
import com.ipor.quimioterapia.model.other.DTO.ActualizarPacienteDTO;
import com.ipor.quimioterapia.model.other.DTO.BusquedaPacienteDTO;
import com.ipor.quimioterapia.service.dynamic.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/app/paciente")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;


    @GetMapping("/desde-garantia/{id}")
    public ResponseEntity<PacienteDesdeGarantiaDTO> getPaciente(@PathVariable Long id) {
        PacienteDesdeGarantiaDTO paciente = pacienteService.obtenerPacienteDesdeGarantia(id);
        if (paciente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(paciente);
    }

}
