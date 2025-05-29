package com.ipor.quimioterapia.service.dynamic;

import com.ipor.quimioterapia.model.dynamic.Medico;
import com.ipor.quimioterapia.repository.dynamic.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {
    @Autowired
    MedicoRepository medicoRepository;

    public void crear(String nombre, String apellidoP, String apellidoM) {
        Medico entidad = new Medico();
        entidad.setNombre(nombre);
        entidad.setApellidoP(apellidoP);
        entidad.setApellidoM(apellidoM);
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
    public void actualizar(Long id, String nombre) {
        Medico entidad = medicoRepository.findById(id).orElseThrow();
        entidad.setNombre(nombre);
        medicoRepository.save(entidad);
    }
    public void cambiarEstado(Long id, boolean isActive) {
        Medico entidad = medicoRepository.findById(id).get();
        entidad.setIsActive(isActive);
        medicoRepository.save(entidad);
    }
}
