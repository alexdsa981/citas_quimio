package com.ipor.quimioterapia.controller.fixed;

import com.ipor.quimioterapia.model.other.DTO.CieSpringDTO;
import com.ipor.quimioterapia.service.fixed.CieService;
import com.ipor.quimioterapia.service.fixed.CieSpringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/clasificadores/cie")
public class CieController {

    @Autowired
    CieService cieService;
    @Autowired
    CieSpringService cieSpringService;

    @GetMapping("/local/activar/{id}")
    public ResponseEntity<?> activar(@PathVariable Long id) {
        cieService.cambiarEstado(id, true);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/local/desactivar/{id}")
    public ResponseEntity<?> desactivar(@PathVariable Long id) {
        cieService.cambiarEstado(id, false);
        return ResponseEntity.ok().build();
    }




    //
    //EXTERNO
    //



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
            CieSpringDTO cie = cieSpringService.obtenerCieSpringPorCodigo(codigo);
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
