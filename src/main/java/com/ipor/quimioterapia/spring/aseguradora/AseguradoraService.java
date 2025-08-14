package com.ipor.quimioterapia.spring.aseguradora;

import com.ipor.quimioterapia.spring.cie.CieSpringDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AseguradoraService {

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:9000/api/aseguradora";

    @Autowired
    public AseguradoraService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<String> obtenerAseguradorasFiltradas(String palabra) {
        String url = baseUrl + "/lista/{palabra}";
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {},
                palabra
        ).getBody();
    }

    public List<String> obtenerTodasLasAseguradoras() {
        String url = baseUrl + "/lista";
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {}
        ).getBody();
    }
}
