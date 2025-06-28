package com.ipor.quimioterapia.spring.cie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CieSpringService {

    private final RestTemplate restTemplate;

    private final String baseUrl = "http://localhost:9000/api/cie";

    @Autowired
    public CieSpringService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<CieSpringDTO> listarCieSpring() {
        String url = baseUrl;
        return List.of(restTemplate.getForObject(url, CieSpringDTO[].class));
    }

    public CieSpringDTO obtenerCieSpringPorCodigo(String codigo) {
        String url = baseUrl + "/codigo/" + codigo;
        return restTemplate.getForObject(url, CieSpringDTO.class);
    }

    public List<CieSpringDTO> obtenerListaPorCodigoPadre(String codigoPadre) {
        String url = baseUrl + "/listaPadre/" + codigoPadre;
        return List.of(restTemplate.getForObject(url, CieSpringDTO[].class));
    }

    public List<CieSpringDTO> buscarPorNombre(String nombre) {
        String url = baseUrl + "/buscar/nombre/" + nombre;
        return List.of(restTemplate.getForObject(url, CieSpringDTO[].class));
    }

    public List<CieSpringDTO> buscarPorCodigo(String codigo) {
        String url = baseUrl + "/buscar/codigo/" + codigo;
        return List.of(restTemplate.getForObject(url, CieSpringDTO[].class));
    }

    public List<CieSpringDTO> buscarPorCodigoPadre(String padre) {
        String url = baseUrl + "/buscar/padre/" + padre;
        return List.of(restTemplate.getForObject(url, CieSpringDTO[].class));
    }
}
