package com.ipor.quimioterapia.usuario;


import com.ipor.quimioterapia.spring.usuario.SpringUserService;
import com.ipor.quimioterapia.spring.usuario.UsuarioSpringDTO;
import com.ipor.quimioterapia.usuario.rol.RolUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/app/usuarios")
public class UsuariosController {


    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private SpringUserService springUserService;

    // Mostrar la lista de usuarios
    @GetMapping("/activos")
    @ResponseBody
    public List<UsuarioDTO> listarUsuariosActivos() {
        List<Usuario> listaUsuarios = usuarioService.getListaUsuariosActivos();
        return listaUsuarios.stream().map(usuario -> {
            UsuarioDTO dto = new UsuarioDTO();
            dto.setId(usuario.getId());
            dto.setUsername(usuario.getUsername());
            dto.setSpringUser(usuario.getIsSpringUser());
            dto.setNombre(usuario.getNombre());
            dto.setRol(usuario.getRolUsuario().getNombre());
            dto.setIsActive(usuario.getIsActive());
            return dto;
        }).toList();
    }


    public Model listarRoles(Model model){
        List<RolUsuario> listaRoles = usuarioService.getListaRoles();
        model.addAttribute("ListaRoles", listaRoles);
        return model;
    }

    @PostMapping("/spring/nuevo")
    public ResponseEntity<String> crearOActualizarUsuario(
            @RequestParam("username") String username,
            @RequestParam("nombre") String nombre,
            @RequestParam("rolUsuario") Long rolId) {

        try {
            // Obtener datos desde el sistema externo (incluye clave)
            UsuarioSpringDTO usuarioSpringDTO = springUserService.obtenerUsuarioSpring(username);
            String claveSpring = usuarioSpringDTO.getClave();

            Optional<Usuario> optionalUsuario = usuarioService.getUsuarioPorUsername(username.toUpperCase());
            Usuario usuario;

            if (optionalUsuario.isPresent()) {
                usuario = optionalUsuario.get();
                usuario.setRolUsuario(usuarioService.getRolPorId(rolId));

                usuario.asignarYEncriptarPassword(claveSpring);


                usuario.setNombre(nombre.toUpperCase());
                usuario.setIsSpringUser(Boolean.TRUE);
                usuario.setIsActive(Boolean.TRUE);
                usuario.setChangedPass(Boolean.FALSE);

                usuarioService.guardarUsuario(usuario);
                return ResponseEntity.ok("Usuario actualizado correctamente");
            } else {
                usuario = new Usuario();
                usuario.setRolUsuario(usuarioService.getRolPorId(rolId));


                usuario.asignarYEncriptarPassword(claveSpring);


                usuario.setNombre(nombre.toUpperCase());
                usuario.setUsername(username.toUpperCase());
                usuario.setIsSpringUser(Boolean.TRUE);
                usuario.setIsActive(Boolean.TRUE);
                usuario.setChangedPass(Boolean.FALSE);

                usuarioService.guardarUsuario(usuario);
                return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado correctamente");
            }


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear o actualizar el usuario.");
        }
    }





    @PostMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarUsuario(
            @PathVariable Long id,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("nombre") String nombre,
            @RequestParam("rol") Long rolId) {

        try {
            Usuario usuario = new Usuario();
            usuario.setUsername(username.toUpperCase());

            if (password != null && !password.isEmpty()) {
                usuario.setPassword(password);
            }

            usuario.setNombre(nombre.toUpperCase());
            usuario.setRolUsuario(usuarioService.getRolPorId(rolId));
            usuario.setIsActive(Boolean.TRUE);

            usuarioService.actualizarUsuario(id, usuario);

            return ResponseEntity.ok("Usuario actualizado correctamente");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Nombre de usuario duplicado");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error general al actualizar usuario");
        }
    }



    @PostMapping("/desactivar/{id}")
    public ResponseEntity<String> desactivarUsuario(@PathVariable Long id) {
        try {
            Long idUsuarioLogeado = usuarioService.getIDdeUsuarioLogeado();

            if (!Objects.equals(idUsuarioLogeado, id) && id != 1) {
                usuarioService.desactivarUsuario(id);
                return ResponseEntity.ok("Usuario desactivado correctamente");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No puedes desactivarte a ti mismo o al usuario admin");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al desactivar el usuario");
        }
    }


    @GetMapping("/rol")
    public ResponseEntity<String> obtenerRolUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Usuario usuario = usuarioService.getUsuarioPorUsername(userDetails.getUsername()).get();

            if (usuario != null && usuario.getRolUsuario().getId() == 2L) {
                return ResponseEntity.ok("soporte");
            }
        }
        return ResponseEntity.ok("otro");
    }
    @GetMapping("/id")
    public ResponseEntity<Long> obtenerIdUsuarioLogeado() {
        Long id = usuarioService.getIDdeUsuarioLogeado();
        return ResponseEntity.ok(id);
    }




}
