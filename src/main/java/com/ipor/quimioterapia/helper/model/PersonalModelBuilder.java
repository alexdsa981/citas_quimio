package com.ipor.quimioterapia.helper.model;

import com.ipor.quimioterapia.recursos.personal.enfermera.Enfermera;
import com.ipor.quimioterapia.recursos.personal.medico.Medico;
import com.ipor.quimioterapia.recursos.personal.enfermera.EnfermeraService;
import com.ipor.quimioterapia.recursos.personal.medico.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;

@Component

public class PersonalModelBuilder {
    @Autowired
    MedicoService medicoService;
    @Autowired
    EnfermeraService enfermeraService;

    //
    // GET LISTA
    //

    public Model getListaEnfermeras(Model model) {
        List<Enfermera> lista = enfermeraService.getLista();
        model.addAttribute("Lista_Enfermeras", lista);
        return model;
    }

    public Model getListaMedicos(Model model) {
        List<Medico> lista = medicoService.getLista();
        model.addAttribute("Lista_Medicos", lista);
        return model;
    }

    //
    // GET LISTA ACTIVOS
    //

    public Model getListaEnfermerasActivos(Model model) {
        List<Enfermera> lista = enfermeraService.getListaActivos();
        model.addAttribute("Lista_Enfermeras_Activos", lista);
        return model;
    }
    public Model getListaMedicosActivos(Model model) {
        List<Medico> lista = medicoService.getListaActivos();
        model.addAttribute("Lista_Medicos_Activos", lista);
        return model;
    }
}