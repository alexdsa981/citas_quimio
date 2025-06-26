package com.ipor.quimioterapia.usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ipor.quimioterapia.usuario.rol.RolUsuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private Boolean isActive;
    @Column(nullable = false, columnDefinition = "BIT DEFAULT 0")
    private Boolean isSpringUser;
    @Column(nullable = false, columnDefinition = "BIT DEFAULT 0")
    private Boolean changedPass;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_rol_usuario", nullable = false)
    private RolUsuario rolUsuario;


    //en SERVICE SE USA PARA ENCRIPTAR DIRECTAMENTE
    public void asignarYEncriptarPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public boolean verificarPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }


}
