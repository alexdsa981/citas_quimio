function llenarFormularioFichaAtencionQuimioterapia(ficha) {
    const mostrarTexto = (valor) => valor || "No asignado";

    document.getElementById('enfermeraFichaTexto').textContent = mostrarTexto(ficha.atencion_enfermera);
    document.getElementById('medicoFichaTexto').textContent = mostrarTexto(ficha.atencion_medico);
    document.getElementById('cubiculoFichaTexto').textContent = mostrarTexto(ficha.atencion_cubiculo);


    document.getElementById('horaInicioFichaTexto').textContent = mostrarTexto(ficha.atencion_horaInicio?.substring(0, 5) || "No Asignado");
    document.getElementById('horaFinFichaTexto').textContent = mostrarTexto(ficha.atencion_horaFin?.substring(0, 5) || "No Asignado");
}
