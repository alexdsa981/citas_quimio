package com.ipor.quimioterapia.restricciones;

import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPaciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface HorariosOcupadosDTORepository extends JpaRepository<FichaPaciente, Long> {
    @Query(value = """
    SELECT
        fp.id AS idFichaPaciente,
        ci.fecha,
        ci.hora_programada,
        DATEADD(MINUTE, aq.duracion_minutos_protocolo, ci.hora_programada) AS hora_fin_protocolo,
        cu.codigo,
        cu.id AS idCubiculo,
        aq.duracion_minutos_protocolo
    FROM ficha_paciente fp
    INNER JOIN cita ci ON ci.id = fp.id_cita
    INNER JOIN atencion_quimioterapia aq ON aq.id = fp.id_atencion_quimioterapia
    INNER JOIN cubiculo cu ON cu.id = aq.id_cubiculo
    WHERE ci.estado != 'CANCELADO'
    AND ci.estado != 'ATENDIDO'
    AND fp.is_active != 0 AND ci.fecha = :fecha

    """, nativeQuery = true)
    List<HorarioOcupadoDTO> buscarHorarioPorFecha(@Param("fecha") LocalDate fecha);
}
