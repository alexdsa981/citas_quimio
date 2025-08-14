package com.ipor.quimioterapia.spring.aseguradora;

import com.ipor.quimioterapia.spring.cie.CieSpringDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app/aseguradora")
public class AseguradoraController {

    @Autowired
    private AseguradoraService aseguradoraService;

    @GetMapping("/externo/lista/{palabra}")
    public List<String> obtenerAseguradorasFiltradasExterno(@PathVariable("palabra") String palabra) {
        return aseguradoraService.obtenerAseguradorasFiltradas(palabra);
    }

    @GetMapping("/externo/lista")
    public List<String> obtenerTodasLasAseguradorasExterno() {
        return aseguradoraService.obtenerTodasLasAseguradoras();
    }
}
