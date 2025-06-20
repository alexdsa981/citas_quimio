package com.ipor.quimioterapia.spring.service;

import com.ipor.quimioterapia.spring.dto.PacienteDesdeGarantiaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PacienteSpringService {
    @Autowired
    RestTemplate restTemplate;

    public PacienteDesdeGarantiaDTO obtenerPacienteDesdeGarantia(Long idGarantia) {
        String url = "http://localhost:9000/api/paciente/garantia/" + idGarantia;
        return restTemplate.getForObject(url, PacienteDesdeGarantiaDTO.class);
    }
}
