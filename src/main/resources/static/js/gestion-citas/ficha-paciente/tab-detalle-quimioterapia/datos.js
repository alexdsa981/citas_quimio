function llenarFormularioDetalleQuimioterapiaFicha(objetoFicha) {
    const detalle = objetoFicha.detalleQuimioterapia || {};

    document.getElementById('medicinasFicha').value = detalle.medicinas || "";
    document.getElementById('observacionesFicha').value = detalle.observaciones || "";
    document.getElementById('anamnesisFicha').value = detalle.anamnesis || "";
    document.getElementById('examenFisicoFicha').value = detalle.examenFisico || "";
    document.getElementById('examenesAuxiliaresFicha').value = detalle.examenesAuxiliares || "";
    document.getElementById('tratamientoFicha').value = detalle.tratamiento || "";
    document.getElementById('evolucionFicha').value = detalle.evolucion || "";
}
