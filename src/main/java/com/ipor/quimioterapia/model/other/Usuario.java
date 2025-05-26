package com.ipor.quimioterapia.model.other;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Boolean changedPass;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_rol_usuario", nullable = false)
    private RolUsuario rolUsuario;
}
