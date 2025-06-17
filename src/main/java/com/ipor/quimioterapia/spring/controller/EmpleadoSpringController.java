package com.ipor.quimioterapia.spring.controller;

import com.ipor.quimioterapia.model.dynamic.Enfermera;
import com.ipor.quimioterapia.model.dynamic.Medico;
import com.ipor.quimioterapia.repository.dynamic.EnfermeraRepository;
import com.ipor.quimioterapia.repository.dynamic.MedicoRepository;
import com.ipor.quimioterapia.spring.dto.EmpleadoDTO;
import com.ipor.quimioterapia.spring.service.EmpleadoSpringService;
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
    public List<EmpleadoDTO> buscarEmpleadoPorTexto(@PathVariable String texto) {
        return empleadoSpringService.obtenerEmpleadoPorTexto(texto);
    }

    // Buscar empleado por ID (persona)
    @GetMapping("/{persona}")
    public EmpleadoDTO obtenerEmpleadoPorId(@PathVariable Long persona) {
        return empleadoSpringService.obtenerEmpleadoPorId(persona);
    }
}
