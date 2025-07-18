package com.ipor.quimioterapia.gestioncitas.fichapaciente.cita;

import com.ipor.quimioterapia.gestioncitas.dto.ObtenerDatosCitaDTO;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPaciente;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPacienteService;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.detallequimioterapia.DetalleQuimioterapia;
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
    public ResponseEntity<ObtenerDatosCitaDTO> obtenerCitaPorFicha(@PathVariable Long idFicha) {
        try {
            FichaPaciente fichaPaciente = fichaPacienteService.getPorID(idFicha);
            Cita cita = fichaPaciente.getCita();
            Paciente paciente = fichaPaciente.getPaciente();
            DetalleQuimioterapia detallequimio = fichaPaciente.getDetalleQuimioterapia();

            ObtenerDatosCitaDTO dto = new ObtenerDatosCitaDTO();
            dto.setNumDoc(paciente.getNumDocIdentidad());
            dto.setTipoDoc(paciente.getTipoDocumentoNombre());
            dto.setFechaCita(cita.getFecha());
            dto.setHoraCita(cita.getHoraProgramada());
            dto.setMedicoId(cita.getMedicoConsulta().getIdPersona());
            dto.setNombrePaciente(paciente.getNombreCompleto());
            dto.setDuracion(cita.getDuracionMinutosProtocolo() != null ? cita.getDuracionMinutosProtocolo() : 0);
            dto.setAseguradora(cita.getAseguradora());
            dto.setTratamiento(detallequimio.getTratamiento() != null ? detallequimio.getTratamiento() : "");
            dto.setObservaciones(detallequimio.getObservaciones() != null ? detallequimio.getObservaciones() : "");
            dto.setMedicina(detallequimio.getMedicinas() != null ? detallequimio.getMedicinas() : "");

            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
