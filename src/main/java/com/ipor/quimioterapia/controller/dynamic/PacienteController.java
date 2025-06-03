package com.ipor.quimioterapia.controller.dynamic;

import com.ipor.quimioterapia.model.dynamic.Paciente;
import com.ipor.quimioterapia.model.other.DTO.BusquedaPacienteDTO;
import com.ipor.quimioterapia.model.other.DTO.CitaCreadaDTO;
import com.ipor.quimioterapia.service.dynamic.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/app/paciente")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping("/buscar-por-documento")
    public ResponseEntity<?> obtenerPacientePorDocumento(@RequestBody BusquedaPacienteDTO dto) {
        try {
            Optional<Paciente> optPaciente = pacienteService.getPorDocumento(
                    dto.idTipoDocIdentidad,
                    dto.numeroDocumento
            );

            if (optPaciente.isPresent()) {
                return ResponseEntity.ok(optPaciente.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Paciente no encontrado"));
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error al buscar paciente: " + e.getMessage()));
        }
    }
}
