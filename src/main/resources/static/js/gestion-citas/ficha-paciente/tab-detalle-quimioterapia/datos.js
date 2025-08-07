function llenarFormularioDetalleQuimioterapiaFicha(ficha) {
    document.getElementById('medicinasFicha').value = ficha.detalle_medicinas || "";
    document.getElementById('observacionesFicha').value = ficha.detalle_observaciones || "";
    document.getElementById('tratamientoFicha').value = ficha.detalle_tratamiento || "";
}
