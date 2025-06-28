package com.ipor.quimioterapia.gestioncitas.botones;

import com.ipor.quimioterapia.dto.*;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPaciente;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.atencionquimioterapia.AtencionQuimioterapia;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.cita.Cita;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.cita.EstadoCita;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.paciente.Paciente;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.paciente.PacienteService;
import com.ipor.quimioterapia.recursos.cubiculo.Cubiculo;
import com.ipor.quimioterapia.recursos.cubiculo.CubiculoService;
import com.ipor.quimioterapia.recursos.personal.enfermera.Enfermera;
import com.ipor.quimioterapia.recursos.personal.enfermera.EnfermeraService;
import com.ipor.quimioterapia.recursos.personal.medico.Medico;
import com.ipor.quimioterapia.restricciones.RestriccionService;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.atencionquimioterapia.AtencionQuimioterapiaService;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.cita.CitaService;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPacienteService;
import com.ipor.quimioterapia.recursos.personal.medico.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
//@RequestMapping("/app/protocolo")

@RestController
@RequestMapping("/app/gestion-citas/boton")
public class GestionCitaController {
    @Autowired
    CitaService citaService;
    @Autowired
    FichaPacienteService fichaPacienteService;
    @Autowired
    AtencionQuimioterapiaService atencionQuimioterapiaService;
    @Autowired
    MedicoService medicoService;
    @Autowired
    RestriccionService restriccionService;
    @Autowired
    CubiculoService cubiculoService;
    @Autowired
    EnfermeraService enfermeraService;
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

    @PostMapping("/asignar")
    public ResponseEntity<?> guardarAtencionQuimioterapia(@RequestBody AtencionQuimioterapiaDTO dto) {
        try {
            FichaPaciente fichaAsignacion = fichaPacienteService.getPorID(dto.getIdFicha());
            Medico medico = medicoService.getPorID(dto.getIdMedico());
            Enfermera enfermera = enfermeraService.getPorID(dto.getIdEnfermera());
            Cubiculo cubiculo = cubiculoService.getPorID(dto.getIdCubiculo());
            AtencionQuimioterapia resultado = atencionQuimioterapiaService.crearOActualizar(dto, fichaAsignacion, medico, enfermera, cubiculo);
            fichaAsignacion.setAtencionQuimioterapia(resultado);
            fichaPacienteService.guardar(fichaAsignacion);

            restriccionService.restriccionesAsignar(fichaAsignacion);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", fichaAsignacion.getAtencionQuimioterapia() != null ? "Protocolo actualizado correctamente" : "Protocolo creado correctamente"
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error al guardar Protocolo Atención: Debe completar todos los campos"));
        }
    }


    @PostMapping("/iniciar")
    public ResponseEntity<?> iniciarProtocolo(@RequestBody IniciarProtocoloDTO iniciarProtocoloDTO) {
        try {
            FichaPaciente fichaPaciente = fichaPacienteService.getPorID(iniciarProtocoloDTO.getIdFicha());

            if (restriccionService.sePuedeIniciar(fichaPaciente)) {
                citaService.cambiarEstado(EstadoCita.EN_PROCESO, fichaPaciente);
                atencionQuimioterapiaService.iniciarProtocolo(iniciarProtocoloDTO.getHoraInicio(), fichaPaciente);

                return ResponseEntity.ok(Map.of("message", "Protocolo iniciado correctamente"));
            } else {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Map.of("message", "Ya existe una atención en curso para este horario y cubículo. No se puede iniciar el protocolo."));
            }

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error al iniciar protocolo: " + e.getMessage()));
        }
    }



    @PostMapping("/atender")
    public ResponseEntity<?> finalizarProtocolo(@RequestBody FinalizarProtocoloDTO finalizarProtocoloDTO) {
        try {
            FichaPaciente fichaPaciente = fichaPacienteService.getPorID(finalizarProtocoloDTO.getIdFicha());

            citaService.cambiarEstado(EstadoCita.ATENDIDO, fichaPaciente);
            atencionQuimioterapiaService.finalizarProtocolo(finalizarProtocoloDTO.getHoraFin(), fichaPaciente);
            return ResponseEntity.ok(Map.of("message", "Protocolo Iniciado correctamente"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error al iniciar protocolo: " + e.getMessage()));
        }
    }




    @PostMapping("/pendiente")
    public ResponseEntity<?> regresarProtocoloPendiente(@RequestBody Map<String, Long> body) {
        try {
            Long idFicha = body.get("idFicha");
            FichaPaciente fichaPaciente = fichaPacienteService.getPorID(idFicha);

            citaService.cambiarEstado(EstadoCita.PENDIENTE, fichaPaciente);
            atencionQuimioterapiaService.pendienteProtocolo(fichaPaciente);

            restriccionService.restriccionesAsignar(fichaPaciente);

            return ResponseEntity.ok(Map.of("message", "Protocolo regresado a Pendiente correctamente"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error al regresar protocolo a Pendiente: " + e.getMessage()));
        }
    }


    @PostMapping("/reprogramar")
    public ResponseEntity<?> reprogramarCita(@RequestBody ReprogramacionDTO dto) {
        try {
            Medico medico = medicoService.getPorID(dto.getIdMedico());
            FichaPaciente fichaPaciente = fichaPacienteService.getPorID(dto.getIdFicha());
            Cita cita = fichaPaciente.getCita();
            citaService.reprogramar(cita, dto.getFecha(), dto.getHora(), medico);

            if (cita.getEstado() == EstadoCita.PENDIENTE || cita.getEstado() == EstadoCita.EN_CONFLICTO){
                atencionQuimioterapiaService.reprogramarCita(fichaPaciente);
                restriccionService.restriccionesReprogramacion(fichaPaciente);
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
