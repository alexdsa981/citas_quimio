package com.ipor.quimioterapia.controller.dynamic;

import com.ipor.quimioterapia.model.dynamic.Cita;
import com.ipor.quimioterapia.model.fixed.TipoEntrada;
import com.ipor.quimioterapia.model.other.CitaCreadaDTO;
import com.ipor.quimioterapia.service.dynamic.CitaService;
import com.ipor.quimioterapia.service.dynamic.FichaPacienteService;
import com.ipor.quimioterapia.service.fixed.TipoEntradaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/app/cita")
public class CitaController {
    @Autowired
    FichaPacienteService fichaPacienteService;
    @Autowired
    CitaService citaService;
    @Autowired
    TipoEntradaService tipoEntradaService;

    @PostMapping("/agendar")
    public ResponseEntity<?> guardarCita(@RequestBody CitaCreadaDTO citaCreadaDTO) {
        try {
            TipoEntrada tipoEntrada = tipoEntradaService.getPorID(citaCreadaDTO.tipoEntradaId);
            Cita cita = citaService.crear(citaCreadaDTO);
            fichaPacienteService.crear(cita, tipoEntrada);
            return ResponseEntity.ok(Map.of("message", "Cita agendada correctamente"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error al guardar cita: " + e.getMessage()));
        }
    }


}
