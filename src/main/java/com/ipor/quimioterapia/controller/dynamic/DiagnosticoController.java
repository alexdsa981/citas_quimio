package com.ipor.quimioterapia.controller.dynamic;

import com.ipor.quimioterapia.model.dynamic.FichaPaciente;
import com.ipor.quimioterapia.model.other.DTO.CieCrearDTO;
import com.ipor.quimioterapia.model.other.DTO.CieGuardarListaDTO;
import com.ipor.quimioterapia.service.dynamic.FichaPacienteService;
import com.ipor.quimioterapia.service.fixed.CieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/diagnostico")
public class DiagnosticoController {

    @Autowired
    CieService cieService;
    @Autowired
    FichaPacienteService fichaPacienteService;

    @PostMapping("/cie/guardar")
    public ResponseEntity<?> guardarCie(@RequestBody CieCrearDTO dto) {
        cieService.crearOActualizar(dto);
        return ResponseEntity.ok("CIE registrado");
    }

    @PostMapping("/cie/guardar-lista")
    public ResponseEntity<?> guardarCieLista(@RequestBody CieGuardarListaDTO dto) {

        FichaPaciente fichaPaciente = fichaPacienteService.getPorID(dto.getIdFicha());
        cieService.guardarListaDetalleCie(fichaPaciente, dto.getCieIds());
        return ResponseEntity.ok("CIE10 guardados con ID de ficha correctamente.");
    }



}
