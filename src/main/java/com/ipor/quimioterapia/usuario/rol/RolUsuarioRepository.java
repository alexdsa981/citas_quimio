package com.ipor.quimioterapia.usuario.rol;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RolUsuarioRepository extends JpaRepository<RolUsuario, Long> {
    RolUsuario findByNombre(String nombre);
}
