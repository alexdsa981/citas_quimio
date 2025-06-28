package com.ipor.quimioterapia.gestioncitas.fichapaciente.cita;

import com.ipor.quimioterapia.dto.CitaCreadaDTO;
import com.ipor.quimioterapia.dto.ReprogramacionDTO;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPaciente;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.atencionquimioterapia.AtencionQuimioterapia;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.atencionquimioterapia.AtencionQuimioterapiaService;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPacienteService;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.paciente.Paciente;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.paciente.PacienteService;
import com.ipor.quimioterapia.recursos.personal.medico.Medico;
import com.ipor.quimioterapia.recursos.personal.medico.MedicoService;
import com.ipor.quimioterapia.restricciones.RestriccionService;
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



    @PostMapping("/ficha/{idFicha}")
    public ResponseEntity<?> obtenerCitaPorFicha(@PathVariable Long idFicha) {
        try {
            FichaPaciente fichaPaciente = fichaPacienteService.getPorID(idFicha);
            AtencionQuimioterapia atencion = fichaPaciente.getAtencionQuimioterapia();
            Cita cita = fichaPaciente.getCita();
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "fechaCita", cita.getFecha(),
                    "HoraCita", cita.getHoraProgramada(),
                    "medicoId", cita.getMedicoConsulta().getIdPersona()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }








}
