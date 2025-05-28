package com.ipor.quimioterapia.service.fixed;

import com.ipor.quimioterapia.model.fixed.Aseguradora;
import com.ipor.quimioterapia.repository.fixed.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AseguradoraService {
    @Autowired
    AseguradoraRepository aseguradoraRepository;

    public void crear(String nombre) {
        Aseguradora entidad = new Aseguradora();
        entidad.setNombre(nombre);
        entidad.setIsActive(Boolean.TRUE);
        aseguradoraRepository.save(entidad);
    }


    public List<Aseguradora> getLista(){
        return aseguradoraRepository.findAllByOrderByNombreAsc();
    }
    public List<Aseguradora> getListaActivos(){
        return aseguradoraRepository.findByIsActiveTrueOrderByNombreAsc();
    }
    public Aseguradora getPorID(Long id) {
        return aseguradoraRepository.findById(id).get();
    }
    public void actualizar(Long id, Aseguradora aseguradoraActualizada) {
        Aseguradora aseguradoraExistente = aseguradoraRepository.findById(id).get();
        aseguradoraExistente.setNombre(aseguradoraActualizada.getNombre());
        aseguradoraRepository.save(aseguradoraExistente);
    }
    public void cambiarEstado(Long id, boolean isActive) {
        Aseguradora aseguradora = aseguradoraRepository.findById(id).get();
        aseguradora.setIsActive(isActive);
        aseguradoraRepository.save(aseguradora);
    }
}
