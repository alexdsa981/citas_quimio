package com.ipor.quimioterapia.gestioncitas.fichapaciente.diagnostico;

import com.ipor.quimioterapia.gestioncitas.dto.CieCrearDTO;
import com.ipor.quimioterapia.gestioncitas.dto.CieGuardarListaDTO;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPacienteService;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPaciente;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.diagnostico.cie.CieService;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.diagnostico.detallecie.DetalleCie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        List<DetalleCie> lista = cieService.guardarListaDetalleCie(fichaPaciente, dto.getCieIds());

        // O construyes un DTO si necesitas limpiar la respuesta
        return ResponseEntity.ok(Map.of("detalleCies", lista));
    }



}
