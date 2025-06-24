package com.ipor.quimioterapia.service.fixed;

import com.ipor.quimioterapia.model.fixed.Cie;
import com.ipor.quimioterapia.model.other.DTO.CieCrearDTO;
import com.ipor.quimioterapia.repository.fixed.CieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CieService {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private CieRepository cieRepository;

    public void crearOActualizar(CieCrearDTO dto) {
        Optional<Cie> optionalCie = cieRepository.findById(dto.getId());

        Cie entidad;
        if (optionalCie.isPresent()) {
            entidad = optionalCie.get();
        } else {
            entidad = new Cie();
            entidad.setId(dto.getId());
        }

        entidad.setCodigo(dto.getCodigo());
        entidad.setDescripcion(dto.getDescripcion());

        cieRepository.save(entidad);
    }


    public Cie getPorID(Long id) {
        return cieRepository.findById(id).get();
    }


}
