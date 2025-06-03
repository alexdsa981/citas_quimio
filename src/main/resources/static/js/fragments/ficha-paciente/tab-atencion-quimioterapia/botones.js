function habilitarEdicionAtencionQuimioterapiaFicha() {
    document.getElementById('datosModificablesAtencionQuimioterapiaFicha').classList.remove('form-disabled');
    document.getElementById('btnGuardarAtencionQuimioterapiaFicha').disabled = false;
    document.getElementById('btnModificarAtencionQuimioterapiaFicha').disabled = true;

}

function deshabilitarEdicionAtencionQuimioterapiaFicha() {
    document.getElementById('datosModificablesAtencionQuimioterapiaFicha').classList.add('form-disabled');
    document.getElementById('btnGuardarAtencionQuimioterapiaFicha').disabled = true;
    document.getElementById('btnModificarAtencionQuimioterapiaFicha').disabled = false;
}

function guardarAtencionQuimioterapiaFicha() {
    const horas = parseInt(document.getElementById('horasProtocoloFicha').value) || 0;
    const minutos = parseInt(document.getElementById('minutosProtocoloFicha').value) || 0;
    const duracionTotalMinutos = horas * 60 + minutos;

    const data = {
        idEnfermera: parseInt(document.getElementById('enfermeraFicha').value),
        idMedico: parseInt(document.getElementById('medicoFicha').value),
        idCubiculo: parseInt(document.getElementById('cubiculoSelectFicha').value),
        duracionMinutos: duracionTotalMinutos,
        idFicha: idFichaSeleccionada,
    };

    fetch('/app/atencion-quimioterapia/guardar', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    })
    .then(res => res.json())
    .then(res => {
        if (res.success) {
            Swal.fire({
                icon: 'success',
                title: 'Datos guardados',
                text: res.message
            });
            refrescarTablaSegunFiltro();
            deshabilitarEdicionAtencionQuimioterapiaFicha();
        } else {
            Swal.fire({
                icon: 'warning',
                title: 'Aviso',
                text: res.message
            });
        }
    })
    .catch(err => {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Ocurrió un problema al guardar los datos: ' + err
        });
    });
}
