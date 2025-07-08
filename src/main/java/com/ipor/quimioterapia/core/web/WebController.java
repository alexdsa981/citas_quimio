package com.ipor.quimioterapia.core.web;

import com.ipor.quimioterapia.helper.model.ClasificadoresModelBuilder;
import com.ipor.quimioterapia.helper.model.FichaPacienteModelBuilder;
import com.ipor.quimioterapia.helper.model.PersonalModelBuilder;
import com.ipor.quimioterapia.helper.model.UsuarioModelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {
    @Autowired
    ClasificadoresModelBuilder clasificadoresModelBuilder;
    @Autowired
    PersonalModelBuilder personalModelBuilder;
    @Autowired
    FichaPacienteModelBuilder fichaPacienteModelBuilder;
    @Autowired
    UsuarioModelBuilder usuarioModelBuilder;

    //redirige / a /login
    @GetMapping("/")
    public String redirectToInicio() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String redirigePaginaLogin(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "username", required = false) String username,
            Model model) {
        // Obtén la autenticación actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                !authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/citas";
        }
        model.addAttribute("error", error);
        model.addAttribute("username", username);
        return "index";
    }


    @GetMapping("/citas")
    public String redirigePaginaInicio(Model model) {
        clasificadoresModelBuilder.getListaEstadoCita(model);
        clasificadoresModelBuilder.getListaCubiculosActivos(model);

        personalModelBuilder.getListaMedicosActivos(model);
        personalModelBuilder.getListaEnfermerasActivos(model);

        fichaPacienteModelBuilder.getListaFichasPacienteDelDia(model);

        model.addAttribute("Titulo", "ClínicaDeDía | Principal");
        return "gestion-citas/inicio";
    }

    @GetMapping("/recursos/medicos")
    public String redirigePaginaRecursosMedicos(Model model) {
        model.addAttribute("Titulo", "ClínicaDeDía | Personal Médico");
        return "recursos/medicos";
    }
    @GetMapping("/recursos/enfermeras")
    public String redirigePaginaRecursosEnfermeras(Model model) {
        model.addAttribute("Titulo", "ClínicaDeDía | Personal Enfermería");
        return "recursos/enfermeria";
    }
    @GetMapping("/recursos/cubiculos")
    public String redirigePaginaRecursosCubiculos(Model model) {
        model.addAttribute("Titulo", "ClínicaDeDía | Cubículos");
        return "recursos/cubiculos";
    }
    @GetMapping("/configuracion/usuarios")
    public String redirigePaginaConfiguracion(Model model) {
        usuarioModelBuilder.getListaRoles(model);
        model.addAttribute("Titulo", "ClínicaDeDía | Usuarios");
        return "configuracion/usuarios";
    }

}
