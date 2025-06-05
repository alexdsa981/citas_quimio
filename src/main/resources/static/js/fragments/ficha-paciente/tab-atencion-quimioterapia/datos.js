function llenarFormularioFichaAtencionQuimioterapia(objetoFicha) {
    const atencion = objetoFicha.atencionQuimioterapia || {};

    // Enfermera
    const enfermeraSelect = document.getElementById('enfermeraFicha');
    enfermeraSelect.value = atencion.enfermera?.id || "";

    // Médico
    const medicoSelect = document.getElementById('medicoFicha');
    medicoSelect.value = atencion.medico?.id || "";

    // Cubículo
    const cubiculoSelect = document.getElementById('cubiculoSelectFicha');
    cubiculoSelect.value = atencion.cubiculo?.id || "";

    // Duración del protocolo (minutos a hh:mm)
    const totalMinutos = atencion.duracionMinutosProtocolo || 0;
    document.getElementById('horasProtocoloFicha').value = Math.floor(totalMinutos / 60);
    document.getElementById('minutosProtocoloFicha').value = totalMinutos % 60;

    // Hora de inicio
    document.getElementById('horaInicioProtocolo').value = atencion.horaInicio?.substring(0, 5) || "";

    // Hora de fin
    document.getElementById('horaFinProtocolo').value = atencion.horaFin?.substring(0, 5) || "";
}
