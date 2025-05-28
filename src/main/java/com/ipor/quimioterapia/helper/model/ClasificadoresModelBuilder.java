package com.ipor.quimioterapia.helper.model;

import com.ipor.quimioterapia.model.dynamic.EstadoCita;
import com.ipor.quimioterapia.model.fixed.*;
import com.ipor.quimioterapia.service.fixed.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;

@Component
public class ClasificadoresModelBuilder {
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

    //ESTADO CITA
    public Model getListaEstadoCita(Model model) {
        model.addAttribute("estadosCita", EstadoCita.values());
        return model;
    }

    //
    // GET LISTA
    //

    public Model getListaAseguradoras(Model model) {
        List<Aseguradora> lista = aseguradoraService.getLista();
        model.addAttribute("Lista_Aseguradoras", lista);
        return model;
    }
    public Model getListaCie(Model model) {
        List<Cie> lista = cieService.getLista();
        model.addAttribute("Lista_Cie", lista);
        return model;
    }
    public Model getListaContratantes(Model model) {
        List<Contratante> lista = contratanteService.getLista();
        model.addAttribute("Lista_Contratantes", lista);
        return model;
    }
    public Model getListaCubiculos(Model model) {
        List<Cubiculo> lista = cubiculoService.getLista();
        model.addAttribute("Lista_Cubiculo", lista);
        return model;
    }
    public Model getListaTipoDocIdentidad(Model model) {
        List<TipoDocIdentidad> lista = tipoDocIdentidadService.getLista();
        model.addAttribute("Lista_TipoDocIdentidad", lista);
        return model;
    }
    public Model getListaTipoPaciente(Model model) {
        List<TipoPaciente> lista = tipoPacienteService.getLista();
        model.addAttribute("Lista_TipoPaciente", lista);
        return model;
    }

    //
    // GET LISTA ACTIVOS
    //


    public Model getListaAseguradorasActivos(Model model) {
        List<Aseguradora> lista = aseguradoraService.getListaActivos();
        model.addAttribute("Lista_Aseguradoras_Activos", lista);
        return model;
    }

    public Model getListaCieActivos(Model model) {
        List<Cie> lista = cieService.getListaActivos();
        model.addAttribute("Lista_Cie_Activos", lista);
        return model;
    }

    public Model getListaContratantesActivos(Model model) {
        List<Contratante> lista = contratanteService.getListaActivos();
        model.addAttribute("Lista_Contratantes_Activos", lista);
        return model;
    }

    public Model getListaCubiculosActivos(Model model) {
        List<Cubiculo> lista = cubiculoService.getListaActivos();
        model.addAttribute("Lista_Cubiculo_Activos", lista);
        return model;
    }

    public Model getListaTipoDocIdentidadActivos(Model model) {
        List<TipoDocIdentidad> lista = tipoDocIdentidadService.getListaActivos();
        model.addAttribute("Lista_TipoDocIdentidad_Activos", lista);
        return model;
    }

    public Model getListaTipoPacienteActivos(Model model) {
        List<TipoPaciente> lista = tipoPacienteService.getListaActivos();
        model.addAttribute("Lista_TipoPaciente_Activos", lista);
        return model;
    }
}
