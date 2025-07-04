package com.ipor.quimioterapia.gestioncitas.fichapaciente.atencionquimioterapia;

import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPaciente;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/app/atencion-quimioterapia")
public class AtencionQuimioterapiaController {
    @Autowired
    FichaPacienteService fichaPacienteService;


    @PostMapping("/ficha/{idFicha}")
    public ResponseEntity<?> obtenerAtencionQuimioterapia(@PathVariable Long idFicha) {
        try {
            FichaPaciente fichaPaciente = fichaPacienteService.getPorID(idFicha);

            AtencionQuimioterapia atencion = fichaPaciente.getAtencionQuimioterapia();
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "enfermeraId", atencion.getEnfermera() != null ? atencion.getEnfermera().getIdPersona() : "",
                    "medicoId", atencion.getMedico() != null ? atencion.getMedico().getIdPersona() : "",
                    "cubiculoId", atencion.getCubiculo() != null ? atencion.getCubiculo().getId() : ""
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }



}
