package com.ipor.quimioterapia.gestioncitas.fichapaciente.paciente;

import com.ipor.quimioterapia.gestioncitas.dto.CitaCreadaDTO;
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
        paciente.setNombreCompleto(citaCreadaDTO.nombreCompleto);
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

