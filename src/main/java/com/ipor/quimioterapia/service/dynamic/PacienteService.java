package com.ipor.quimioterapia.service.dynamic;

import com.ipor.quimioterapia.model.dynamic.Paciente;
import com.ipor.quimioterapia.model.other.CitaCreadaDTO;
import com.ipor.quimioterapia.repository.dynamic.PacienteRepository;
import com.ipor.quimioterapia.service.fixed.ContratanteService;
import com.ipor.quimioterapia.service.fixed.TipoDocIdentidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    PacienteRepository pacienteRepository;
    @Autowired
    TipoDocIdentidadService tipoDocIdentidadService;
    @Autowired
    ContratanteService contratanteService;


    public Paciente crear(CitaCreadaDTO citaCreadaDTO) {
        Paciente paciente = new Paciente();
        paciente.setNombre(citaCreadaDTO.nombres);
        paciente.setApellidoP(citaCreadaDTO.apellidoPaterno);
        paciente.setApellidoM(citaCreadaDTO.apellidoMaterno);

        if (citaCreadaDTO.nroHistoria == null || citaCreadaDTO.nroHistoria.isBlank()) {
            paciente.setHistoria(generarHistoriaUnica());
        } else {
            paciente.setHistoria(citaCreadaDTO.nroHistoria.trim());
        }
        paciente.setSexo(citaCreadaDTO.sexo);
        paciente.setFechaNacimiento(citaCreadaDTO.fechaNacimiento);

        paciente.setNumCelular(citaCreadaDTO.telefono);
        paciente.setTipoDocIdentidad(tipoDocIdentidadService.getPorID(citaCreadaDTO.idTipoDocIdentidad));
        paciente.setNumDocIdentidad(citaCreadaDTO.numeroDocumento);

        paciente.setContratante(contratanteService.getPorID(citaCreadaDTO.idContratante));
        pacienteRepository.save(paciente);

        return paciente;
    }

    public List<Paciente> getLista() {
        return pacienteRepository.findAll();
    }


    public Paciente getPorID(Long id) {
        return pacienteRepository.findById(id).get();
    }

    public Optional<Paciente> getPorHistoria(String historia){
        return pacienteRepository.findByHistoria(historia);
    }
    public Optional<Paciente> getPorDocumento(Long tipoDoc, String numeroDoc) {
        return pacienteRepository.findByTipoDocIdentidadIdAndNumDocIdentidad(tipoDoc, numeroDoc);
    }

    public String generarHistoriaUnica() {
        long total = pacienteRepository.count();
        return String.format("HIS-%06d", total + 1);
    }


    public void actualizar(Long id, String nombre) {
        Paciente paciente = pacienteRepository.findById(id).orElseThrow();
        pacienteRepository.save(paciente);
    }

}

