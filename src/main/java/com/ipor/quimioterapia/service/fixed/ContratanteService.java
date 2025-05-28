package com.ipor.quimioterapia.service.fixed;

import com.ipor.quimioterapia.model.fixed.Contratante;
import com.ipor.quimioterapia.repository.fixed.ContratanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContratanteService {

    @Autowired
    private ContratanteRepository contratanteRepository;

    public void crear(String nombre) {
        Contratante entidad = new Contratante();
        entidad.setNombre(nombre);
        entidad.setIsActive(Boolean.TRUE);
        contratanteRepository.save(entidad);
    }


    public List<Contratante> getLista() {
        return contratanteRepository.findAllByOrderByNombreAsc();
    }

    public List<Contratante> getListaActivos() {
        return contratanteRepository.findByIsActiveTrueOrderByNombreAsc();
    }

    public Contratante getPorID(Long id) {
        return contratanteRepository.findById(id).get();
    }

    public void actualizar(Long id, Contratante contratanteActualizado) {
        Contratante contratanteExistente = contratanteRepository.findById(id).get();
        contratanteExistente.setNombre(contratanteActualizado.getNombre());
        contratanteRepository.save(contratanteExistente);
    }

    public void cambiarEstado(Long id, boolean isActive) {
        Contratante contratante = contratanteRepository.findById(id).get();
        contratante.setIsActive(isActive);
        contratanteRepository.save(contratante);
    }
}
