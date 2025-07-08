SELECT * FROM ficha_paciente;
SELECT * FROM funciones_vitales;

/*eliminar "estado" y "id_ficha_paciente" de funciones vitales*/

UPDATE fp
SET fp.id_funciones_vitales = fv.id
FROM ficha_paciente fp
JOIN funciones_vitales fv ON fv.id_ficha_paciente = fp.id


SELECT fp.id, fp.id_funciones_vitales, fv.id AS funciones_id, fv.id_ficha_paciente
FROM ficha_paciente fp
LEFT JOIN funciones_vitales fv ON fv.id = fp.id_funciones_vitales









DECLARE @fkName NVARCHAR(128);
DECLARE @sql NVARCHAR(MAX);

-- Buscar el nombre de la foreign key asociada a la columna id_ficha_paciente
SELECT @fkName = fk.name
FROM sys.foreign_keys fk
JOIN sys.foreign_key_columns fkc ON fk.object_id = fkc.constraint_object_id
JOIN sys.columns c ON fkc.parent_object_id = c.object_id AND fkc.parent_column_id = c.column_id
WHERE fk.parent_object_id = OBJECT_ID('funciones_vitales')
  AND c.name = 'id_ficha_paciente';

-- Si existe, eliminar la constraint
IF @fkName IS NOT NULL
BEGIN
    SET @sql = 'ALTER TABLE funciones_vitales DROP CONSTRAINT [' + @fkName + ']';
    EXEC sp_executesql @sql;
END

-- Luego eliminar la columna
IF EXISTS (
    SELECT 1 FROM sys.columns 
    WHERE object_id = OBJECT_ID('funciones_vitales') 
    AND name = 'id_ficha_paciente'
)
BEGIN
    ALTER TABLE funciones_vitales DROP COLUMN id_ficha_paciente;
END














ALTER TABLE funciones_vitales
DROP COLUMN estado;
