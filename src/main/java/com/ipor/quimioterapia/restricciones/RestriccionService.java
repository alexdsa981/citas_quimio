/*package com.ipor.quimioterapia.restricciones;

import com.ipor.quimioterapia.gestioncitas.fichapaciente.atencionquimioterapia.AtencionQuimioterapia;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.cita.Cita;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.cita.EstadoCita;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPaciente;
import com.ipor.quimioterapia.recursos.cubiculo.Cubiculo;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.cita.CitaService;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Service
public class RestriccionService {
    @Autowired
    HorariosOcupadosDTORepository horariosOcupadosDTORepository;
    @Autowired
    FichaPacienteService fichaPacienteService;
    @Autowired
    CitaService citaService;


    public void restriccionesAsignar(FichaPaciente fichaActual) {
        AtencionQuimioterapia atencionActual = fichaActual.getAtencionQuimioterapia();
        Cita citaActual = fichaActual.getCita();
        LocalDate fechaProtocolo = citaActual.getFecha();

        Cubiculo cubiculoActual = atencionActual.getCubiculo();
        LocalTime horaInicioActual = citaActual.getHoraProgramada();
        LocalTime horaFinPrevista = horaInicioActual.plusMinutes(citaActual.getDuracionMinutosProtocolo());

        List<HorarioOcupadoDTO> listaHorarios = horariosOcupadosDTORepository.buscarHorarioPorFecha(fechaProtocolo);

        // Determinar si la ficha actual entra en conflicto
        boolean conflictoFichaActual = false;
        for (HorarioOcupadoDTO horario : listaHorarios) {
            if (!horario.getIdFichaPaciente().equals(fichaActual.getId())
                    && seSuperpone(horario, horaInicioActual, horaFinPrevista, cubiculoActual.getId())) {
                conflictoFichaActual = true;
                break;
            }
        }

        // Cambiar estado de la ficha actual
        EstadoCita estadoActual = conflictoFichaActual ? EstadoCita.EN_CONFLICTO : EstadoCita.PENDIENTE;
        citaService.cambiarEstado(estadoActual, fichaActual);

        // Verificar una por una si cada otra ficha sigue en conflicto con alguna otra
        for (HorarioOcupadoDTO horarioEvaluado : listaHorarios) {
            if (horarioEvaluado.getIdFichaPaciente().equals(fichaActual.getId())) continue;

            boolean sigueEnConflicto = false;
            for (HorarioOcupadoDTO otro : listaHorarios) {
                if (horarioEvaluado.getIdFichaPaciente().equals(otro.getIdFichaPaciente())) continue;

                if (seSuperpone(horarioEvaluado, otro)) {
                    sigueEnConflicto = true;
                    break;
                }
            }

            FichaPaciente fichaEvaluada = fichaPacienteService.getPorID(horarioEvaluado.getIdFichaPaciente());

            if (sigueEnConflicto) {
                if (fichaEvaluada.getAtencionQuimioterapia().getHoraInicio() != null) {
                    citaService.cambiarEstado(EstadoCita.EN_PROCESO, fichaEvaluada);
                } else {
                    citaService.cambiarEstado(EstadoCita.EN_CONFLICTO, fichaEvaluada);
                }
            } else {
                citaService.cambiarEstado(EstadoCita.PENDIENTE, fichaEvaluada);
            }
        }
    }


    public void restriccionesReprogramacion(FichaPaciente fichaActual) {
        AtencionQuimioterapia atencionActual = fichaActual.getAtencionQuimioterapia();
        Cita citaActual = fichaActual.getCita();
        LocalDate fechaProtocolo = citaActual.getFecha();

        // Si no tiene cubículo asignado
        if (atencionActual.getCubiculo() == null) {
            List<HorarioOcupadoDTO> listaHorarios = horariosOcupadosDTORepository.buscarHorarioPorFecha(fechaProtocolo);

            for (HorarioOcupadoDTO horarioOcupadoA : listaHorarios) {
                // Saltar a sí mismo
                if (horarioOcupadoA.getIdFichaPaciente().equals(fichaActual.getId())) continue;

                FichaPaciente fichaEvaluada = fichaPacienteService.getPorID(horarioOcupadoA.getIdFichaPaciente());

                // Verificamos si esta ficha sigue en conflicto con otra
                boolean sigueEnConflicto = false;
                for (HorarioOcupadoDTO horarioOcupadoB : listaHorarios) {
                    if (horarioOcupadoB.getIdFichaPaciente().equals(fichaEvaluada.getId())) continue;
                    if (seSuperpone(horarioOcupadoA, horarioOcupadoB)) {
                        sigueEnConflicto = true;
                        break;
                    }
                }

                if (!sigueEnConflicto) {
                    // Si ya no tiene conflictos, cambia estado
                    if (fichaEvaluada.getAtencionQuimioterapia().getHoraInicio() != null) {
                        citaService.cambiarEstado(EstadoCita.EN_PROCESO, fichaEvaluada);
                    } else {
                        citaService.cambiarEstado(EstadoCita.PENDIENTE, fichaEvaluada);
                    }
                }
            }

            // Reprogramada: sin cubículo
            citaService.cambiarEstado(EstadoCita.NO_ASIGNADO, fichaActual);
        }
    }

    public Boolean sePuedeIniciar(FichaPaciente fichaActual) {
        AtencionQuimioterapia atencionActual = fichaActual.getAtencionQuimioterapia();
        Cita citaActual = fichaActual.getCita();
        LocalDate fechaProtocolo = citaActual.getFecha();

        Cubiculo cubiculoActual = atencionActual.getCubiculo();
        LocalTime horaInicioActual = citaActual.getHoraProgramada();
        LocalTime horaFinPrevista = horaInicioActual.plusMinutes(citaActual.getDuracionMinutosProtocolo());

        List<HorarioOcupadoDTO> listaHorarios = horariosOcupadosDTORepository.buscarHorarioPorFecha(fechaProtocolo);

        for (HorarioOcupadoDTO horarioOcupado : listaHorarios) {
            if (horarioOcupado.getIdFichaPaciente().equals(fichaActual.getId())) continue;

            if (seSuperpone(horarioOcupado, horaInicioActual, horaFinPrevista, cubiculoActual.getId())) {
                FichaPaciente fichaEvaluada = fichaPacienteService.getPorID(horarioOcupado.getIdFichaPaciente());
                Cita citaEvaluada = fichaEvaluada.getCita();

                if (citaEvaluada.getEstado() == EstadoCita.EN_PROCESO) {
                    return false;
                }
            }
        }

        return true;
    }

    public void comprobarDespuesDeAtendido(FichaPaciente fichaActual) {
        Cita citaActual = fichaActual.getCita();
        LocalDate fechaProtocolo = citaActual.getFecha();

        if (citaActual.getEstado() == EstadoCita.ATENDIDO) {

            List<HorarioOcupadoDTO> listaHorarios = horariosOcupadosDTORepository.buscarHorarioPorFecha(fechaProtocolo);

            for (HorarioOcupadoDTO horarioOcupadoA : listaHorarios) {
                FichaPaciente fichaEvaluada = fichaPacienteService.getPorID(horarioOcupadoA.getIdFichaPaciente());
                // Verificamos si esta ficha sigue en conflicto con otra
                boolean sigueEnConflicto = false;
                for (HorarioOcupadoDTO horarioOcupadoB : listaHorarios) {
                    if (horarioOcupadoB.getIdFichaPaciente().equals(fichaEvaluada.getId())) continue;
                    if (seSuperpone(horarioOcupadoA, horarioOcupadoB)) {
                        sigueEnConflicto = true;
                        break;
                    }
                }

                if (!sigueEnConflicto) {
                    // Si ya no tiene conflictos, cambia estado
                    if (fichaEvaluada.getAtencionQuimioterapia().getHoraInicio() != null) {
                        citaService.cambiarEstado(EstadoCita.EN_PROCESO, fichaEvaluada);
                    } else {
                        citaService.cambiarEstado(EstadoCita.PENDIENTE, fichaEvaluada);
                    }
                }
            }
        }
    }

    public void comprobarDespuesDeRetroceso(FichaPaciente fichaActual) {
        Cita citaActual = fichaActual.getCita();
        LocalDate fechaProtocolo = citaActual.getFecha();
        Long idFichaActual = fichaActual.getId();

        List<HorarioOcupadoDTO> listaHorarios = horariosOcupadosDTORepository.buscarHorarioPorFecha(fechaProtocolo);

        HorarioOcupadoDTO horarioActual = listaHorarios.stream()
                .filter(h -> h.getIdFichaPaciente().equals(idFichaActual))
                .findFirst()
                .orElse(null);

        if (horarioActual != null) {
            boolean tieneConflicto = listaHorarios.stream()
                    .filter(h -> !h.getIdFichaPaciente().equals(idFichaActual))
                    .anyMatch(h -> Objects.equals(h.getIdCubiculo(), horarioActual.getIdCubiculo()) &&
                            seSuperpone(horarioActual, h));

            if (tieneConflicto) {
                // Solo cambiar si no está en proceso
                if (citaActual.getEstado() != EstadoCita.EN_PROCESO) {
                    citaService.cambiarEstado(EstadoCita.EN_CONFLICTO, fichaActual);
                }
            } else {
                AtencionQuimioterapia atencion = fichaActual.getAtencionQuimioterapia();
                if (atencion != null && atencion.getHoraInicio() != null) {
                    if (citaActual.getEstado() != EstadoCita.EN_PROCESO) {
                        citaService.cambiarEstado(EstadoCita.EN_PROCESO, fichaActual);
                    }
                } else {
                    citaService.cambiarEstado(EstadoCita.PENDIENTE, fichaActual);
                }
            }
        }

        // 2. Verificar si otras fichas siguen en conflicto o ya no
        for (HorarioOcupadoDTO horarioEvaluado : listaHorarios) {
            Long idEvaluado = horarioEvaluado.getIdFichaPaciente();
            if (idEvaluado.equals(idFichaActual)) continue;

            FichaPaciente fichaEvaluada = fichaPacienteService.getPorID(idEvaluado);
            Cita citaEvaluada = fichaEvaluada.getCita();

            if (citaEvaluada.getEstado() == EstadoCita.CANCELADO || citaEvaluada.getEstado() == EstadoCita.ATENDIDO)
                continue;

            boolean tieneConflicto = listaHorarios.stream()
                    .filter(h -> !h.getIdFichaPaciente().equals(idEvaluado))
                    .anyMatch(h -> Objects.equals(h.getIdCubiculo(), horarioEvaluado.getIdCubiculo()) &&
                            seSuperpone(horarioEvaluado, h));

            if (tieneConflicto) {
                if (citaEvaluada.getEstado() != EstadoCita.EN_PROCESO) {
                    citaService.cambiarEstado(EstadoCita.EN_CONFLICTO, fichaEvaluada);
                }
            } else {
                AtencionQuimioterapia atencion = fichaEvaluada.getAtencionQuimioterapia();
                if (atencion != null && atencion.getHoraInicio() != null) {
                    if (citaEvaluada.getEstado() != EstadoCita.EN_PROCESO) {
                        citaService.cambiarEstado(EstadoCita.EN_PROCESO, fichaEvaluada);
                    }
                } else {
                    citaService.cambiarEstado(EstadoCita.PENDIENTE, fichaEvaluada);
                }
            }
        }
    }



    public void comprobarDespuesDeCancelacion(FichaPaciente fichaCancelada) {
        LocalDate fecha = fichaCancelada.getCita().getFecha();

        // Obtener la nueva lista ya sin la ficha cancelada
        List<HorarioOcupadoDTO> listaHorarios = horariosOcupadosDTORepository.buscarHorarioPorFecha(fecha);

        for (HorarioOcupadoDTO horario : listaHorarios) {
            Long idFicha = horario.getIdFichaPaciente();
            FichaPaciente ficha = fichaPacienteService.getPorID(idFicha);
            Cita cita = ficha.getCita();

            if (cita.getEstado() == EstadoCita.CANCELADO || cita.getEstado() == EstadoCita.ATENDIDO)
                continue;

            boolean tieneConflicto = listaHorarios.stream()
                    .filter(h -> !h.getIdFichaPaciente().equals(idFicha))
                    .anyMatch(h -> Objects.equals(h.getIdCubiculo(), horario.getIdCubiculo()) &&
                            seSuperpone(horario, h));

            if (tieneConflicto) {
                if (cita.getEstado() != EstadoCita.EN_PROCESO) {
                    citaService.cambiarEstado(EstadoCita.EN_CONFLICTO, ficha);
                }
            } else {
                AtencionQuimioterapia atencion = ficha.getAtencionQuimioterapia();
                if (atencion != null && atencion.getHoraInicio() != null) {
                    if (cita.getEstado() != EstadoCita.EN_PROCESO) {
                        citaService.cambiarEstado(EstadoCita.EN_PROCESO, ficha);
                    }
                } else {
                    citaService.cambiarEstado(EstadoCita.PENDIENTE, ficha);
                }
            }
        }
    }


    private boolean seSuperpone(HorarioOcupadoDTO a, HorarioOcupadoDTO b) {
        return a.getIdCubiculo().equals(b.getIdCubiculo())
                && !(a.getHoraFinProtocolo().isBefore(b.getHoraProgramada()) || a.getHoraProgramada().isAfter(b.getHoraFinProtocolo()));
    }

    private boolean seSuperpone(HorarioOcupadoDTO a, LocalTime inicioActual, LocalTime finPrevisto, Long idCubiculoActual) {
        return a.getIdCubiculo().equals(idCubiculoActual)
                && !(finPrevisto.isBefore(a.getHoraProgramada()) || inicioActual.isAfter(a.getHoraFinProtocolo()));
    }

}
*/