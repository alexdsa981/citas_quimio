package com.ipor.quimioterapia.gestioncitas.fichapaciente;

import com.ipor.quimioterapia.gestioncitas.fichapaciente.cita.Cita;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.detallequimioterapia.DetalleQuimioterapia;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.detallequimioterapia.DetalleQuimioterapiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class FichaPacienteService {

    @Autowired
    FichaPacienteRepository fichaPacienteRepository;


    public FichaPaciente crear(Cita cita, DetalleQuimioterapia detalleQuimioterapia) {
        FichaPaciente fichaPaciente = new FichaPaciente();
        fichaPaciente.setFechaCreacion(LocalDate.now());
        fichaPaciente.setHoraCreacion(LocalTime.now());
        fichaPaciente.setCita(cita);
        fichaPaciente.setDetalleQuimioterapia(detalleQuimioterapia);
        fichaPaciente.setIsActive(Boolean.TRUE);
        fichaPacienteRepository.save(fichaPaciente);
        return fichaPaciente;
    }
    public void save(FichaPaciente fichaPaciente){
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

    public List<FichaPaciente> findByPacienteOrderByFechaDesc(Long pacienteId, Long excluirFichaId) {
        return fichaPacienteRepository
                .findByCitaPacienteIdAndIdNotOrderByCitaFechaDesc(pacienteId, excluirFichaId);
    }


}

