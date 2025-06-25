package com.ipor.quimioterapia.spring.service;

import com.ipor.quimioterapia.spring.dto.PacienteSpringDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PacienteSpringService {

    @Autowired
    private RestTemplate restTemplate;

    private final String baseUrl = "http://localhost:9000/api/paciente";

    public PacienteSpringDTO obtenerPacienteDesdeGarantia(Long idGarantia) {
        String url = baseUrl + "/garantia/" + idGarantia;
        return restTemplate.getForObject(url, PacienteSpringDTO.class);
    }

    public List<PacienteSpringDTO> buscarPacientesPorNombre(String nombre) {
        String url = baseUrl + "/buscar?nombre=" + nombre;
        ResponseEntity<PacienteSpringDTO[]> response = restTemplate.getForEntity(url, PacienteSpringDTO[].class);
        return response.getBody() != null ? List.of(response.getBody()) : List.of();
    }

    public PacienteSpringDTO buscarPorDocumento(String tipoDocumento, String documento) {
        String url = baseUrl + "/buscar-por-documento?tipoDocumento=" + tipoDocumento + "&documento=" + documento;
        return restTemplate.getForObject(url, PacienteSpringDTO.class);
    }
}
