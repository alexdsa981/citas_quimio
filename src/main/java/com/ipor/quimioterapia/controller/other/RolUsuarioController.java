package com.ipor.quimioterapia.controller.other;

import com.ipor.quimioterapia.service.other.RolUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/clasificadores/rolusuario")
public class RolUsuarioController {

    @Autowired
    RolUsuarioService rolUsuarioService;


}
