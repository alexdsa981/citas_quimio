package com.ipor.quimioterapia.controller.fixed;

import com.ipor.quimioterapia.model.fixed.Aseguradora;
import com.ipor.quimioterapia.service.fixed.AseguradoraService;
import com.ipor.quimioterapia.service.fixed.TipoPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/clasificadores/tipopaciente")
public class TipoPacienteController {

    @Autowired
    TipoPacienteService tipoPacienteService;

    @Autowired
    AseguradoraService aseguradoraService;

    @GetMapping("/lista-aseguradoras/{id}")
    @ResponseBody
    public List<Aseguradora> getAseguradoras(@PathVariable Long id) {
        return aseguradoraService.getPorTipoPaciente(id);
    }

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
