package com.ipor.quimioterapia.gestioncitas.fichapaciente.cita;

import com.ipor.quimioterapia.gestioncitas.fichapaciente.*;
import com.ipor.quimioterapia.dto.CitaCreadaDTO;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.paciente.Paciente;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.paciente.PacienteService;
import com.ipor.quimioterapia.recursos.personal.medico.Medico;
import com.ipor.quimioterapia.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class CitaService {

    @Autowired
    CitaRepository citaRepository;
    @Autowired
    PacienteService pacienteService;
    @Autowired
    UsuarioService usuarioService;


    public Cita crear(CitaCreadaDTO citaCreadaDTO, Medico medico, Paciente paciente) {
        Cita cita = new Cita();
        cita.setFecha(citaCreadaDTO.fechaCita);
        cita.setMedicoConsulta(medico);
        cita.setHoraProgramada(citaCreadaDTO.horaProgramada);
        cita.setEstado(EstadoCita.NO_ASIGNADO);
        cita.setUsuarioCreacion(usuarioService.getUsuarioPorId(usuarioService.getIDdeUsuarioLogeado()));
//        cita.setNumPresupuesto(citaCreadaDTO.numeroPresupuesto);
//        cita.setContratante(citaCreadaDTO.contratante);
        cita.setAseguradora(citaCreadaDTO.aseguradora);
//        cita.setTipoPaciente(citaCreadaDTO.tipoPaciente);
        cita.setPaciente(paciente);
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
    public void reprogramar(Cita citaActual, LocalDate fechaReprogramacion, LocalTime horaReprogramacion, Medico medico) {
        citaActual.setFecha(fechaReprogramacion);
        citaActual.setHoraProgramada(horaReprogramacion);
        citaActual.setMedicoConsulta(medico);
        citaRepository.save(citaActual);
    }



}

