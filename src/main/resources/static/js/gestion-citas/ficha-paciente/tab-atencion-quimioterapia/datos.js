function llenarFormularioFichaAtencionQuimioterapia(objetoFicha) {
    const atencion = objetoFicha.atencionQuimioterapia || {};

    const mostrarTexto = (valor) => valor || "No asignado";

    document.getElementById('enfermeraFichaTexto').textContent = mostrarTexto(atencion.enfermera?.nombreCompleto);
    document.getElementById('medicoFichaTexto').textContent = mostrarTexto(atencion.medico?.nombreCompleto);
    document.getElementById('cubiculoFichaTexto').textContent = mostrarTexto(atencion.cubiculo?.codigo);


    document.getElementById('horaInicioFichaTexto').textContent = mostrarTexto(atencion.horaInicio?.substring(0, 5));
    document.getElementById('horaFinFichaTexto').textContent = mostrarTexto(atencion.horaFin?.substring(0, 5));
}
