
SELECT 
    SS_AD_Garantia.IdGarantia AS NumeroPresupuesto,
    SS_AD_Garantia.IdPaciente,
    Paciente.ApellidoPaterno,
    Paciente.ApellidoMaterno,
    Paciente.Nombres,
    Paciente.TipoDocumento,            -- ← Tipo de documento del paciente
    Paciente.Documento,                -- ← Número de documento del paciente
    Paciente.FechaNacimiento,
    DATEDIFF(YEAR, Paciente.FechaNacimiento, GETDATE()) - CASE 
        WHEN MONTH(Paciente.FechaNacimiento) > MONTH(GETDATE()) 
             OR (MONTH(Paciente.FechaNacimiento) = MONTH(GETDATE()) 
                 AND DAY(Paciente.FechaNacimiento) > DAY(GETDATE())) 
        THEN 1 
        ELSE 0 
    END AS Edad,
    Paciente.Sexo AS CodigoSexo,
    CASE
        WHEN Paciente.Sexo = 'F' THEN 'Femenino'
        WHEN Paciente.Sexo = 'M' THEN 'Masculino'
        ELSE 'No especificado'
    END AS Sexo,
    Paciente.Telefono,
    Paciente.Celular,
    SS_AD_Garantia.TipoPaciente AS IdTipoPaciente,
    CM_CO_TablaMaestroDetalle.Nombre AS TipoPaciente,
    SS_AD_Garantia.IdEmpresaAseguradora,
    ISNULL(Aseguradora.NombreCompleto, 'Contado') AS EmpresaAseguradora,
    SS_AD_Garantia.IdEmpresaEmpleadora,
    ISNULL(Empleadora.NombreCompleto, 'Contado') AS EmpresaEmpleadora
FROM SS_AD_Garantia
LEFT JOIN PersonaMast AS Paciente 
    ON Paciente.Persona = SS_AD_Garantia.IdPaciente
LEFT JOIN CM_CO_TablaMaestroDetalle 
    ON CM_CO_TablaMaestroDetalle.IdCodigo = SS_AD_Garantia.TipoPaciente
    AND CM_CO_TablaMaestroDetalle.IdTablaMaestro = 106
LEFT JOIN PersonaMast AS Aseguradora 
    ON Aseguradora.Persona = SS_AD_Garantia.IdEmpresaAseguradora
LEFT JOIN PersonaMast AS Empleadora 
    ON Empleadora.Persona = SS_AD_Garantia.IdEmpresaEmpleadora
ORDER BY NumeroPresupuesto;
