package com.ipor.quimioterapia.recursos.cubiculo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CubiculoService {

    @Autowired
    private CubiculoRepository cubiculoRepository;

    public void crear(String codigo) {
        Cubiculo entidad = new Cubiculo();
        entidad.setCodigo(codigo);
        entidad.setIsActive(Boolean.TRUE);
        cubiculoRepository.save(entidad);
    }


    public List<Cubiculo> getLista() {
        return cubiculoRepository.findAllByOrderByCodigoAsc();
    }

    public List<Cubiculo> getListaActivos() {
        return cubiculoRepository.findByIsActiveTrueOrderByCodigoAsc();
    }

    public Cubiculo getPorID(Long id) {
        return cubiculoRepository.findById(id).get();
    }

    public void actualizar(Long id, String codigo) {
        Cubiculo entidad = cubiculoRepository.findById(id).orElseThrow();
        entidad.setCodigo(codigo);
        cubiculoRepository.save(entidad);
    }


    public void cambiarEstado(Long id, boolean isActive) {
        Cubiculo cubiculo = cubiculoRepository.findById(id).get();
        cubiculo.setIsActive(isActive);
        cubiculoRepository.save(cubiculo);
    }
}
