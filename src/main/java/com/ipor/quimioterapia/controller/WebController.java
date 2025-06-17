package com.ipor.quimioterapia.controller;

import com.ipor.quimioterapia.helper.model.ClasificadoresModelBuilder;
import com.ipor.quimioterapia.helper.model.FichaPacienteModelBuilder;
import com.ipor.quimioterapia.helper.model.PersonalModelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @Autowired
    ClasificadoresModelBuilder clasificadoresModelBuilder;
    @Autowired
    PersonalModelBuilder personalModelBuilder;
    @Autowired
    FichaPacienteModelBuilder fichaPacienteModelBuilder;

    @GetMapping("/")
    public String redirectToInicio() {
        return "redirect:/citas";
    }

    @GetMapping("/citas")
    public String redirigePaginaInicio(Model model) {
        clasificadoresModelBuilder.getListaEstadoCita(model);
        clasificadoresModelBuilder.getListaCieActivos(model);
        clasificadoresModelBuilder.getListaCubiculosActivos(model);



        personalModelBuilder.getListaMedicosActivos(model);
        personalModelBuilder.getListaEnfermerasActivos(model);

        fichaPacienteModelBuilder.getListaFichasPacienteDelDia(model);

        model.addAttribute("Titulo", "Quimioterapia | Principal");
        return "gestion-citas/inicio";
    }

    @GetMapping("/recursos/medicos")
    public String redirigePaginaRecursosMedicos(Model model) {
        model.addAttribute("Titulo", "Quimioterapia | Personal Médico");
        return "recursos/medicos";
    }
    @GetMapping("/recursos/enfermeras")
    public String redirigePaginaRecursosEnfermeras(Model model) {
        model.addAttribute("Titulo", "Quimioterapia | Personal Enfermería");
        return "recursos/enfermeria";
    }
    @GetMapping("/recursos/cubiculos")
    public String redirigePaginaRecursosCubiculos(Model model) {
        clasificadoresModelBuilder.getListaCubiculos(model);
        model.addAttribute("Titulo", "Quimioterapia | Cubículos");
        return "recursos/cubiculos";
    }

}
