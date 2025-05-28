package com.ipor.quimioterapia.service.fixed;

import com.ipor.quimioterapia.model.fixed.TipoPaciente;
import com.ipor.quimioterapia.repository.fixed.TipoPacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoPacienteService {
    @Autowired
    TipoPacienteRepository tipoPacienteRepository;

    public void crear(String nombre) {
        TipoPaciente entidad = new TipoPaciente();
        entidad.setNombre(nombre);
        entidad.setIsActive(Boolean.TRUE);
        tipoPacienteRepository.save(entidad);
    }


    public List<TipoPaciente> getLista(){
        return tipoPacienteRepository.findAllByOrderByNombreAsc();
    }

    public List<TipoPaciente> getListaActivos(){
        return tipoPacienteRepository.findByIsActiveTrueOrderByNombreAsc();
    }

    public TipoPaciente getPorID(Long id) {
        return tipoPacienteRepository.findById(id).get();
    }

    public void actualizar(Long id, TipoPaciente tipoPacienteActualizada) {
        TipoPaciente tipoPacienteExistente = tipoPacienteRepository.findById(id).get();
        tipoPacienteExistente.setNombre(tipoPacienteActualizada.getNombre());
        tipoPacienteRepository.save(tipoPacienteExistente);
    }
    public void cambiarEstado(Long id, boolean isActive) {
        TipoPaciente tipoPaciente = tipoPacienteRepository.findById(id).get();
        tipoPaciente.setIsActive(isActive);
        tipoPacienteRepository.save(tipoPaciente);
    }
}
