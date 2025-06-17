select * from SS_AD_Garantia;
select * from PersonaMast WHERE NombreCompleto like '%orihuela%' and EsEmpleado like 'S';
--SE PODRIA UTILIZAR PARA BUSCAR MEDICOS O ENFERMERAS Y GUARDAR SUS DATOS EN LA APP PARA QUE SEAN SELECCIONABLES
select Persona, ApellidoPaterno, ApellidoMaterno, Nombres, NombreCompleto, EsEmpleado from PersonaMast WHERE EsEmpleado like 'S';


SELECT name AS TableName
FROM sys.tables
WHERE name LIKE '%WH_ITEMMAST%'
ORDER BY name;

SELECT * FROM WH_ItemMast WHERE DescripcionLocal LIKE '%fulves%';

--SS_GE_PACIENTE
--SS_CE
--SELECT * FROM HSQL_Productos where Descripcion like '%citarabina%';


--TIPO DOCUMENTO
SELECT *
FROM CM_CO_TablaMaestroDetalle d
WHERE d.Codigo IN ('D', 'O', 'P', 'R', 'X')
ORDER BY d.Nombre;

SELECT DISTINCT TipoDocumento
FROM PersonaMast
WHERE TipoDocumento IS NOT NULL
ORDER BY TipoDocumento;