package com.ipor.quimioterapia.controller.dynamic;

import com.ipor.quimioterapia.model.other.DTO.CieCrearDTO;
import com.ipor.quimioterapia.service.fixed.CieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/diagnostico")
public class DiagnosticoController {

    @Autowired
    CieService cieService;

    @PostMapping("/cie/guardar")
    public ResponseEntity<?> guardarCie(@RequestBody CieCrearDTO dto) {
        cieService.crearOActualizar(dto);
        return ResponseEntity.ok("CIE registrado");
    }

}
