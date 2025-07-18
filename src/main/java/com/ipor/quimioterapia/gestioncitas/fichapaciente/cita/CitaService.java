package com.ipor.quimioterapia.gestioncitas.fichapaciente.cita;

import com.ipor.quimioterapia.gestioncitas.dto.DuplicarCitaDTO;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.*;
import com.ipor.quimioterapia.gestioncitas.dto.CitaCreadaDTO;
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


    public Cita crear(CitaCreadaDTO citaCreadaDTO, Medico medico) {
        Cita cita = new Cita();
        cita.setFecha(citaCreadaDTO.fechaCita);
        cita.setMedicoConsulta(medico);
        cita.setHoraProgramada(citaCreadaDTO.horaProgramada);
        cita.setEstado(EstadoCita.NO_ASIGNADO);
        cita.setUsuarioCreacion(usuarioService.getUsuarioPorId(usuarioService.getIDdeUsuarioLogeado()));

        cita.setDuracionMinutosProtocolo(citaCreadaDTO.duracionMinutos);

        cita.setAseguradora(citaCreadaDTO.aseguradora);
        citaRepository.save(cita);
        return cita;
    }

    public void save(Cita cita){
        citaRepository.save(cita);
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
    public void editar(Cita citaActual, LocalDate fechaReprogramacion, LocalTime horaReprogramacion, Medico medico, Integer duracionEnMinutos, String aseguradora) {
        citaActual.setFecha(fechaReprogramacion);
        citaActual.setHoraProgramada(horaReprogramacion);
        citaActual.setAseguradora(aseguradora);
        citaActual.setDuracionMinutosProtocolo(duracionEnMinutos);
        citaActual.setMedicoConsulta(medico);
        citaRepository.save(citaActual);
    }
    public Cita duplicar (DuplicarCitaDTO duplicarCitaDTO, Cita citaActual, Medico medico){
        Cita citaNueva = new Cita();
        citaNueva.setAseguradora(duplicarCitaDTO.getAseguradora());
        citaNueva.setEstado(EstadoCita.NO_ASIGNADO);
        citaNueva.setHoraProgramada(duplicarCitaDTO.getHoraProgramada());

        citaNueva.setFecha(duplicarCitaDTO.getFecha());
        citaNueva.setMedicoConsulta(medico);
        citaNueva.setDuracionMinutosProtocolo(duplicarCitaDTO.getDuracionMinutos());
        citaNueva.setUsuarioCreacion(usuarioService.getUsuarioPorId(usuarioService.getIDdeUsuarioLogeado()));
        citaRepository.save(citaNueva);
        return citaNueva;
    }



}

