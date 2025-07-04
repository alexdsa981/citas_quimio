function llenarFormularioDetalleQuimioterapiaFicha(objetoFicha) {
    const detalle = objetoFicha.detalleQuimioterapia || {};

    document.getElementById('medicinasFicha').value = detalle.medicinas || "";
    document.getElementById('observacionesFicha').value = detalle.observaciones || "";
    document.getElementById('examenesAuxiliaresFicha').value = detalle.examenesAuxiliares || "";
    document.getElementById('tratamientoFicha').value = detalle.tratamiento || "";
}
