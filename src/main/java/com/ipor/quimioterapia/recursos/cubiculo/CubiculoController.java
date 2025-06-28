package com.ipor.quimioterapia.recursos.cubiculo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/clasificadores/cubiculo")
public class CubiculoController {

    @Autowired
    CubiculoService cubiculoService;

    @PostMapping("/nuevo")
    public ResponseEntity<?> crear(@RequestParam String nombre) {
        cubiculoService.crear(nombre);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestParam String nombre) {
        cubiculoService.actualizar(id, nombre);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/activar/{id}")
    public ResponseEntity<?> activar(@PathVariable Long id) {
        cubiculoService.cambiarEstado(id, true);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/desactivar/{id}")
    public ResponseEntity<?> desactivar(@PathVariable Long id) {
        cubiculoService.cambiarEstado(id, false);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Cubiculo>> listar() {
        return ResponseEntity.ok(cubiculoService.getLista());
    }

}
