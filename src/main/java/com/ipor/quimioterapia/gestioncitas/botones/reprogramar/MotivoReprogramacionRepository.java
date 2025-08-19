package com.ipor.quimioterapia.gestioncitas.botones.reprogramar;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MotivoReprogramacionRepository extends JpaRepository<MotivoReprogramacion, Long> {
    List<MotivoReprogramacion> findAllByOrderByNombreAsc();
    List<MotivoReprogramacion> findByIsActiveTrueOrderByNombreAsc();


}
