package com.ipor.quimioterapia.helper.model;

import com.ipor.quimioterapia.service.other.RolUsuarioService;
import com.ipor.quimioterapia.usuario.rol.RolUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;

@Component
public class UsuarioModelBuilder {
    @Autowired
    RolUsuarioService rolUsuarioService;

    //
    // GET LISTA
    //

    public Model getListaRoles(Model model) {
        List<RolUsuario> lista = rolUsuarioService.getLista();
        model.addAttribute("Lista_Roles", lista);
        return model;
    }
}
