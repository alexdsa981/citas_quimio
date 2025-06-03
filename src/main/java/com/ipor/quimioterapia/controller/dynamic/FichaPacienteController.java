package com.ipor.quimioterapia.controller.dynamic;

import com.ipor.quimioterapia.model.dynamic.FichaPaciente;
import com.ipor.quimioterapia.model.other.DTO.FiltroFechasDTO;
import com.ipor.quimioterapia.service.dynamic.FichaPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/ficha-paciente")
public class FichaPacienteController {

    @Autowired
    FichaPacienteService fichaPacienteService;

    @GetMapping("/fichas/hoy")
    public ResponseEntity<?> getFichasDelDia() {
        try {
            List<FichaPaciente> fichas = fichaPacienteService.getListaHoy();
            System.out.println(LocalDate.now() + " " + LocalTime.now());
            return ResponseEntity.ok(fichas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error al obtener fichas del día: " + e.getMessage()));
        }
    }

    @PostMapping("/fichas/entre-fechas")
    public ResponseEntity<?> getFichasEntreFechas(@RequestBody FiltroFechasDTO filtro) {
        try {
            List<FichaPaciente> fichas = fichaPacienteService.getListaEntreFechas(filtro.getDesde(), filtro.getHasta());
            System.out.println(filtro.getDesde() + " " + filtro.getHasta());
            return ResponseEntity.ok(fichas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error al obtener fichas entre fechas: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFichaPorId(@PathVariable Long id) {
        try {
            FichaPaciente ficha = fichaPacienteService.getPorID(id);
            if (ficha == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Ficha no encontrada con id " + id));
            }
            return ResponseEntity.ok(ficha);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error al obtener ficha: " + e.getMessage()));
        }
    }







}
