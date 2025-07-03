package com.ipor.quimioterapia.recursos.personal.medico;

import com.ipor.quimioterapia.gestioncitas.dto.EmpleadoCrearDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicoService {
    @Autowired
    MedicoRepository medicoRepository;

    public void crearOActualizar(EmpleadoCrearDTO dto) {
        Optional<Medico> existente = medicoRepository.findById(dto.getId());
        Medico entidad;
        if (existente.isPresent()) {
            entidad = existente.get();
        } else {
            entidad = new Medico();
            entidad.setIdPersona(dto.getId());
        }

        entidad.setNombre(dto.getNombre());
        entidad.setApellidoP(dto.getApellidoP());
        entidad.setApellidoM(dto.getApellidoM());
        entidad.setNombreCompleto(dto.getNombreCompleto());
        entidad.setIsActive(Boolean.TRUE);
        medicoRepository.save(entidad);
    }

    public List<Medico> getLista(){
        return medicoRepository.findAllByOrderByNombreAsc();
    }
    public List<Medico> getListaActivos(){
        return medicoRepository.findByIsActiveTrueOrderByNombreAsc();
    }
    public Medico getPorID(Long id) {
        return medicoRepository.findById(id).get();
    }

    public void cambiarEstado(Long id, boolean isActive) {
        Medico entidad = medicoRepository.findById(id).get();
        entidad.setIsActive(isActive);
        medicoRepository.save(entidad);
    }
}
