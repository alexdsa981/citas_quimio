
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
