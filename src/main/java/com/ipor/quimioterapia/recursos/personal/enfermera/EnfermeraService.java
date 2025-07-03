package com.ipor.quimioterapia.recursos.personal.enfermera;

import com.ipor.quimioterapia.gestioncitas.dto.EmpleadoCrearDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnfermeraService {

    @Autowired
    EnfermeraRepository enfermeraRepository;

    public void crearOActualizar(EmpleadoCrearDTO dto) {
        Optional<Enfermera> existente = enfermeraRepository.findById(dto.getId());
        Enfermera entidad;
        if (existente.isPresent()) {
            entidad = existente.get();
        } else {
            entidad = new Enfermera();
            entidad.setIdPersona(dto.getId());
        }

        entidad.setNombre(dto.getNombre());
        entidad.setApellidoP(dto.getApellidoP());
        entidad.setApellidoM(dto.getApellidoM());
        entidad.setNombreCompleto(dto.getNombreCompleto());
        entidad.setIsActive(Boolean.TRUE);
        enfermeraRepository.save(entidad);
    }

    public List<Enfermera> getLista() {
        return enfermeraRepository.findAllByOrderByNombreAsc();
    }

    public List<Enfermera> getListaActivos() {
        return enfermeraRepository.findByIsActiveTrueOrderByNombreAsc();
    }

    public Enfermera getPorID(Long id) {
        return enfermeraRepository.findById(id).get();
    }

    public void actualizar(Long id, String nombre) {
        Enfermera entidad = enfermeraRepository.findById(id).orElseThrow();
        entidad.setNombre(nombre);
        enfermeraRepository.save(entidad);
    }

    public void cambiarEstado(Long id, boolean isActive) {
        Enfermera entidad = enfermeraRepository.findById(id).get();
        entidad.setIsActive(isActive);
        enfermeraRepository.save(entidad);
    }
}

