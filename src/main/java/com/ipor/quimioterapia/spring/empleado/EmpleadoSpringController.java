package com.ipor.quimioterapia.spring.empleado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app/empleado")
public class EmpleadoSpringController {

    @Autowired
    private EmpleadoSpringService empleadoSpringService;

    // Buscar empleados por texto (máximo 50)
    @GetMapping("/buscar/{texto}")
    public List<EmpleadoSpringDTO> buscarEmpleadoPorTexto(@PathVariable String texto) {
        return empleadoSpringService.obtenerEmpleadoPorTexto(texto);
    }

    // Buscar empleado por ID (persona)
    @GetMapping("/{persona}")
    public EmpleadoSpringDTO obtenerEmpleadoPorId(@PathVariable Long persona) {
        return empleadoSpringService.obtenerEmpleadoPorId(persona);
    }
}
