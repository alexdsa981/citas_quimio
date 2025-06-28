package com.ipor.quimioterapia.gestioncitas.fichapaciente.diagnostico;

import com.ipor.quimioterapia.dto.CieCrearDTO;
import com.ipor.quimioterapia.dto.CieGuardarListaDTO;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPacienteService;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPaciente;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.diagnostico.cie.CieService;
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
