function llenarVisualFichaPaciente(objetoFicha) {
    const paciente = objetoFicha.cita?.paciente || {};
    const cita = objetoFicha.cita || {};
    const contratante = cita.contratante || {};
    const aseguradora = cita.aseguradora || {};
    const medico = cita.medicoConsulta || {};

    // Paciente
    document.getElementById('nombrePacienteFicha').textContent = `${paciente.nombreCompleto || ''}`;
    document.getElementById('tipoDocumentoPacienteFicha').textContent = paciente.tipoDocumentoNombre || '';
    document.getElementById('numeroDocumentoPacienteFicha').textContent = paciente.numDocIdentidad || '';
    document.getElementById('fechaNacimientoPacienteFicha').textContent = paciente.fechaNacimiento || '';
    document.getElementById('sexoPacienteFicha').textContent = paciente.sexo || '';
    document.getElementById('celularPacienteFicha').textContent = paciente.numCelular || 'No asignado';
    document.getElementById('telefonoPacienteFicha').textContent = paciente.telefono || 'No asignado';

    // Edad
    document.getElementById('edadPacienteFicha').textContent = paciente.edad || calcularEdadTexto(paciente.fechaNacimiento);

    // Cita

    document.getElementById('fechaRegistroCita').textContent = objetoFicha.fechaCreacion || '';
    document.getElementById('usuarioRegistroCita').textContent = cita.usuarioCreacion.nombre || '';


    document.getElementById('fechaCitaFicha').textContent = cita.fecha || '';

    document.getElementById('horaCitaFicha').textContent = cita.horaProgramada ? cita.horaProgramada.slice(0, 5) : '';
    const horaInicio = cita.horaProgramada; // formato "HH:mm"
    const duracionMinutos = cita.duracionMinutosProtocolo || 0;

    if (horaInicio) {
        const [horas, minutos] = horaInicio.split(":").map(Number);
        const fecha = new Date();
        fecha.setHours(horas);
        fecha.setMinutes(minutos + duracionMinutos);

        const horaFinal = fecha.toTimeString().slice(0, 5); // "HH:mm"
        document.getElementById('horaCitaFinFicha').textContent = horaFinal;
    } else {
        document.getElementById('horaCitaFinFicha').textContent = "";
    }


    document.getElementById('estadoCitaFicha').textContent = cita.estado || '';
    document.getElementById('medicoCitaFicha').textContent = medico ? `${medico.nombre || ''} ${medico.apellidoP || ''} ${medico.apellidoM || ''}` : '';

    //Otros
    document.getElementById('aseguradoraCitaFicha').textContent = cita.aseguradora || '';

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
