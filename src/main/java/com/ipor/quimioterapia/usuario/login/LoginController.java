package com.ipor.quimioterapia.usuario.login;

import com.ipor.quimioterapia.core.other.CookieUtil;
import com.ipor.quimioterapia.usuario.Usuario;
import com.ipor.quimioterapia.usuario.UsuarioService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/app")
public class LoginController {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    LoginService loginService;


    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) throws IOException {
        return loginService.logearUsuarioAlSistema(username, password, response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) throws IOException {
        CookieUtil.removeJwtCookie(response);
        response.sendRedirect("/login");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/cambiar-password")
    public ResponseEntity<Void> cambiarPassword(@RequestParam String newPassword) {
        Long iDUsuarioLogeado = usuarioService.getIDdeUsuarioLogeado();
        Usuario usuario = usuarioService.getUsuarioPorId(iDUsuarioLogeado);
            usuario.asignarYEncriptarPassword(newPassword);
            usuario.setChangedPass(true);
            usuarioService.guardarUsuario(usuario);
            return ResponseEntity.ok().build();

    }


}
