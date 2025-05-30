package com.ipor.quimioterapia.service.fixed;

import com.ipor.quimioterapia.model.fixed.TipoEntrada;
import com.ipor.quimioterapia.model.fixed.TipoPaciente;
import com.ipor.quimioterapia.repository.fixed.TipoEntradaRepository;
import com.ipor.quimioterapia.repository.fixed.TipoPacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoEntradaService {

    @Autowired
    private TipoEntradaRepository tipoEntradaRepository;

    public void crear(String nombre) {
        TipoEntrada entidad = new TipoEntrada();
        entidad.setNombre(nombre);
        entidad.setIsActive(Boolean.TRUE);
        tipoEntradaRepository.save(entidad);
    }

    public List<TipoEntrada> getLista() {
        return tipoEntradaRepository.findAllByOrderByNombreAsc();
    }

    public List<TipoEntrada> getListaActivos() {
        return tipoEntradaRepository.findByIsActiveTrueOrderByNombreAsc();
    }

    public TipoEntrada getPorID(Long id) {
        return tipoEntradaRepository.findById(id).orElseThrow();
    }

    public void actualizar(Long id, String nombre) {
        TipoEntrada entidad = tipoEntradaRepository.findById(id).orElseThrow();
        entidad.setNombre(nombre);
        tipoEntradaRepository.save(entidad);
    }

    public void cambiarEstado(Long id, boolean isActive) {
        TipoEntrada tipoEntrada = tipoEntradaRepository.findById(id).orElseThrow();
        tipoEntrada.setIsActive(isActive);
        tipoEntradaRepository.save(tipoEntrada);
    }
}
