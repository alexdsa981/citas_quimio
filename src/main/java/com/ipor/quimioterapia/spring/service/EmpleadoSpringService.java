package com.ipor.quimioterapia.spring.service;

import com.ipor.quimioterapia.spring.dto.EmpleadoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class EmpleadoSpringService {
    @Autowired
    RestTemplate restTemplate;

    public List<EmpleadoDTO> obtenerEmpleadoPorTexto(String texto) {
        String url = "http://localhost:9000/api/empleado/lista/" + texto;
        EmpleadoDTO[] empleados = restTemplate.getForObject(url, EmpleadoDTO[].class);
        return Arrays.asList(empleados);
    }
    public EmpleadoDTO obtenerEmpleadoPorId(Long persona) {
        String url = "http://localhost:9000/api/empleado/" + persona;
        return restTemplate.getForObject(url, EmpleadoDTO.class);
    }
}
