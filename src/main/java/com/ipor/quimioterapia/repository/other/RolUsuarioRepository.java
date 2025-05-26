package com.ipor.quimioterapia.repository.other;

import com.ipor.quimioterapia.model.other.RolUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolUsuarioRepository extends JpaRepository<RolUsuario, Long> {
    RolUsuario findByNombre(String nombre);
}
