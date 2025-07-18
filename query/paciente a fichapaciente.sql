ALTER TABLE ficha_paciente
ADD id_paciente BIGINT;


UPDATE fp
SET fp.id_paciente = c.id_paciente
FROM ficha_paciente fp
JOIN cita c ON fp.id_cita = c.id;


ALTER TABLE cita
DROP COLUMN id_paciente;

ALTER TABLE cita
DROP CONSTRAINT FK7fljkhue1c7r80b4li70f6fh3;


ALTER TABLE funciones_vitales
DROP COLUMN fecha, hora;


DELETE FROM funciones_vitales
WHERE id = 10103;
