package com.ipor.quimioterapia.service.dynamic;

import com.ipor.quimioterapia.model.dynamic.Cita;
import com.ipor.quimioterapia.model.dynamic.FichaPaciente;
import com.ipor.quimioterapia.model.dynamic.Medico;
import com.ipor.quimioterapia.model.fixed.TipoEntrada;
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
    @Autowired
    AtencionQuimioterapiaService atencionQuimioterapiaService;


    public void crear(Cita cita, TipoEntrada tipoEntrada, Medico medico) {
        FichaPaciente fichaPaciente = new FichaPaciente();
        fichaPaciente.setFechaCreacion(LocalDate.now());
        fichaPaciente.setHoraCreacion(LocalTime.now());
        fichaPaciente.setTipoEntrada(tipoEntrada);
        fichaPaciente.setCita(cita);
        fichaPaciente.setMedico(medico);
        fichaPacienteRepository.save(fichaPaciente);
        atencionQuimioterapiaService.crear(fichaPaciente);
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

    public void actualizar(Long id, String nombre) {
        FichaPaciente fichaPaciente = fichaPacienteRepository.findById(id).orElseThrow();
        fichaPacienteRepository.save(fichaPaciente);
    }

}

