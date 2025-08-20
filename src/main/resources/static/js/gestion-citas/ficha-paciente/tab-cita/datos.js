function llenarVisualFichaPaciente(ficha) {

    // Paciente
    document.getElementById('nombrePacienteFicha').textContent = `${ficha.paciente_nombreCompleto || ''}`;
    document.getElementById('tipoDocumentoPacienteFicha').textContent = ficha.paciente_tipoDocumentoNombre || '';
    document.getElementById('numeroDocumentoPacienteFicha').textContent = ficha.paciente_numDocIdentidad || '';
    document.getElementById('fechaNacimientoPacienteFicha').textContent = ficha.paciente_fechaNacimiento || '';
    document.getElementById('sexoPacienteFicha').textContent = ficha.paciente_sexo || '';
    document.getElementById('celularPacienteFicha').textContent = ficha.paciente_numCelular || 'No asignado';
    document.getElementById('telefonoPacienteFicha').textContent = ficha.paciente_telefono || 'No asignado';

    // Edad
    document.getElementById('edadPacienteFicha').textContent = ficha.paciente_edad || calcularEdadTexto(ficha.paciente_fechaNacimiento);

    // Cita

    document.getElementById('fechaRegistroCita').textContent = ficha.cita_fechaRegistro || '';
    document.getElementById('usuarioRegistroCita').textContent = ficha.cita_usuarioCreacion || '';


    document.getElementById('fechaCitaFicha').textContent = ficha.cita_fecha || '';

    document.getElementById('horaCitaFicha').textContent = ficha.cita_horaProgramada ? ficha.cita_horaProgramada.slice(0, 5) : '';
    const horaInicio = ficha.cita_horaProgramada; // formato "HH:mm"
    const duracionMinutos = ficha.cita_duracionMinutosProtocolo || 0;

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

    // Mostrar/ocultar campos de reprogramación según el estado
    if (ficha.cita_estado === "REPROGRAMADO") {
        document.getElementById('reprogramacionCampos').style.display = "block";
        document.getElementById('motivoReprogramacionCita').textContent = ficha.cita_motivoReprogramacion || '';
        document.getElementById('descripcionReprogramacionCita').textContent = ficha.cita_descripcionReprogramacion || '';
    } else {
        document.getElementById('reprogramacionCampos').style.display = "none";
        document.getElementById('motivoReprogramacionCita').textContent = '';
        document.getElementById('descripcionReprogramacionCita').textContent = '';
    }


    document.getElementById('estadoCitaFicha').textContent = ficha.cita_estado || '';
    document.getElementById('medicoCitaFicha').textContent = ficha.cita_medico || '';

    //Otros
    document.getElementById('aseguradoraCitaFicha').textContent = ficha.cita_aseguradora || '';

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
