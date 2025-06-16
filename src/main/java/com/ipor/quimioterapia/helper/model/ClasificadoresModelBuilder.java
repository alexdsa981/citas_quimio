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
    CieService cieService;

    @Autowired
    CubiculoService cubiculoService;

    //ESTADO CITA
    public Model getListaEstadoCita(Model model) {
        model.addAttribute("estadosCita", EstadoCita.values());
        return model;
    }

    //
    // GET LISTA
    //


    public Model getListaCie(Model model) {
        List<Cie> lista = cieService.getLista();
        model.addAttribute("Lista_Cie", lista);
        return model;
    }

    public Model getListaCubiculos(Model model) {
        List<Cubiculo> lista = cubiculoService.getLista();
        model.addAttribute("Lista_Cubiculo", lista);
        return model;
    }


    //
    // GET LISTA ACTIVOS
    //




    public Model getListaCieActivos(Model model) {
        List<Cie> lista = cieService.getListaActivos();
        model.addAttribute("Lista_Cie_Activos", lista);
        return model;
    }



    public Model getListaCubiculosActivos(Model model) {
        List<Cubiculo> lista = cubiculoService.getListaActivos();
        model.addAttribute("Lista_Cubiculo_Activos", lista);
        return model;
    }



}
