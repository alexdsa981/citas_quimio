package com.ipor.quimioterapia.spring.controller;

import com.ipor.quimioterapia.spring.dto.CieDTO;
import com.ipor.quimioterapia.spring.service.CieSpringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/clasificadores/cie")
public class CieSpringController {

    @Autowired
    CieSpringService cieSpringService;

    @GetMapping("/externo/listar")
    public ResponseEntity<?> listarExternos() {
        try {
            return ResponseEntity.ok(cieSpringService.listarCieSpring());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Error consultando microservicio SPRING");
        }
    }

    @GetMapping("/externo/codigo/{codigo}")
    public ResponseEntity<?> obtenerPorCodigo(@PathVariable String codigo) {
        try {
            CieDTO cie = cieSpringService.obtenerCieSpringPorCodigo(codigo);
            if (cie != null) return ResponseEntity.ok(cie);
            else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Error consultando microservicio SPRING");
        }
    }

    @GetMapping("/externo/listaPadre/{codigoPadre}")
    public ResponseEntity<?> obtenerPorCodigoPadre(@PathVariable String codigoPadre) {
        try {
            return ResponseEntity.ok(cieSpringService.obtenerListaPorCodigoPadre(codigoPadre));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Error consultando microservicio SPRING");
        }
    }

    @GetMapping("/externo/buscar/nombre/{nombre}")
    public ResponseEntity<?> buscarPorNombre(@PathVariable String nombre) {
        try {
            return ResponseEntity.ok(cieSpringService.buscarPorNombre(nombre));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Error consultando microservicio SPRING");
        }
    }

    @GetMapping("/externo/buscar/codigo/{codigo}")
    public ResponseEntity<?> buscarPorCodigo(@PathVariable String codigo) {
        try {
            return ResponseEntity.ok(cieSpringService.buscarPorCodigo(codigo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Error consultando microservicio SPRING");
        }
    }

    @GetMapping("/externo/buscar/padre/{padre}")
    public ResponseEntity<?> buscarPorCodigoPadre(@PathVariable String padre) {
        try {
            return ResponseEntity.ok(cieSpringService.buscarPorCodigoPadre(padre));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Error consultando microservicio SPRING");
        }
    }

}
