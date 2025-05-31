package com.ipor.quimioterapia.service.fixed;

import com.ipor.quimioterapia.model.fixed.Cie;
import com.ipor.quimioterapia.model.other.CieSpringDTO;
import com.ipor.quimioterapia.repository.fixed.CieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class CieService {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private CieRepository cieRepository;

    public void crear(String codigo, String descripcion) {
        Cie entidad = new Cie();
        entidad.setCodigo(codigo);
        entidad.setDescripcion(descripcion);
        entidad.setFechaActualizacion(LocalDate.now());
        entidad.setHoraActualizacion(LocalTime.now());
        entidad.setIsActive(Boolean.TRUE);
        cieRepository.save(entidad);
    }


    public List<Cie> getLista() {
        return cieRepository.findAllByOrderByCodigoAsc();
    }

    public List<Cie> getListaActivos() {
        return cieRepository.findByIsActiveTrueOrderByCodigoAsc();
    }

    public Cie getPorID(Long id) {
        return cieRepository.findById(id).get();
    }

    public void actualizar(Long id, String codigo, String descripcion) {
        Cie entidad = cieRepository.findById(id).orElseThrow();
        entidad.setCodigo(codigo);
        entidad.setDescripcion(descripcion);
        entidad.setFechaActualizacion(LocalDate.now());
        entidad.setHoraActualizacion(LocalTime.now());
        cieRepository.save(entidad);
    }

    public void cambiarEstado(Long id, boolean isActive) {
        Cie cie = cieRepository.findById(id).get();
        cie.setIsActive(isActive);
        cieRepository.save(cie);
    }

}
