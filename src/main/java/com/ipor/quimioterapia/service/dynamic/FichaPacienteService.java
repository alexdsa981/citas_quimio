package com.ipor.quimioterapia.service.dynamic;

import com.ipor.quimioterapia.model.dynamic.Cita;
import com.ipor.quimioterapia.model.dynamic.FichaPaciente;
import com.ipor.quimioterapia.repository.dynamic.FichaPacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class FichaPacienteService {

    @Autowired
    FichaPacienteRepository fichaPacienteRepository;


    public FichaPaciente crear(Cita cita) {
        FichaPaciente fichaPaciente = new FichaPaciente();
        fichaPaciente.setFechaCreacion(LocalDate.now());
        fichaPaciente.setHoraCreacion(LocalTime.now());
        fichaPaciente.setCita(cita);
        fichaPacienteRepository.save(fichaPaciente);
        return fichaPaciente;
    }
    public void guardar(FichaPaciente fichaPaciente){
        fichaPacienteRepository.save(fichaPaciente);
    }

    public List<FichaPaciente> getLista() {
        return fichaPacienteRepository.findAll();
    }

    public List<FichaPaciente> getListaHoy() {
        return fichaPacienteRepository.buscarFichasPorFecha(LocalDate.now());
    }

    public List<FichaPaciente> getListaEntreFechas(LocalDate desde, LocalDate hasta) {
        return fichaPacienteRepository.buscarFichasEntreFechas(desde, hasta);
    }

    public FichaPaciente getPorID(Long id) {
        return fichaPacienteRepository.findById(id).get();
    }


}

