function llenarFormularioFichaAtencionQuimioterapia(objetoFicha) {
    const atencion = objetoFicha.atencionQuimioterapiaList?.[0] || {}; // Tomamos la primera atención, si existe

    // Enfermera
    const enfermeraSelect = document.getElementById('enfermeraFicha');
    if (atencion.enfermera && atencion.enfermera.id) {
        enfermeraSelect.value = atencion.enfermera.id;
    } else {
        enfermeraSelect.value = "";
    }

    // Médico
    const medicoSelect = document.getElementById('medicoFicha');
    if (atencion.medico && atencion.medico.id) {
        medicoSelect.value = atencion.medico.id;
    } else {
        medicoSelect.value = "";
    }

    // Cubículo
    const cubiculoSelect = document.getElementById('cubiculoSelectFicha');
    if (atencion.cubiculo && atencion.cubiculo.id) {
        cubiculoSelect.value = atencion.cubiculo.id;
    } else {
        cubiculoSelect.value = "";
    }

    // Duración del protocolo
    const totalMinutos = atencion.duracionMinutosProtocolo || 0;
    const horas = Math.floor(totalMinutos / 60);
    const minutos = totalMinutos % 60;

    document.getElementById('horasProtocoloFicha').value = horas;
    document.getElementById('minutosProtocoloFicha').value = minutos;

    // Hora inicio
    const horaInicio = atencion.horaInicio ? atencion.horaInicio.substring(0, 5) : "";
    document.getElementById('horaInicioProtocolo').value = horaInicio;

    // Hora fin
    const horaFin = atencion.horaFin ? atencion.horaFin.substring(0, 5) : "";
    document.getElementById('horaFinProtocolo').value = horaFin;
}
