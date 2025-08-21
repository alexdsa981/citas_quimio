package com.ipor.quimioterapia.gestioncitas.botones.reprogramar;

import com.ipor.quimioterapia.recursos.cubiculo.Cubiculo;
import com.ipor.quimioterapia.recursos.cubiculo.CubiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/clasificadores/motivo-reprogramacion")
public class MotivoReprogramacionController {

    @Autowired
    MotivoReprogramacionService motivoReprogramacionService;

    @PostMapping("/nuevo")
    public ResponseEntity<?> crear(@RequestParam String nombre) {
        motivoReprogramacionService.crear(nombre);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestParam String nombre) {
        motivoReprogramacionService.actualizar(id, nombre);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/activar/{id}")
    public ResponseEntity<?> activar(@PathVariable Long id) {
        motivoReprogramacionService.cambiarEstado(id, true);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/desactivar/{id}")
    public ResponseEntity<?> desactivar(@PathVariable Long id) {
        motivoReprogramacionService.cambiarEstado(id, false);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listar")
    public ResponseEntity<List<MotivoReprogramacion>> listar() {
        return ResponseEntity.ok(motivoReprogramacionService.getLista());
    }

}
