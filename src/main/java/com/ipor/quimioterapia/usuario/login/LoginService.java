package com.ipor.quimioterapia.usuario.login;

import com.ipor.quimioterapia.core.security.ConstantesSeguridad;
import com.ipor.quimioterapia.core.security.JwtTokenProvider;
import com.ipor.quimioterapia.usuario.Usuario;
import com.ipor.quimioterapia.usuario.UsuarioService;
import com.ipor.quimioterapia.usuario.integracionSpringERP.SpringUserService;
import com.ipor.quimioterapia.usuario.integracionSpringERP.UsuarioSpringDTO;
import com.ipor.quimioterapia.usuario.rol.RolUsuarioRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class LoginService {
    @Autowired
    SpringUserService springUserService;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    RolUsuarioRepository rolUsuarioRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;


    public ResponseEntity<Void> logearUsuarioAlSistema(String username, String password, HttpServletResponse response) throws IOException {
        try {
            System.out.println("Iniciando login para usuario: " + username);

            // Verifica si el usuario está registrado en la BD local
            Optional<Usuario> optionalUsuario = usuarioService.getUsuarioPorUsername(username.toUpperCase());

            if (optionalUsuario.isEmpty()) {
                System.out.println("Usuario no encontrado localmente: " + username);
                response.sendRedirect("/login?error=unregistered&username=" + URLEncoder.encode(username, StandardCharsets.UTF_8));
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            Usuario usuarioLocal = optionalUsuario.get();
            System.out.println("Usuario encontrado localmente: " + usuarioLocal.getUsername());

            // Si no está activo, bloquear acceso
            if (!usuarioLocal.getIsActive()) {
                System.out.println("Usuario inactivo: " + username);
                response.sendRedirect("/login?error=inactive&username=" + URLEncoder.encode(username, StandardCharsets.UTF_8));
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Validar las credenciales contra el sistema externo (Spring)
            System.out.println("Validando credenciales contra sistema Spring externo...");
            Boolean credencialesValidas = springUserService.obtenerValidacionLoginSpring(username, password);

            if (credencialesValidas == null || !credencialesValidas) {
                System.out.println("Credenciales inválidas para usuario: " + username);
                response.sendRedirect("/login?error=badCredentials&username=" + URLEncoder.encode(username, StandardCharsets.UTF_8));
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Obtener datos actualizados desde Spring
            UsuarioSpringDTO usuarioSpringDTO = springUserService.obtenerUsuarioSpring(username);
            usuarioLocal.setChangedPass(true);
            usuarioLocal.setIsSpringUser(true);
            usuarioLocal.setNombre(usuarioSpringDTO.getNombre().toUpperCase());
            usuarioLocal.asignarYEncriptarPassword(password);
            usuarioService.guardarUsuario(usuarioLocal);
            System.out.println("Datos del usuario actualizados desde Spring");

            // Autenticación local
            System.out.println("Autenticando contra sistema local...");
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generar token JWT
            System.out.println("Generando JWT...");
            String token = jwtTokenProvider.generarToken(authentication);
            Cookie jwtCookie = new Cookie("JWT", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setMaxAge((int) (ConstantesSeguridad.JWT_EXPIRATION_TOKEN / 1000));
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);

            // Redirección final
            if (!usuarioLocal.getChangedPass()) {
                System.out.println("Redirigiendo a /citas?changePassword");
                response.sendRedirect("/citas?changePassword");
                return ResponseEntity.ok().build();
            }

            System.out.println("Redirigiendo a /citas (login exitoso)");
            response.sendRedirect("/citas");
            return ResponseEntity.ok().build();

        } catch (BadCredentialsException e) {
            System.out.println("Excepción: BadCredentialsException");
            response.sendRedirect("/login?error=badCredentials&username=" + URLEncoder.encode(username, StandardCharsets.UTF_8));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        } catch (Exception e) {
            System.out.println("Excepción general:");
            e.printStackTrace();
            response.sendRedirect("/login?error=unknown");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }






}
