package com.ipor.quimioterapia.gestioncitas.botones;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.ipor.quimioterapia.gestioncitas.logs.AccionLogFicha;
import com.ipor.quimioterapia.gestioncitas.logs.LogService;
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
import com.ipor.quimioterapia.usuario.Usuario;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    LogService logService;
    @Autowired
    private ObjectMapper objectMapper;

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


            //LOG AGENDAR CITA---------------------------------------------------
            Map<String, Object> valorNuevoMap = Map.of(
                    "paciente", fichaPaciente.getCita().getPaciente().getNombreCompleto(),
                    "fechaCita", fichaPaciente.getCita().getFecha(),
                    "aseguradora", fichaPaciente.getCita().getAseguradora(),
                    "medico", fichaPaciente.getCita().getMedicoConsulta().getNombreCompleto(),
                    "horaCita", fichaPaciente.getCita().getHoraProgramada(),
                    "duracionProtocolo", fichaPaciente.getCita().getDuracionMinutosProtocolo(),
                    "medicinas", fichaPaciente.getDetalleQuimioterapia().getMedicinas(),
                    "tratamiento", fichaPaciente.getDetalleQuimioterapia().getTratamiento(),
                    "observacion", fichaPaciente.getDetalleQuimioterapia().getObservaciones()
            );

            String valorNuevoJson = objectMapper.writeValueAsString(valorNuevoMap);


            String descripcionLog = String.format(
                    "Cita agendada por el usuario %s para el paciente %s. Fecha y hora de registro: %s.",
                    usuarioService.getUsuarioLogeado().getNombre(),
                    paciente.getNombreCompleto(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
            );


            logService.saveDeFicha(usuarioService.getUsuarioLogeado(), fichaPaciente,  AccionLogFicha.AGENDAR_CITA, null, valorNuevoJson,descripcionLog);
            //---------------------------------------------------

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

                boolean esNueva = fichaAsignacion.getAtencionQuimioterapia() == null;
                AtencionQuimioterapia resultado = atencionQuimioterapiaService.crearOActualizar(dto, fichaAsignacion, medico, enfermera, cubiculo);
                if (esNueva) {
                    fichaAsignacion.setAtencionQuimioterapia(resultado);
                    fichaPacienteService.save(fichaAsignacion);
                }




                citaService.cambiarEstado(EstadoCita.PENDIENTE, fichaAsignacion);
                wsNotificacionesService.notificarActualizacionTabla();




                //LOG ASIGNAR CITA---------------------------------------------------
                Map<String, Object> valorNuevoMap = Map.of(
                        "enfermera", fichaAsignacion.getAtencionQuimioterapia().getEnfermera().getNombreCompleto(),
                        "cubiculo", fichaAsignacion.getAtencionQuimioterapia().getCubiculo().getCodigo(),
                        "medico", fichaAsignacion.getAtencionQuimioterapia().getMedico().getNombreCompleto()
                );

                String valorNuevoJson = objectMapper.writeValueAsString(valorNuevoMap);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                String descripcionLog = String.format(
                        "El usuario %s realizó la asignación de personal y espacio al paciente %s: enfermera %s, médico %s y cubículo %s. Fecha y hora de la asignación: %s.",
                        usuarioService.getUsuarioLogeado().getNombre(),
                        fichaAsignacion.getCita().getPaciente().getNombreCompleto(),
                        enfermera.getNombreCompleto(),
                        medico.getNombreCompleto(),
                        cubiculo.getCodigo(),
                        LocalDateTime.now().format(formatter)
                );


                logService.saveDeFicha(usuarioService.getUsuarioLogeado(), fichaAsignacion,  AccionLogFicha.ASIGNAR_CITA, null, valorNuevoJson,descripcionLog);
                //---------------------------------------------------





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


            if (fichaPaciente.getCita().getEstado() == EstadoCita.EN_PROCESO) {
                return ResponseEntity.ok(Map.of(
                        "status", "EN_PROCESO",
                        "message", "La atención para este paciente ya se encuentra en proceso."
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





            //LOG INICIAR ATENCION---------------------------------------------------
            Map<String, Object> valorNuevoMap = Map.of(
                    "horaInicio", fichaPaciente.getAtencionQuimioterapia().getHoraInicio()
            );

            String valorNuevoJson = objectMapper.writeValueAsString(valorNuevoMap);


            String descripcionLog = String.format(
                    "El usuario %s registró el inicio de la atención del paciente %s a las %s.",
                    usuarioService.getUsuarioLogeado().getNombre(),
                    fichaPaciente.getCita().getPaciente().getNombreCompleto(),
                    fichaPaciente.getAtencionQuimioterapia().getHoraInicio()
            );


            logService.saveDeFicha(usuarioService.getUsuarioLogeado(), fichaPaciente,  AccionLogFicha.INICIAR_ATENCION, null, valorNuevoJson,descripcionLog);
            //---------------------------------------------------


            return ResponseEntity.ok(Map.of(
                    "status", "INICIO_OK",
                    "message", "La atención ha sido iniciada correctamente."
            ));


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



                //LOG FINALIZAR ATENCION---------------------------------------------------
                Map<String, Object> valorNuevoMap = Map.of(
                        "horaFin", fichaPaciente.getAtencionQuimioterapia().getHoraFin()
                );

                String valorNuevoJson = objectMapper.writeValueAsString(valorNuevoMap);


                String descripcionLog = String.format(
                        "El usuario %s registró el inicio de la atención del paciente %s a las %s.",
                        usuarioService.getUsuarioLogeado().getNombre(),
                        fichaPaciente.getCita().getPaciente().getNombreCompleto(),
                        fichaPaciente.getAtencionQuimioterapia().getHoraFin()
                );


                logService.saveDeFicha(usuarioService.getUsuarioLogeado(), fichaPaciente,  AccionLogFicha.FINALIZAR_ATENCION, null, valorNuevoJson,descripcionLog);
                //---------------------------------------------------








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


            String estadoAnterior = cita.getEstado().toString();

            // No retroceder si ya está en el estado más inicial
            if (cita.getEstado() == EstadoCita.NO_ASIGNADO) {

                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                        "status", "NO_ASIGNADO",
                        "message", "La cita ya se encuentra en el estado más inicial ('No Asignado'). No es posible retroceder más."
                ));
            }

            if (cita.getEstado() == EstadoCita.ATENDIDO) {
                Long idRol = usuarioService.getUsuarioPorId(usuarioService.getIDdeUsuarioLogeado()).getRolUsuario().getId();

//                if (idRol != 2 && idRol != 3) {
//                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
//                            "status", "NO_AUTORIZADO",
//                            "message", "No cuenta con los permisos necesarios para retroceder una atención finalizada. Por favor, contacte al supervisor o al administrador."
//                    ));
//                }

                // Borrar horas
                atencionQuimioterapia.setHoraFin(null);

                citaService.cambiarEstado(EstadoCita.EN_PROCESO, fichaPaciente);


            } else if (cita.getEstado() == EstadoCita.EN_PROCESO) {
                Long idRol = usuarioService.getUsuarioPorId(usuarioService.getIDdeUsuarioLogeado()).getRolUsuario().getId();

                // Borrar horas
                atencionQuimioterapia.setHoraInicio(null);
                // Cambiar estado explícitamente antes de guardar
                citaService.cambiarEstado(EstadoCita.PENDIENTE, fichaPaciente);

            }
            else if (cita.getEstado() == EstadoCita.PENDIENTE || cita.getEstado() == EstadoCita.EN_CONFLICTO) {
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




            //LOG FINALIZAR ATENCION---------------------------------------------------
            String estadoActual = cita.getEstado().toString();
            Map<String, Object> valorNuevoMap = Map.of(
                    "estadoCita", estadoActual
            );
            Map<String, Object> valorAnteriorMap = Map.of(
                    "estadoCita", estadoAnterior
            );

            String valorNuevoJson = objectMapper.writeValueAsString(valorNuevoMap);
            String valorAnteriorJson = objectMapper.writeValueAsString(valorAnteriorMap);


            String descripcionLog = String.format(
                    "El usuario %s retrocedió el estado de la cita del paciente %s a %s a las %s",
                    usuarioService.getUsuarioLogeado().getNombre(),
                    fichaPaciente.getCita().getPaciente().getNombreCompleto(),
                    fichaPaciente.getCita().getEstado(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
            );


            logService.saveDeFicha(usuarioService.getUsuarioLogeado(), fichaPaciente,  AccionLogFicha.RETROCEDER_CITA, valorAnteriorJson, valorNuevoJson,descripcionLog);
            //---------------------------------------------------









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


            //PARTE DE LOG--------------------------------
            Map<String, Object> valorAnteriorMap = Map.of(
                    "estadoCita", cita.getEstado().toString(),
                    "aseguradora", cita.getAseguradora(),
                    "fecha", cita.getFecha(),
                    "horaProgramada", cita.getHoraProgramada(),
                    "duracionMinutos", cita.getDuracionMinutosProtocolo(),
                    "medico", cita.getMedicoConsulta().getNombreCompleto(),
                    "medicina", fichaPaciente.getDetalleQuimioterapia().getMedicinas(),
                    "observacion",fichaPaciente.getDetalleQuimioterapia().getObservaciones(),
                    "tratamiento", fichaPaciente.getDetalleQuimioterapia().getTratamiento()
            );
            String valorAnteriorJson = objectMapper.writeValueAsString(valorAnteriorMap);

            //-------------------------------------------




            citaService.reprogramar(cita, dto.getFecha(), dto.getHora(), medico, dto.getDuracionMinutos(), dto.getAseguradora());

            DetalleQuimioterapia detalleQuimioterapia = fichaPaciente.getDetalleQuimioterapia();
            detalleQuimioterapia.setObservaciones(dto.observaciones);
            detalleQuimioterapia.setTratamiento(dto.tratamiento);
            detalleQuimioterapia.setMedicinas(dto.medicamentos);
            detalleQuimioterapiaService.save(detalleQuimioterapia);


            citaService.cambiarEstado(EstadoCita.NO_ASIGNADO, fichaPaciente);
            wsNotificacionesService.notificarActualizacionTabla();







            //LOG EDITAR ATENCION---------------------------------------------------
            Map<String, Object> valorNuevoMap = Map.of(
                    "estadoCita", cita.getEstado().toString(),
                    "aseguradora", cita.getAseguradora(),
                    "fecha", cita.getFecha(),
                    "horaProgramada", cita.getHoraProgramada(),
                    "duracionMinutos", cita.getDuracionMinutosProtocolo(),
                    "medico", cita.getMedicoConsulta().getNombreCompleto(),
                    "medicina", fichaPaciente.getDetalleQuimioterapia().getMedicinas(),
                    "observacion",fichaPaciente.getDetalleQuimioterapia().getObservaciones(),
                    "tratamiento", fichaPaciente.getDetalleQuimioterapia().getTratamiento()
            );
            String valorNuevoJson = objectMapper.writeValueAsString(valorNuevoMap);


            String descripcionLog = String.format(
                    "El usuario %s editó la cita del paciente %s. Fecha y hora de la edición: %s.",
                    usuarioService.getUsuarioLogeado().getNombre(),
                    fichaPaciente.getCita().getPaciente().getNombreCompleto(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
            );


            logService.saveDeFicha(usuarioService.getUsuarioLogeado(), fichaPaciente,  AccionLogFicha.EDITAR_CITA, valorAnteriorJson, valorNuevoJson,descripcionLog);
            //---------------------------------------------------


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

            String estadoAnterior = fichaPaciente.getCita().getEstado().toString();

            citaService.cambiarEstado(EstadoCita.CANCELADO, fichaPaciente);

            String estadoActual = fichaPaciente.getCita().getEstado().toString();
            fichaPacienteService.save(fichaPaciente);

            wsNotificacionesService.notificarActualizacionTabla();

            //LOG CANCELAR CITA----------------------------------------------
            Map<String, Object> valorNuevoMap = Map.of(
                    "estadoCita", estadoActual
            );
            Map<String, Object> valorAnteriorMap = Map.of(
                    "estadoCita", estadoAnterior
            );

            String valorNuevoJson = objectMapper.writeValueAsString(valorNuevoMap);
            String valorAnteriorJson = objectMapper.writeValueAsString(valorAnteriorMap);


            String descripcionLog = String.format(
                    "El usuario %s canceló la cita del paciente %s a las %s",
                    usuarioService.getUsuarioLogeado().getNombre(),
                    fichaPaciente.getCita().getPaciente().getNombreCompleto(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
            );


            logService.saveDeFicha(usuarioService.getUsuarioLogeado(), fichaPaciente,  AccionLogFicha.CANCELAR_CITA, valorAnteriorJson, valorNuevoJson,descripcionLog);
            //---------------------------------------------------





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



            //LOG DUPLICAR CITA----------------------------------------------
            Map<String, Object> valorAnteriorMap = Map.of(
                    "paciente", fichaNueva.getCita().getPaciente().getNombreCompleto(),
                    "aseguradora", fichaNueva.getCita().getPaciente().getNombreCompleto(),
                    "fechaCita", fichaNueva.getCita().getFecha(),
                    "medico", fichaNueva.getCita().getMedicoConsulta().getNombreCompleto(),
                    "horaCita", fichaNueva.getCita().getHoraProgramada(),
                    "duracionProtocolo", fichaNueva.getCita().getDuracionMinutosProtocolo(),
                    "medicinas", fichaNueva.getDetalleQuimioterapia().getMedicinas(),
                    "tratamiento", fichaNueva.getDetalleQuimioterapia().getTratamiento(),
                    "observacion", fichaNueva.getDetalleQuimioterapia().getObservaciones()
            );

            Map<String, Object> valorNuevoMap = Map.of(
                    "paciente", fichaNueva.getCita().getPaciente().getNombreCompleto(),
                    "aseguradora", fichaNueva.getCita().getPaciente().getNombreCompleto(),
                    "fechaCita", fichaNueva.getCita().getFecha(),
                    "medico", fichaNueva.getCita().getMedicoConsulta().getNombreCompleto(),
                    "horaCita", fichaNueva.getCita().getHoraProgramada(),
                    "duracionProtocolo", fichaNueva.getCita().getDuracionMinutosProtocolo(),
                    "medicinas", fichaNueva.getDetalleQuimioterapia().getMedicinas(),
                    "tratamiento", fichaNueva.getDetalleQuimioterapia().getTratamiento(),
                    "observacion", fichaNueva.getDetalleQuimioterapia().getObservaciones()
            );

            String valorNuevoJson = objectMapper.writeValueAsString(valorNuevoMap);
            String valorAnteriorJson = objectMapper.writeValueAsString(valorAnteriorMap);


            String descripcionDuplicacionLog = String.format(
                    "El usuario %s duplicó la ficha del paciente %s el %s.",
                    usuarioService.getUsuarioLogeado().getNombre(),
                    fichaActual.getCita().getPaciente().getNombreCompleto(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
            );


            String descripcionDuplicadoLog = String.format(
                    "Esta ficha fue generada como duplicado de una cita anterior del paciente %s. Registrado por el usuario %s el %s.",
                    fichaNueva.getCita().getPaciente().getNombreCompleto(),
                    usuarioService.getUsuarioLogeado().getNombre(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
            );


            logService.saveDeFicha(usuarioService.getUsuarioLogeado(), fichaActual,  AccionLogFicha.DUPLICAR_CITA, valorAnteriorJson, valorNuevoJson,descripcionDuplicacionLog);
            logService.saveDeFicha(usuarioService.getUsuarioLogeado(), fichaNueva,  AccionLogFicha.DUPLICAR_CITA, valorAnteriorJson, valorNuevoJson,descripcionDuplicadoLog);
            //---------------------------------------------------


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
