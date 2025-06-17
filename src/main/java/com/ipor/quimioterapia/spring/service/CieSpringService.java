package com.ipor.quimioterapia.spring.service;

import com.ipor.quimioterapia.spring.dto.CieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CieSpringService {

    private final RestTemplate restTemplate;

    private final String baseUrl = "http://localhost:9001/api/cie";

    @Autowired
    public CieSpringService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<CieDTO> listarCieSpring() {
        String url = baseUrl;
        return List.of(restTemplate.getForObject(url, CieDTO[].class));
    }

    public CieDTO obtenerCieSpringPorCodigo(String codigo) {
        String url = baseUrl + "/codigo/" + codigo;
        return restTemplate.getForObject(url, CieDTO.class);
    }

    public List<CieDTO> obtenerListaPorCodigoPadre(String codigoPadre) {
        String url = baseUrl + "/listaPadre/" + codigoPadre;
        return List.of(restTemplate.getForObject(url, CieDTO[].class));
    }

    public List<CieDTO> buscarPorNombre(String nombre) {
        String url = baseUrl + "/buscar/nombre/" + nombre;
        return List.of(restTemplate.getForObject(url, CieDTO[].class));
    }

    public List<CieDTO> buscarPorCodigo(String codigo) {
        String url = baseUrl + "/buscar/codigo/" + codigo;
        return List.of(restTemplate.getForObject(url, CieDTO[].class));
    }

    public List<CieDTO> buscarPorCodigoPadre(String padre) {
        String url = baseUrl + "/buscar/padre/" + padre;
        return List.of(restTemplate.getForObject(url, CieDTO[].class));
    }
}
