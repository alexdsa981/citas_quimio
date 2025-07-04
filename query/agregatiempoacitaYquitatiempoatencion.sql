
ALTER TABLE cita
ADD duracion_minutos_protocolo INT;
GO

UPDATE c
SET c.duracion_minutos_protocolo = aq.duracion_minutos_protocolo
FROM cita c
JOIN ficha_paciente f ON f.id_cita = c.id
JOIN atencion_quimioterapia aq ON f.id_atencion_quimioterapia = aq.id;
GO

SELECT c.id, c.duracion_minutos_protocolo
FROM cita c
WHERE duracion_minutos_protocolo IS NOT NULL;
GO


ALTER TABLE atencion_quimioterapia
DROP COLUMN duracion_minutos_protocolo;
GO




ALTER TABLE detalle_quimioterapia
DROP COLUMN hora_modificacion, fecha_modificacion;





INSERT INTO detalle_quimioterapia (tratamiento, observaciones, medicinas)
SELECT NULL, NULL, NULL
FROM ficha_paciente
WHERE id_detalle_quimioterapia IS NULL;


;WITH NuevosDetalles AS (
    SELECT id, 
           ROW_NUMBER() OVER (ORDER BY id) AS rn
    FROM detalle_quimioterapia
    WHERE tratamiento IS NULL AND observaciones IS NULL AND medicinas IS NULL
),
FichasSinDetalle AS (
    SELECT id, 
           ROW_NUMBER() OVER (ORDER BY id) AS rn
    FROM ficha_paciente
    WHERE id_detalle_quimioterapia IS NULL
)
UPDATE f
SET f.id_detalle_quimioterapia = d.id
FROM ficha_paciente f
JOIN FichasSinDetalle fsd ON f.id = fsd.id
JOIN NuevosDetalles d ON fsd.rn = d.rn;


SELECT COUNT(*) AS faltantes
FROM ficha_paciente
WHERE id_detalle_quimioterapia IS NULL;



ALTER TABLE detalle_quimioterapia
DROP COLUMN anamnesis,
             evolucion,
             examen_fisico;







