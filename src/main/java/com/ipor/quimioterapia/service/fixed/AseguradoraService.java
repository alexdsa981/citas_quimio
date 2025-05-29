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
    @Autowired
    TipoPacienteService tipoPacienteService;

    public void crear(String nombre, Long idTipoPaciente) {
        Aseguradora entidad = new Aseguradora();
        entidad.setNombre(nombre);
        entidad.setTipoPaciente(tipoPacienteService.getPorID(idTipoPaciente));
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
    public List<Aseguradora> getPorTipoPaciente(Long idTipoPaciente) {
        return aseguradoraRepository.findByTipoPacienteIdAndIsActiveTrueOrderByNombreAsc(idTipoPaciente);
    }


    public void actualizar(Long id, String nombre) {
        Aseguradora entidad = aseguradoraRepository.findById(id).orElseThrow();
        entidad.setNombre(nombre);
        aseguradoraRepository.save(entidad);
    }
    public void cambiarEstado(Long id, boolean isActive) {
        Aseguradora aseguradora = aseguradoraRepository.findById(id).get();
        aseguradora.setIsActive(isActive);
        aseguradoraRepository.save(aseguradora);
    }
}
