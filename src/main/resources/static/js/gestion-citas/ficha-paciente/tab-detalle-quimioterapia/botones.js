
function habilitarEdicionDetalleQuimioterapiaFicha() {
    document.getElementById('datosModificablesDetalleQuimioterapiaFicha').classList.remove('form-disabled');
    document.getElementById('btnGuardarDetalleQuimioterapiaFicha').disabled = false;
    document.getElementById('btnModificarDetalleQuimioterapiaFicha').disabled = true;

}

function deshabilitarEdicionDetalleQuimioterapiaFicha() {
    document.getElementById('datosModificablesDetalleQuimioterapiaFicha').classList.add('form-disabled');
    document.getElementById('btnGuardarDetalleQuimioterapiaFicha').disabled = true;
    document.getElementById('btnModificarDetalleQuimioterapiaFicha').disabled = false;
}


function guardarDetalleQuimioterapiaFicha() {
    const dto = {
        medicinas: document.getElementById("medicinasFicha").value,
        observaciones: document.getElementById("observacionesFicha").value,
        anamnesis: document.getElementById("anamnesisFicha").value,
        examenFisico: document.getElementById("examenFisicoFicha").value,
        examenesAuxiliares: document.getElementById("examenesAuxiliaresFicha").value,
        tratamiento: document.getElementById("tratamientoFicha").value,
        evolucion: document.getElementById("evolucionFicha").value,
        idFicha: idFichaSeleccionada
    };


    fetch('/app/detalle-quimioterapia/guardar', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(dto)
    })
    .then(response => {
        if (!response.ok) throw new Error('Error en la respuesta del servidor');
        return response.json();
    })
    .then(data => {
        Swal.fire({
            icon: 'success',
            title: 'Éxito',
            text: data.mensaje
        });
        deshabilitarEdicionDetalleQuimioterapiaFicha();
    })
    .catch(error => {
        console.error('Error al guardar:', error);
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Ocurrió un error al guardar los datos.'
        });
    });
}