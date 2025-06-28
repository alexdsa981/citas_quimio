package com.ipor.quimioterapia.spring.usuario;

import com.ipor.quimioterapia.spring.empleado.EmpleadoSpringDTO;
import com.ipor.quimioterapia.spring.empleado.EmpleadoSpringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/usuarios")

public class UsuarioSpringController {
    @Autowired
    private SpringUserService springUserService;

    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("/spring/buscar")
    public ResponseEntity<List<UsuarioSpringDTO>> buscarUsuariosPorNombre(@RequestParam String nombre) {
        List<UsuarioSpringDTO> lista = springUserService.buscarUsuariosPorNombre(nombre);
        return ResponseEntity.ok(lista);
    }


}
