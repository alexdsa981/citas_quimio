package com.ipor.quimioterapia.service.dynamic;

import com.ipor.quimioterapia.model.dynamic.Paciente;
import com.ipor.quimioterapia.model.other.DTO.CitaCreadaDTO;
import com.ipor.quimioterapia.repository.dynamic.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    RestTemplate restTemplate;


    public Paciente crearOActualizar(CitaCreadaDTO citaCreadaDTO) {
        Optional<Paciente> pacienteExistente = pacienteRepository.findByIdPaciente(citaCreadaDTO.idPaciente);
        Paciente paciente = pacienteExistente.orElseGet(Paciente::new);

        paciente.setIdPaciente(citaCreadaDTO.idPaciente);
        paciente.setNombre(citaCreadaDTO.nombres);
        paciente.setApellidoP(citaCreadaDTO.apellidoPaterno);
        paciente.setApellidoM(citaCreadaDTO.apellidoMaterno);
        paciente.setTipoDocumentoNombre(citaCreadaDTO.tipoDocumento);
        paciente.setNumDocIdentidad(citaCreadaDTO.numeroDocumento);
        paciente.setFechaNacimiento(citaCreadaDTO.fechaNacimiento);
        paciente.setEdad(citaCreadaDTO.edad);
        paciente.setSexo(citaCreadaDTO.sexo);
        paciente.setNumCelular(citaCreadaDTO.celular);
        paciente.setTelefono(citaCreadaDTO.telefono);
        pacienteRepository.save(paciente);
        return paciente;
    }


    public List<Paciente> getLista() {
        return pacienteRepository.findAll();
    }


    public Paciente getPorID(Long id) {
        return pacienteRepository.findById(id).get();
    }

}

