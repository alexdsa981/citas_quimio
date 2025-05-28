package com.ipor.quimioterapia.controller.clasificadores;

import com.ipor.quimioterapia.service.fixed.TipoDocIdentidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/clasificadores/tipodocidentidad")
public class TipoDocIdentidadController {

    @Autowired
    TipoDocIdentidadService tipoDocIdentidadService;

    @PostMapping("/nuevo")
    public ResponseEntity<?> crear(@RequestParam String nombre) {
        tipoDocIdentidadService.crear(nombre);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestParam String nombre) {
        tipoDocIdentidadService.actualizar(id, nombre);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/activar/{id}")
    public ResponseEntity<?> activar(@PathVariable Long id) {
        tipoDocIdentidadService.cambiarEstado(id, true);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/desactivar/{id}")
    public ResponseEntity<?> desactivar(@PathVariable Long id) {
        tipoDocIdentidadService.cambiarEstado(id, false);
        return ResponseEntity.ok().build();
    }
}
