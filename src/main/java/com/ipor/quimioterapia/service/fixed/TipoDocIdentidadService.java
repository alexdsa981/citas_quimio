package com.ipor.quimioterapia.service.fixed;

import com.ipor.quimioterapia.model.fixed.TipoDocIdentidad;
import com.ipor.quimioterapia.repository.fixed.TipoDocIdentidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoDocIdentidadService {

    @Autowired
    private TipoDocIdentidadRepository tipoDocIdentidadRepository;

    public void crear(String nombre) {
        TipoDocIdentidad entidad = new TipoDocIdentidad();
        entidad.setNombre(nombre);
        entidad.setIsActive(Boolean.TRUE);
        tipoDocIdentidadRepository.save(entidad);
    }


    public List<TipoDocIdentidad> getLista() {
        return tipoDocIdentidadRepository.findAllByOrderByNombreAsc();
    }

    public List<TipoDocIdentidad> getListaActivos() {
        return tipoDocIdentidadRepository.findByIsActiveTrueOrderByNombreAsc();
    }

    public TipoDocIdentidad getPorID(Long id) {
        return tipoDocIdentidadRepository.findById(id).get();
    }

    public void actualizar(Long id, String nombre) {
        TipoDocIdentidad entidad = tipoDocIdentidadRepository.findById(id).orElseThrow();
        entidad.setNombre(nombre);
        tipoDocIdentidadRepository.save(entidad);
    }


    public void cambiarEstado(Long id, boolean isActive) {
        TipoDocIdentidad tipoDocIdentidad = tipoDocIdentidadRepository.findById(id).get();
        tipoDocIdentidad.setIsActive(isActive);
        tipoDocIdentidadRepository.save(tipoDocIdentidad);
    }
}
