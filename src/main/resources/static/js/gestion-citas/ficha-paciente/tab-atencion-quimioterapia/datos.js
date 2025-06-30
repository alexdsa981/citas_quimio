function llenarFormularioFichaAtencionQuimioterapia(objetoFicha) {
    const atencion = objetoFicha.atencionQuimioterapia || {};

    const mostrarTexto = (valor) => valor || "No asignado";

    document.getElementById('enfermeraFichaTexto').textContent = mostrarTexto(atencion.enfermera?.nombreCompleto);
    document.getElementById('medicoFichaTexto').textContent = mostrarTexto(atencion.medico?.nombreCompleto);
    document.getElementById('cubiculoFichaTexto').textContent = mostrarTexto(atencion.cubiculo?.codigo);

    const totalMin = atencion.duracionMinutosProtocolo;
    if (totalMin != null) {
        const horas = Math.floor(totalMin / 60);
        const minutos = totalMin % 60;
        document.getElementById('duracionFichaTexto').textContent = `${horas} h ${minutos} min`;
    } else {
        document.getElementById('duracionFichaTexto').textContent = "No asignado";
    }

    document.getElementById('horaInicioFichaTexto').textContent = mostrarTexto(atencion.horaInicio?.substring(0, 5));
    document.getElementById('horaFinFichaTexto').textContent = mostrarTexto(atencion.horaFin?.substring(0, 5));
}
