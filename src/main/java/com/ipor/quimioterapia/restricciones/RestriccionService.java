package com.ipor.quimioterapia.restricciones;

import com.ipor.quimioterapia.model.dynamic.AtencionQuimioterapia;
import com.ipor.quimioterapia.model.dynamic.Cita;
import com.ipor.quimioterapia.model.dynamic.EstadoCita;
import com.ipor.quimioterapia.model.dynamic.FichaPaciente;
import com.ipor.quimioterapia.model.fixed.Cubiculo;
import com.ipor.quimioterapia.service.dynamic.CitaService;
import com.ipor.quimioterapia.service.dynamic.FichaPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class RestriccionService {
    @Autowired
    HorariosOcupadosDTORepository horariosOcupadosDTORepository;
    @Autowired
    FichaPacienteService fichaPacienteService;
    @Autowired
    CitaService citaService;

    public void camillaOcupada(FichaPaciente fichaActualizar) {
        AtencionQuimioterapia atencionQuimioterapiaActual = fichaActualizar.getAtencionQuimioterapia();
        Cita citaActual = fichaActualizar.getCita();
        LocalDate fechaProtocolo = citaActual.getFecha();

        // Si no tiene cubículo asignado
        if (atencionQuimioterapiaActual.getCubiculo() == null) {
            List<HorarioOcupadoDTO> listaHorarios = horariosOcupadosDTORepository.buscarHorarioPorFecha(fechaProtocolo);
            for (HorarioOcupadoDTO horario : listaHorarios) {
                FichaPaciente ficha = fichaPacienteService.getPorID(horario.getIdFichaPaciente());
                citaService.cambiarEstado(EstadoCita.PENDIENTE, ficha); // Asume que los demás sí tienen cubículo
            }

            citaService.cambiarEstado(EstadoCita.NO_ASIGNADO, fichaActualizar);
            return; // No continúa con la lógica de conflicto
        }

        // Lógica normal si tiene cubículo asignado
        Cubiculo cubiculoActual = atencionQuimioterapiaActual.getCubiculo();
        LocalTime horaInicio = citaActual.getHoraProgramada();
        LocalTime horaFinPrevista = horaInicio.plusMinutes(atencionQuimioterapiaActual.getDuracionMinutosProtocolo());

        boolean conflicto = false;

        List<HorarioOcupadoDTO> listaHorariosOcupadosFecha = horariosOcupadosDTORepository.buscarHorarioPorFecha(fechaProtocolo);

        for (HorarioOcupadoDTO horarioOcupado : listaHorariosOcupadosFecha) {
            if (horarioOcupado.getIdCubiculo().equals(cubiculoActual.getId())) {

                if (horarioOcupado.getIdFichaPaciente().equals(fichaActualizar.getId())) {
                    continue; // Evita compararse consigo mismo
                }

                LocalTime inicioOcupado = horarioOcupado.getHoraProgramada();
                LocalTime finOcupado = horarioOcupado.getHoraFinProtocolo();

                boolean seSuperpone = horaInicio.isBefore(finOcupado) && horaFinPrevista.isAfter(inicioOcupado);

                FichaPaciente fichaComparacion = fichaPacienteService.getPorID(horarioOcupado.getIdFichaPaciente());

                if (seSuperpone) {
                    conflicto = true;
                    citaService.cambiarEstado(EstadoCita.EN_CONFLICTO, fichaComparacion);
                    break;
                } else {
                    citaService.cambiarEstado(EstadoCita.PENDIENTE, fichaComparacion);
                }
            }
        }

        EstadoCita estadoCita = conflicto ? EstadoCita.EN_CONFLICTO : EstadoCita.PENDIENTE;
        citaService.cambiarEstado(estadoCita, fichaActualizar);
    }


}
