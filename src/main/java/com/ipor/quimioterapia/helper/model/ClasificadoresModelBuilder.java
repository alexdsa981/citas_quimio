package com.ipor.quimioterapia.helper.model;

import com.ipor.quimioterapia.gestioncitas.fichapaciente.diagnostico.cie.CieService;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.cita.EstadoCita;
import com.ipor.quimioterapia.recursos.cubiculo.Cubiculo;
import com.ipor.quimioterapia.recursos.cubiculo.CubiculoService;
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


    public Model getListaCubiculos(Model model) {
        List<Cubiculo> lista = cubiculoService.getLista();
        model.addAttribute("Lista_Cubiculo", lista);
        return model;
    }


    public Model getListaCubiculosActivos(Model model) {
        List<Cubiculo> lista = cubiculoService.getListaActivos();
        model.addAttribute("Lista_Cubiculo_Activos", lista);
        return model;
    }



}
