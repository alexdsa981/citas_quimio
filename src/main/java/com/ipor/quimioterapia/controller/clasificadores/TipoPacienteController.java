package com.ipor.quimioterapia.controller.clasificadores;

import com.ipor.quimioterapia.service.fixed.TipoPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/clasificadores/tipopaciente")
public class TipoPacienteController {

    @Autowired
    TipoPacienteService tipoPacienteService;

    @PostMapping("/nuevo")
    public ResponseEntity<?> crear(@RequestParam String nombre) {
        tipoPacienteService.crear(nombre);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestParam String nombre) {
        tipoPacienteService.actualizar(id, nombre);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/activar/{id}")
    public ResponseEntity<?> activar(@PathVariable Long id) {
        tipoPacienteService.cambiarEstado(id, true);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/desactivar/{id}")
    public ResponseEntity<?> desactivar(@PathVariable Long id) {
        tipoPacienteService.cambiarEstado(id, false);
        return ResponseEntity.ok().build();
    }
}
