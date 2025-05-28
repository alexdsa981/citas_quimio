package com.ipor.quimioterapia.repository.other;

import com.ipor.quimioterapia.model.fixed.Cie;
import com.ipor.quimioterapia.model.other.RolUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolUsuarioRepository extends JpaRepository<RolUsuario, Long> {
    RolUsuario findByNombre(String nombre);
}
