package com.ipor.quimioterapia.gestioncitas.botones;

import com.ipor.quimioterapia.core.websocket.WSNotificacionesService;
import com.ipor.quimioterapia.gestioncitas.dto.*;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPaciente;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.atencionquimioterapia.AtencionQuimioterapia;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.cita.Cita;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.cita.EstadoCita;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.detallequimioterapia.DetalleQuimioterapia;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.detallequimioterapia.DetalleQuimioterapiaService;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.paciente.Paciente;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.paciente.PacienteService;
import com.ipor.quimioterapia.recursos.cubiculo.Cubiculo;
import com.ipor.quimioterapia.recursos.cubiculo.CubiculoService;
import com.ipor.quimioterapia.recursos.personal.enfermera.Enfermera;
import com.ipor.quimioterapia.recursos.personal.enfermera.EnfermeraService;
import com.ipor.quimioterapia.recursos.personal.medico.Medico;
//import com.ipor.quimioterapia.restricciones.RestriccionService;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.atencionquimioterapia.AtencionQuimioterapiaService;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.cita.CitaService;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPacienteService;
import com.ipor.quimioterapia.recursos.personal.medico.MedicoService;
import com.ipor.quimioterapia.usuario.UsuarioService;
import com.ipor.quimioterapia.usuario.rol.RolUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

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
    //@Autowired
    //RestriccionService restriccionService;
    @Autowired
    CubiculoService cubiculoService;
    @Autowired
    EnfermeraService enfermeraService;
    @Autowired
    PacienteService pacienteService;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    RolUsuarioRepository rolUsuarioRepository;
    @Autowired
    DetalleQuimioterapiaService detalleQuimioterapiaService;

    @Autowired
    WSNotificacionesService wsNotificacionesService;

    @PostMapping("/agendar")
    public ResponseEntity<?> guardarCita(@RequestBody CitaCreadaDTO citaCreadaDTO) {
        try {
            // Validar fecha
            LocalDate fechaCita = citaCreadaDTO.fechaCita;
            LocalDate hoy = LocalDate.now();

            if (fechaCita.isBefore(hoy)) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", "No se puede agendar una cita en una fecha pasada."));
            }

            Medico medico = medicoService.getPorID(citaCreadaDTO.medicoId);
            Paciente paciente = pacienteService.crearOActualizar(citaCreadaDTO);

            DetalleQuimioterapia detalleQuimioterapia = new DetalleQuimioterapia();
            detalleQuimioterapia.setMedicinas(citaCreadaDTO.medicamentos);
            detalleQuimioterapia.setObservaciones(citaCreadaDTO.observaciones);
            detalleQuimioterapia.setTratamiento(citaCreadaDTO.tratamiento);
            detalleQuimioterapiaService.save(detalleQuimioterapia);

            Cita cita = citaService.crear(citaCreadaDTO, medico, paciente);
            FichaPaciente fichaPaciente = fichaPacienteService.crear(cita, detalleQuimioterapia);
            fichaPacienteService.save(fichaPaciente);

            wsNotificacionesService.notificarActualizacionTabla();
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

            if (fichaAsignacion.getCita().getEstado() == EstadoCita.ATENDIDO) {
                return ResponseEntity.ok(Map.of(
                        "success", false,
                        "message", "La atención para esta ficha ya fue completada previamente."
                ));
            } else if (fichaAsignacion.getCita().getEstado() == EstadoCita.EN_PROCESO) {
                return ResponseEntity.ok(Map.of(
                        "success", false,
                        "message", "La atención ya se encuentra en curso."
                ));
            } else {
                Medico medico = medicoService.getPorID(dto.getIdMedico());
                Enfermera enfermera = enfermeraService.getPorID(dto.getIdEnfermera());
                Cubiculo cubiculo = cubiculoService.getPorID(dto.getIdCubiculo());

                AtencionQuimioterapia resultado = atencionQuimioterapiaService.crearOActualizar(dto, fichaAsignacion, medico, enfermera, cubiculo);
                fichaAsignacion.setAtencionQuimioterapia(resultado);
                fichaPacienteService.save(fichaAsignacion);

                //restriccionService.restriccionesAsignar(fichaAsignacion);
                citaService.cambiarEstado(EstadoCita.PENDIENTE, fichaAsignacion);
                wsNotificacionesService.notificarActualizacionTabla();

                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "message", fichaAsignacion.getAtencionQuimioterapia() != null
                                ? "El protocolo fue actualizado exitosamente."
                                : "El protocolo fue registrado exitosamente."
                ));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", false,
                    "message", "Se produjo un error al intentar registrar la atención. Por favor, asegúrese de completar todos los campos requeridos."
            ));
        }
    }



    @PostMapping("/iniciar")
    public ResponseEntity<?> iniciarProtocolo(@RequestBody IniciarProtocoloDTO iniciarProtocoloDTO) {
        try {
            FichaPaciente fichaPaciente = fichaPacienteService.getPorID(iniciarProtocoloDTO.getIdFicha());

            if (fichaPaciente.getCita().getEstado() == EstadoCita.NO_ASIGNADO) {
                return ResponseEntity.ok(Map.of(
                        "status", "NO_ASIGNADO",
                        "message", "No se puede iniciar el protocolo. El paciente aún no ha sido asignado a una atención."
                ));
            }

            if (fichaPaciente.getCita().getEstado() == EstadoCita.ATENDIDO) {
                return ResponseEntity.ok(Map.of(
                        "status", "YA_ATENDIDO",
                        "message", "La atención para este paciente ya ha sido registrada previamente."
                ));
            }


            citaService.cambiarEstado(EstadoCita.EN_PROCESO, fichaPaciente);
            atencionQuimioterapiaService.iniciarProtocolo(iniciarProtocoloDTO.getHoraInicio(), fichaPaciente);

            wsNotificacionesService.notificarActualizacionTabla();

            return ResponseEntity.ok(Map.of(
                    "status", "INICIO_OK",
                    "message", "La atención ha sido iniciada correctamente."
            ));

//            if (restriccionService.sePuedeIniciar(fichaPaciente)) {
//                citaService.cambiarEstado(EstadoCita.EN_PROCESO, fichaPaciente);
//                atencionQuimioterapiaService.iniciarProtocolo(iniciarProtocoloDTO.getHoraInicio(), fichaPaciente);
//
//                wsNotificacionesService.notificarActualizacionTabla();
//
//                return ResponseEntity.ok(Map.of(
//                        "status", "INICIO_OK",
//                        "message", "La atención ha sido iniciada correctamente."
//                ));
//            } else {
//                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
//                        "status", "EN_CURSO",
//                        "message", "Ya existe una atención en curso en el mismo horario y cubículo. No se puede iniciar el protocolo."
//                ));
//            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "status", "ERROR",
                    "message", "Error al iniciar el protocolo: " + e.getMessage()
            ));
        }
    }




    @PostMapping("/atender")
    public ResponseEntity<?> finalizarProtocolo(@RequestBody FinalizarProtocoloDTO finalizarProtocoloDTO) {
        try {
            FichaPaciente fichaPaciente = fichaPacienteService.getPorID(finalizarProtocoloDTO.getIdFicha());

            if (fichaPaciente.getCita().getEstado() == EstadoCita.EN_PROCESO) {
                citaService.cambiarEstado(EstadoCita.ATENDIDO, fichaPaciente);
                atencionQuimioterapiaService.finalizarProtocolo(finalizarProtocoloDTO.getHoraFin(), fichaPaciente);
                //restriccionService.comprobarDespuesDeAtendido(fichaPaciente);
                wsNotificacionesService.notificarActualizacionTabla();

                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "message", "El protocolo ha sido finalizado satisfactoriamente."
                ));
            }

            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "success", false,
                    "message", "No es posible finalizar el protocolo, ya que la atención aún no ha sido iniciada."
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", "Ocurrió un error inesperado al finalizar el protocolo. Detalles: " + e.getMessage()
            ));
        }
    }





    @PostMapping("/retroceso")
    public ResponseEntity<?> regresarProtocoloPendiente(@RequestBody Map<String, Long> body) {
        try {
            Long idFicha = body.get("idFicha");
            FichaPaciente fichaPaciente = fichaPacienteService.getPorID(idFicha);
            AtencionQuimioterapia atencionQuimioterapia = fichaPaciente.getAtencionQuimioterapia();
            Cita cita = fichaPaciente.getCita();

            EstadoCita estado = cita.getEstado();

            // No retroceder si ya está en el estado más inicial
            if (estado == EstadoCita.NO_ASIGNADO) {

                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                        "status", "NO_ASIGNADO",
                        "message", "La cita ya se encuentra en el estado más inicial ('No Asignado'). No es posible retroceder más."
                ));
            }

            if (estado == EstadoCita.ATENDIDO) {
                Long idRol = usuarioService.getUsuarioPorId(usuarioService.getIDdeUsuarioLogeado()).getRolUsuario().getId();

//                if (idRol != 2 && idRol != 3) {
//                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
//                            "status", "NO_AUTORIZADO",
//                            "message", "No cuenta con los permisos necesarios para retroceder una atención finalizada. Por favor, contacte al supervisor o al administrador."
//                    ));
//                }

                // Borrar horas
                atencionQuimioterapia.setHoraFin(null);

                // Cambiar estado explícitamente antes de guardar
                citaService.cambiarEstado(EstadoCita.EN_PROCESO, fichaPaciente);

                // Evaluar conflictos
                //  restriccionService.comprobarDespuesDeRetroceso(fichaPaciente);

            } else if (estado == EstadoCita.EN_PROCESO) {
                Long idRol = usuarioService.getUsuarioPorId(usuarioService.getIDdeUsuarioLogeado()).getRolUsuario().getId();

                // Borrar horas
                atencionQuimioterapia.setHoraInicio(null);
                // Cambiar estado explícitamente antes de guardar
                citaService.cambiarEstado(EstadoCita.PENDIENTE, fichaPaciente);

                // Evaluar conflictos
                //  restriccionService.comprobarDespuesDeRetroceso(fichaPaciente);
            }
            else if (estado == EstadoCita.PENDIENTE || estado == EstadoCita.EN_CONFLICTO) {
                atencionQuimioterapia.setCubiculo(null);
                atencionQuimioterapia.setEnfermera(null);
                atencionQuimioterapia.setMedico(null);
                // Cambiar estado y guardar correctamente
                citaService.cambiarEstado(EstadoCita.NO_ASIGNADO, fichaPaciente);

                //  restriccionService.comprobarDespuesDeRetroceso(fichaPaciente);
            }

            // Guardar solo la atención, la cita ya fue guardada en cambiarEstado
            atencionQuimioterapiaService.save(atencionQuimioterapia);
            wsNotificacionesService.notificarActualizacionTabla();

            return ResponseEntity.ok(Map.of(
                    "status", "CAMBIO_OK",
                    "message", "La atención fue retrocedida exitosamente al estado anterior."
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "ERROR",
                    "message", "Se produjo un error al intentar retroceder la atención: " + e.getMessage()
            ));
        }
    }


    @PostMapping("/reprogramar")
    public ResponseEntity<?> reprogramarCita(@RequestBody ReprogramacionDTO dto) {
        try {
            FichaPaciente fichaPaciente = fichaPacienteService.getPorID(dto.getIdFicha());
            Cita cita = fichaPaciente.getCita();

            if (cita.getEstado() == EstadoCita.ATENDIDO) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                        "success", false,
                        "message", "La cita ya ha sido atendida, por lo tanto no puede ser editada."
                ));
            } else if (cita.getEstado() == EstadoCita.EN_PROCESO) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                        "success", false,
                        "message", "La atención se encuentra en proceso y no es posible realizar una edición."
                ));
            }

            // ✅ Validación de fecha pasada
            LocalDate fechaNueva = (dto.getFecha());
            LocalDate hoy = LocalDate.now();
            if (fechaNueva.isBefore(hoy)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                        "success", false,
                        "message", "No se puede editar la cita a una fecha pasada."
                ));
            }

            // Si pasa las validaciones, continúa la reprogramación
            Medico medico = medicoService.getPorID(dto.getIdMedico());
            citaService.reprogramar(cita, dto.getFecha(), dto.getHora(), medico, dto.getDuracionMinutos(), dto.getAseguradora());

            DetalleQuimioterapia detalleQuimioterapia = fichaPaciente.getDetalleQuimioterapia();
            detalleQuimioterapia.setObservaciones(dto.observaciones);
            detalleQuimioterapia.setTratamiento(dto.tratamiento);
            detalleQuimioterapia.setMedicinas(dto.medicamentos);
            detalleQuimioterapiaService.save(detalleQuimioterapia);

            if (cita.getEstado() == EstadoCita.PENDIENTE || cita.getEstado() == EstadoCita.EN_CONFLICTO) {
                atencionQuimioterapiaService.reprogramarCita(fichaPaciente);
                //  restriccionService.restriccionesReprogramacion(fichaPaciente);
            }

            citaService.cambiarEstado(EstadoCita.NO_ASIGNADO, fichaPaciente);
            wsNotificacionesService.notificarActualizacionTabla();

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "La cita ha sido editado satisfactoriamente."
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", false,
                    "message", "Se produjo un error al intentar editar la cita: " + e.getMessage()
            ));
        }
    }

    @PostMapping("/cancelar")
    public ResponseEntity<?> cancelarCita(@RequestBody Long idficha) {
        try {
            FichaPaciente fichaPaciente = fichaPacienteService.getPorID(idficha);
            fichaPaciente.setIsActive(Boolean.FALSE);
            citaService.cambiarEstado(EstadoCita.CANCELADO, fichaPaciente);
            fichaPacienteService.save(fichaPaciente);

            // restriccionService.comprobarDespuesDeCancelacion(fichaPaciente);


            wsNotificacionesService.notificarActualizacionTabla();

            // El frontend espera que haya una propiedad "success"
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Cita cancelada correctamente"
            ));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "success", false,
                            "message", "Error al cancelar cita: " + e.getMessage()
                    ));
        }
    }



    @PostMapping("/duplicar")
    public ResponseEntity<?> duplicarCita(@RequestBody DuplicarCitaDTO duplicarCitaDTO) {
        try {
            // ✅ Validar que la fecha no sea pasada
            LocalDate fechaDuplicada = duplicarCitaDTO.getFecha();
            LocalDate hoy = LocalDate.now();

            if (fechaDuplicada.isBefore(hoy)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                        "success", false,
                        "message", "No se puede duplicar una cita en una fecha pasada."
                ));
            }

            FichaPaciente fichaActual = fichaPacienteService.getPorID(duplicarCitaDTO.getIdFichaPaciente());
            Cita citaActual = fichaActual.getCita();
            Medico medico = medicoService.getPorID(duplicarCitaDTO.getIdMedico());
            Cita citaNueva = citaService.duplicar(duplicarCitaDTO, citaActual, medico);

            DetalleQuimioterapia detalleQuimioterapia = new DetalleQuimioterapia();
            detalleQuimioterapia.setMedicinas(duplicarCitaDTO.medicamentos);
            detalleQuimioterapia.setObservaciones(duplicarCitaDTO.observaciones);
            detalleQuimioterapia.setTratamiento(duplicarCitaDTO.tratamiento);
            detalleQuimioterapiaService.save(detalleQuimioterapia);

            FichaPaciente fichaNueva = fichaPacienteService.crear(citaNueva, detalleQuimioterapia);
            fichaPacienteService.save(fichaNueva);

            wsNotificacionesService.notificarActualizacionTabla();

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "La cita ha sido duplicada correctamente para el mismo paciente."
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", "Ocurrió un error al duplicar la cita. Por favor, inténtelo nuevamente o contacte con soporte técnico."
            ));
        }
    }



}
