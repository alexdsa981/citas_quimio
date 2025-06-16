select * from SS_AD_Garantia;
select * from PersonaMast WHERE NombreCompleto like '%wuilbert%';
--SE PODRIA UTILIZAR PARA BUSCAR MEDICOS O ENFERMERAS Y GUARDAR SUS DATOS EN LA APP PARA QUE SEAN SELECCIONABLES
select * from PersonaMast WHERE EsEmpleado like 'S';


SELECT name AS TableName
FROM sys.tables
WHERE name LIKE '%medico%'
ORDER BY name;

SELECT * FROM Medicos_Espec1;

--TIPO DOCUMENTO
SELECT *
FROM CM_CO_TablaMaestroDetalle d
WHERE d.Codigo IN ('D', 'O', 'P', 'R', 'X')
ORDER BY d.Nombre;

SELECT DISTINCT TipoDocumento
FROM PersonaMast
WHERE TipoDocumento IS NOT NULL
ORDER BY TipoDocumento;