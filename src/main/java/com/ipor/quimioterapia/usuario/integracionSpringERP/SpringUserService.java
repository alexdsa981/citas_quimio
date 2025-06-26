package com.ipor.quimioterapia.usuario.integracionSpringERP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class SpringUserService {
    @Autowired
    RestTemplate restTemplate;

    public Boolean obtenerValidacionLoginSpring(String username, String password) {
        String url = "http://localhost:9000/api/usuarios/validar?username=" + username+ "&password=" + password;
        try {
            return restTemplate.getForObject(url, Boolean.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public UsuarioSpringDTO obtenerUsuarioSpring(String nombreUsuario) {
        String url = "http://localhost:9000/api/usuarios/" + nombreUsuario;
        return restTemplate.getForObject(url, UsuarioSpringDTO.class);
    }

    public List<UsuarioSpringDTO> obtenerTodosLosUsuarios() {
        String url = "http://localhost:9000/api/usuarios";
        try {
            ResponseEntity<UsuarioSpringDTO[]> response = restTemplate.getForEntity(url, UsuarioSpringDTO[].class);
            return Arrays.asList(response.getBody());
        } catch (Exception e) {
            System.out.println("Error al obtener usuarios: " + e.getMessage());
            return Collections.emptyList();
        }
    }


}
