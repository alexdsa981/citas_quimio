package com.ipor.quimioterapia.controller.clasificadores;

import com.ipor.quimioterapia.model.fixed.Aseguradora;
import com.ipor.quimioterapia.model.fixed.Contratante;
import com.ipor.quimioterapia.service.fixed.AseguradoraService;
import com.ipor.quimioterapia.service.fixed.ContratanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/clasificadores/aseguradora")
public class AseguradoraController {

    @Autowired
    AseguradoraService aseguradoraService;
    @Autowired
    ContratanteService contratanteService;

    @GetMapping("/lista-contratantes/{id}")
    @ResponseBody
    public List<Contratante> getContratantes(@PathVariable Long id) {
        return contratanteService.getPorAseguradora(id);
    }

    @PostMapping("/nuevo")
    public ResponseEntity<?> crear(@RequestParam String nombre, @RequestParam Long idTipoPaciente) {
        aseguradoraService.crear(nombre, idTipoPaciente);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestParam String nombre) {
        aseguradoraService.actualizar(id, nombre);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/activar/{id}")
    public ResponseEntity<?> activar(@PathVariable Long id) {
        aseguradoraService.cambiarEstado(id, true);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/desactivar/{id}")
    public ResponseEntity<?> desactivar(@PathVariable Long id) {
        aseguradoraService.cambiarEstado(id, false);
        return ResponseEntity.ok().build();
    }
}
