package com.ipor.quimioterapia.controller.fixed;

import com.ipor.quimioterapia.service.fixed.CieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/clasificadores/cie")
public class CieController {

    @Autowired
    CieService cieService;

    @GetMapping("/local/activar/{id}")
    public ResponseEntity<?> activar(@PathVariable Long id) {
        cieService.cambiarEstado(id, true);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/local/desactivar/{id}")
    public ResponseEntity<?> desactivar(@PathVariable Long id) {
        cieService.cambiarEstado(id, false);
        return ResponseEntity.ok().build();
    }

}
