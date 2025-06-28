package com.ipor.quimioterapia.spring.paciente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/paciente")
public class PacienteSpringController {

    @Autowired
    private PacienteSpringService pacienteSpringService;

    @GetMapping("/buscar")
    public ResponseEntity<List<PacienteSpringDTO>> buscarPorNombre(@RequestParam String nombre) {
        List<PacienteSpringDTO> resultados = pacienteSpringService.buscarPacientesPorNombre(nombre);
        return resultados.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(resultados);
    }

    @GetMapping("/buscar-por-documento")
    public ResponseEntity<PacienteSpringDTO> buscarPorDocumento(
            @RequestParam String tipoDocumento,
            @RequestParam String documento) {
        PacienteSpringDTO paciente = pacienteSpringService.buscarPorDocumento(tipoDocumento, documento);
        if (paciente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(paciente);
    }
}
