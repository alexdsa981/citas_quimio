



SELECT 
PersonaMast.Persona,
PersonaMast.TipoDocumento,
PersonaMast.Documento,

CASE 
    WHEN PersonaMast.TipoDocumento = 'X' THEN 'Carnet Extranjería'
    WHEN PersonaMast.TipoDocumento = 'D' THEN 'D.N.I./Cédula/L.E.'
    WHEN PersonaMast.TipoDocumento = 'O' THEN 'Otros'
    WHEN PersonaMast.TipoDocumento = 'P' THEN 'Pasaporte'
    WHEN PersonaMast.TipoDocumento = 'R' THEN 'RUC / NIT'
    ELSE 'Desconocido'
END AS TipoDocumentoNombre,

PersonaMast.ApellidoPaterno,
PersonaMast.ApellidoMaterno,
PersonaMast.Nombres,
PersonaMast.NombreCompleto,
FORMAT(PersonaMast.FechaNacimiento, 'yyyy-MM-dd') AS FechaNacimiento,
DATEDIFF(YEAR, PersonaMast.FechaNacimiento, GETDATE()) - CASE 
	WHEN MONTH(PersonaMast.FechaNacimiento) > MONTH(GETDATE()) OR (MONTH(PersonaMast.FechaNacimiento) = MONTH(GETDATE()) AND DAY(PersonaMast.FechaNacimiento) > DAY(GETDATE())) 
	THEN 1 
    ELSE 0 
    END AS Edad,
PersonaMast.Sexo AS CodigoSexo,
CASE
	WHEN PersonaMast.Sexo = 'F' THEN 'Femenino'
    WHEN PersonaMast.Sexo = 'M' THEN 'Masculino'
    ELSE 'No especificado'
END AS Sexo,
PersonaMast.Telefono,
PersonaMast.Celular
FROM SS_GE_Paciente
INNER JOIN PersonaMast ON PersonaMast.Persona = SS_GE_Paciente.IdPaciente
WHERE PersonaMast.NombreCompleto LIKE '%ROJAS%'







SELECT 
PersonaMast.Persona,
PersonaMast.TipoDocumento,
PersonaMast.Documento,

CASE 
    WHEN PersonaMast.TipoDocumento = 'X' THEN 'Carnet Extranjería'
    WHEN PersonaMast.TipoDocumento = 'D' THEN 'D.N.I./Cédula/L.E.'
    WHEN PersonaMast.TipoDocumento = 'O' THEN 'Otros'
    WHEN PersonaMast.TipoDocumento = 'P' THEN 'Pasaporte'
    WHEN PersonaMast.TipoDocumento = 'R' THEN 'RUC / NIT'
    ELSE 'Desconocido'
END AS TipoDocumentoNombre,

PersonaMast.ApellidoPaterno,
PersonaMast.ApellidoMaterno,
PersonaMast.Nombres,
PersonaMast.NombreCompleto,
FORMAT(PersonaMast.FechaNacimiento, 'yyyy-MM-dd') AS FechaNacimiento,
DATEDIFF(YEAR, PersonaMast.FechaNacimiento, GETDATE()) - CASE 
	WHEN MONTH(PersonaMast.FechaNacimiento) > MONTH(GETDATE()) OR (MONTH(PersonaMast.FechaNacimiento) = MONTH(GETDATE()) AND DAY(PersonaMast.FechaNacimiento) > DAY(GETDATE())) 
	THEN 1 
    ELSE 0 
    END AS Edad,
PersonaMast.Sexo AS CodigoSexo,
CASE
	WHEN PersonaMast.Sexo = 'F' THEN 'Femenino'
    WHEN PersonaMast.Sexo = 'M' THEN 'Masculino'
    ELSE 'No especificado'
END AS Sexo,
PersonaMast.Telefono,
PersonaMast.Celular
FROM SS_GE_Paciente
INNER JOIN PersonaMast ON PersonaMast.Persona = SS_GE_Paciente.IdPaciente
WHERE PersonaMast.TipoDocumento = 'D'
AND PersonaMast.Documento = '08652658';



