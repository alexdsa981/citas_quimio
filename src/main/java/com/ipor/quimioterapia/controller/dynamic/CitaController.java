package com.ipor.quimioterapia.controller.dynamic;

import com.ipor.quimioterapia.model.dynamic.*;
import com.ipor.quimioterapia.model.other.DTO.CitaCreadaDTO;
import com.ipor.quimioterapia.model.other.DTO.ReprogramacionDTO;
import com.ipor.quimioterapia.service.dynamic.*;
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
    @Autowired
    CitaService citaService;

    @Autowired
    MedicoService medicoService;
    @Autowired
    AtencionQuimioterapiaService atencionQuimioterapiaService;
    @Autowired
    PacienteService pacienteService;

    @PostMapping("/agendar")
    public ResponseEntity<?> guardarCita(@RequestBody CitaCreadaDTO citaCreadaDTO) {
        try {
            Medico medico = medicoService.getPorID(citaCreadaDTO.medicoId);

            Paciente paciente = pacienteService.crearOActualizar(citaCreadaDTO);

            Cita cita = citaService.crear(citaCreadaDTO, medico, paciente);
            FichaPaciente fichaPaciente = fichaPacienteService.crear(cita);
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
                    "medicoId", cita.getMedicoConsulta().getIdPersona()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }


    @PostMapping("/reprogramar")
    public ResponseEntity<?> reprogramarCita(@RequestBody ReprogramacionDTO dto) {
        try {
            Medico medico = medicoService.getPorID(dto.getIdMedico());
            FichaPaciente fichaPaciente = fichaPacienteService.getPorID(dto.getIdFicha());
            Cita cita = fichaPaciente.getCita();
            citaService.reprogramar(cita, dto.getFecha(), dto.getHora(), medico);

            if (cita.getEstado() == EstadoCita.PENDIENTE){
                atencionQuimioterapiaService.reprogramarCita(fichaPaciente);
            }

            citaService.cambiarEstado(EstadoCita.NO_ASIGNADO, fichaPaciente);

            return ResponseEntity.ok(Map.of("message", "Cita reprogramada correctamente"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error al reprogramar cita: " + e.getMessage()));
        }
    }

    @PostMapping("/cancelar")
    public ResponseEntity<?> cancelarCita(@RequestBody Long idficha) {
        try {
            FichaPaciente fichaPaciente = fichaPacienteService.getPorID(idficha);
            fichaPaciente.setIsActive(Boolean.FALSE);
            citaService.cambiarEstado(EstadoCita.CANCELADO, fichaPaciente);
            fichaPacienteService.guardar(fichaPaciente);
            return ResponseEntity.ok(Map.of("message", "Cita cancelada correctamente"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error al cancelar cita: " + e.getMessage()));
        }
    }






}
