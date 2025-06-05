package com.ipor.quimioterapia.controller.dynamic;

import com.ipor.quimioterapia.model.dynamic.AtencionQuimioterapia;
import com.ipor.quimioterapia.model.dynamic.Cita;
import com.ipor.quimioterapia.model.dynamic.FichaPaciente;
import com.ipor.quimioterapia.model.dynamic.Medico;
import com.ipor.quimioterapia.model.fixed.TipoEntrada;
import com.ipor.quimioterapia.model.other.DTO.CitaCreadaDTO;
import com.ipor.quimioterapia.model.other.DTO.ReprogramacionDTO;
import com.ipor.quimioterapia.service.dynamic.AtencionQuimioterapiaService;
import com.ipor.quimioterapia.service.dynamic.CitaService;
import com.ipor.quimioterapia.service.dynamic.FichaPacienteService;
import com.ipor.quimioterapia.service.dynamic.MedicoService;
import com.ipor.quimioterapia.service.fixed.TipoEntradaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
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
    @Autowired
    MedicoService medicoService;
    @Autowired
    AtencionQuimioterapiaService atencionQuimioterapiaService;

    @PostMapping("/agendar")
    public ResponseEntity<?> guardarCita(@RequestBody CitaCreadaDTO citaCreadaDTO) {
        try {
            TipoEntrada tipoEntrada = tipoEntradaService.getPorID(citaCreadaDTO.tipoEntradaId);
            Medico medico = medicoService.getPorID(citaCreadaDTO.medicoId);

            Cita cita = citaService.crear(citaCreadaDTO);
            FichaPaciente fichaPaciente = fichaPacienteService.crear(cita, tipoEntrada);
            fichaPaciente.setAtencionQuimioterapia(atencionQuimioterapiaService.crear(medico));
            fichaPacienteService.guardar(fichaPaciente);

            return ResponseEntity.ok(Map.of("message", "Cita agendada correctamente"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error al guardar cita: " + e.getMessage()));
        }
    }


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
                    "medicoId", atencion.getMedico().getId()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }


    @PostMapping("/reprogramar")
    public ResponseEntity<?> reprogramarCita(@RequestBody ReprogramacionDTO dto) {
        try {
            FichaPaciente fichaPaciente = fichaPacienteService.getPorID(dto.getIdFicha());
            Medico medico = medicoService.getPorID(dto.getIdMedico());

            Cita cita = fichaPaciente.getCita();
            citaService.reprogramar(cita, dto.getFecha(), dto.getHora());
            atencionQuimioterapiaService.reprogramarCita(fichaPaciente, medico);

            return ResponseEntity.ok(Map.of("message", "Cita reprogramada correctamente"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error al reprogramar cita: " + e.getMessage()));
        }
    }



}
