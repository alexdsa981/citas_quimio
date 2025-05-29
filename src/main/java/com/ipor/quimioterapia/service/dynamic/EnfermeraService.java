package com.ipor.quimioterapia.service.dynamic;

import com.ipor.quimioterapia.model.dynamic.Enfermera;
import com.ipor.quimioterapia.model.dynamic.Medico;
import com.ipor.quimioterapia.repository.dynamic.EnfermeraRepository;
import com.ipor.quimioterapia.repository.dynamic.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnfermeraService {

    @Autowired
    EnfermeraRepository enfermeraRepository;

    public void crear(String nombre, String apellidoP, String apellidoM) {
        Enfermera entidad = new Enfermera();
        entidad.setNombre(nombre);
        entidad.setApellidoP(apellidoP);
        entidad.setApellidoM(apellidoM);
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

