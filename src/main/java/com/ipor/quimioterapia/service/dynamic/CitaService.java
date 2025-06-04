package com.ipor.quimioterapia.service.dynamic;

import com.ipor.quimioterapia.model.dynamic.Cita;
import com.ipor.quimioterapia.model.dynamic.EstadoCita;
import com.ipor.quimioterapia.model.dynamic.FichaPaciente;
import com.ipor.quimioterapia.model.dynamic.Paciente;
import com.ipor.quimioterapia.model.other.DTO.CitaCreadaDTO;
import com.ipor.quimioterapia.repository.dynamic.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class CitaService {

    @Autowired
    CitaRepository citaRepository;
    @Autowired
    PacienteService pacienteService;



    public Cita crear(CitaCreadaDTO citaCreadaDTO) {
        Cita cita = new Cita();
        cita.setFecha(citaCreadaDTO.fechaCita);
        cita.setHoraProgramada(citaCreadaDTO.horaProgramada);
        cita.setHoraCreacion(LocalTime.now());
        cita.setEstado(EstadoCita.NO_ASIGNADO);

        //crear logica para verificar existencia de paciente
        Optional<Paciente> optPaciente = pacienteService.getPorDocumento(
                citaCreadaDTO.idTipoDocIdentidad,
                citaCreadaDTO.numeroDocumento
        );
        if (optPaciente.isPresent()){
            Paciente pacienteActual = optPaciente.get();
            pacienteService.actualizar(citaCreadaDTO, pacienteActual);
            cita.setPaciente(optPaciente.get());
        }else{
            Paciente paciente = pacienteService.crear(citaCreadaDTO);
            cita.setPaciente(paciente);
        }

        citaRepository.save(cita);
        return cita;
    }

    public List<Cita> getLista() {
        return citaRepository.findAll();
    }


    public Cita getPorID(Long id) {
        return citaRepository.findById(id).get();
    }

    public void cambiarEstado(EstadoCita estadoCita, FichaPaciente fichaPaciente) {
        Cita cita = fichaPaciente.getCita();
        cita.setEstado(estadoCita);
        citaRepository.save(cita);
    }
    public void reprogramar(Cita citaActual, LocalDate fechaReprogramacion, LocalTime horaReprogramacion) {
        citaActual.setFecha(fechaReprogramacion);
        citaActual.setHoraProgramada(horaReprogramacion);
        citaRepository.save(citaActual);
    }



}

