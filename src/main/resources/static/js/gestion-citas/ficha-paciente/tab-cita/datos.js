function llenarVisualFichaPaciente(objetoFicha) {
    const paciente = objetoFicha.cita?.paciente || {};
    const cita = objetoFicha.cita || {};
    const contratante = cita.contratante || {};
    const aseguradora = cita.aseguradora || {};
    const medico = cita.medicoConsulta || {};

    // Paciente
    document.getElementById('nombrePacienteFicha').textContent = `${paciente.nombre || ''} ${paciente.apellidoP || ''} ${paciente.apellidoM || ''}`;
    document.getElementById('tipoDocumentoPacienteFicha').textContent = paciente.tipoDocumentoNombre || '';
    document.getElementById('numeroDocumentoPacienteFicha').textContent = paciente.numDocIdentidad || '';
    document.getElementById('fechaNacimientoPacienteFicha').textContent = paciente.fechaNacimiento || '';
    document.getElementById('sexoPacienteFicha').textContent = paciente.sexo || '';
    document.getElementById('telefonoPacienteFicha').textContent = paciente.numCelular || paciente.telefono || 'No asignado';

    // Edad
    document.getElementById('edadPacienteFicha').textContent = paciente.edad || calcularEdadTexto(paciente.fechaNacimiento);

    // Cita
    document.getElementById('fechaCitaFicha').textContent = cita.fecha || '';
    document.getElementById('horaCitaFicha').textContent = cita.horaProgramada || '';
    document.getElementById('presupuestoCitaFicha').textContent = cita.numPresupuesto || '';
    document.getElementById('tipoPacienteFicha').textContent = cita.tipoPaciente || '';
    document.getElementById('aseguradoraCitaFicha').textContent = cita.aseguradora || '';
    document.getElementById('contratanteCitaFicha').textContent = cita.contratante || '';
    document.getElementById('estadoCitaFicha').textContent = cita.estado || '';
    document.getElementById('medicoCitaFicha').textContent = medico ? `${medico.nombre || ''} ${medico.apellidoP || ''} ${medico.apellidoM || ''}` : '';
}

// Si no tienes edad directa, puedes calcularla:
function calcularEdadTexto(fechaNacStr) {
    if (!fechaNacStr) return '';
    const hoy = new Date();
    const fechaNac = new Date(fechaNacStr);
    let edad = hoy.getFullYear() - fechaNac.getFullYear();
    const m = hoy.getMonth() - fechaNac.getMonth();
    if (m < 0 || (m === 0 && hoy.getDate() < fechaNac.getDate())) {
        edad--;
    }
    return edad;
}
