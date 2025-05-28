package com.ipor.quimioterapia.controller;

import com.ipor.quimioterapia.model.fixed.*;
import com.ipor.quimioterapia.service.fixed.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/app/clasificadores")
public class ClasificadoresController {
    @Autowired
    AseguradoraService aseguradoraService;
    @Autowired
    CieService cieService;
    @Autowired
    ContratanteService contratanteService;
    @Autowired
    CubiculoService cubiculoService;
    @Autowired
    TipoDocIdentidadService tipoDocIdentidadService;
    @Autowired
    TipoPacienteService tipoPacienteService;

    //
    // CREACION DE CLASIFICADOR
    //

    @PostMapping("/aseguradora/nuevo")
    public void crearAseguradora(@RequestParam("nombre") String nombre,
                                 HttpServletResponse response) throws IOException {
        aseguradoraService.crear(nombre);
        response.sendRedirect("/admin/clasificadores/aseguradora");
    }

    @PostMapping("/cie/nuevo")
    public void crearCie(@RequestParam("codigo") String codigo,
                         @RequestParam("descripcion") String descripcion,
                         HttpServletResponse response) throws IOException {
        cieService.crear(codigo, descripcion);
        response.sendRedirect("/admin/clasificadores/cie");
    }

    @PostMapping("/contratante/nuevo")
    public void crearContratante(@RequestParam("nombre") String nombre,
                                 HttpServletResponse response) throws IOException {
        contratanteService.crear(nombre);
        response.sendRedirect("/admin/clasificadores/contratante");
    }

    @PostMapping("/cubiculo/nuevo")
    public void crearCubiculo(@RequestParam("nombre") String codigo,
                              HttpServletResponse response) throws IOException {
        cubiculoService.crear(codigo);
        response.sendRedirect("/admin/clasificadores/cubiculo");
    }

    @PostMapping("/tipodocidentidad/nuevo")
    public void crearTipoDocIdentidad(@RequestParam("nombre") String nombre,
                                      HttpServletResponse response) throws IOException {
        tipoDocIdentidadService.crear(nombre);
        response.sendRedirect("/admin/clasificadores/tipo-documento-identidad");
    }

    @PostMapping("/tipopaciente/nuevo")
    public void crearTipoPaciente(@RequestParam("nombre") String nombre,
                                  HttpServletResponse response) throws IOException {
        tipoPacienteService.crear(nombre);
        response.sendRedirect("/admin/clasificadores/tipo-paciente");
    }

}
