package com.ipor.quimioterapia.gestioncitas.fichapaciente.cita;

import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPaciente;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPacienteService;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.paciente.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/app/cita")
public class CitaController {
    @Autowired
    FichaPacienteService fichaPacienteService;



    @GetMapping("/ficha/{idFicha}")
    public ResponseEntity<?> obtenerCitaPorFicha(@PathVariable Long idFicha) {
        try {
            FichaPaciente fichaPaciente = fichaPacienteService.getPorID(idFicha);
            Cita cita = fichaPaciente.getCita();
            Paciente paciente = cita.getPaciente();
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "fechaCita", cita.getFecha(),
                    "HoraCita", cita.getHoraProgramada(),
                    "medicoId", cita.getMedicoConsulta().getIdPersona(),
                    "nombrePaciente", paciente.getNombreCompleto(),
                    "duracion", cita.getDuracionMinutosProtocolo() != null ? cita.getDuracionMinutosProtocolo() : 0
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }








}
