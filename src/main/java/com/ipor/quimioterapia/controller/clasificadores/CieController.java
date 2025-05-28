package com.ipor.quimioterapia.controller.clasificadores;

import com.ipor.quimioterapia.service.fixed.CieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/clasificadores/cie")
public class CieController {

    @Autowired
    CieService cieService;

    @PostMapping("/nuevo")
    public ResponseEntity<?> crear(@RequestParam String codigo, @RequestParam String descripcion) {
        cieService.crear(codigo, descripcion);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                        @RequestParam String codigo,
                                        @RequestParam String descripcion) {
        cieService.actualizar(id, codigo, descripcion);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/activar/{id}")
    public ResponseEntity<?> activar(@PathVariable Long id) {
        cieService.cambiarEstado(id, true);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/desactivar/{id}")
    public ResponseEntity<?> desactivar(@PathVariable Long id) {
        cieService.cambiarEstado(id, false);
        return ResponseEntity.ok().build();
    }
}
