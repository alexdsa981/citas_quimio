function habilitarEdicionPacienteFicha() {
    document.getElementById('datosModificablesPacienteFicha').classList.remove('form-disabled');
    document.getElementById('btnGuardarPacienteFicha').disabled = false;
    document.getElementById('btnModificarPacienteFicha').disabled = true;

}

function deshabilitarEdicionPacienteFicha() {
    document.getElementById('datosModificablesPacienteFicha').classList.add('form-disabled');
    document.getElementById('btnGuardarPacienteFicha').disabled = true;
    document.getElementById('btnModificarPacienteFicha').disabled = false;
}

function guardarPacienteFicha() {
    const data = {
        idTipoDocIdentidad: parseInt(document.getElementById('tipoDocIdentidadFicha').value),
        numeroDocumento: document.getElementById('numeroDocumentoFicha').value,

        apellidoPaterno: document.getElementById('apellidoPaternoFicha').value,
        apellidoMaterno: document.getElementById('apellidoMaternoFicha').value,
        nombres: document.getElementById('nombresFicha').value,
        fechaNacimiento: document.getElementById('fechaNacimientoFicha').value,
        edad: document.getElementById('edadFicha').value,
        sexo: document.getElementById('sexoFicha').value,
        telefono: document.getElementById('telefonoFicha').value,

        idTipoPaciente: parseInt(document.getElementById('tipoPacienteFicha').value),
        idAseguradora: parseInt(document.getElementById('aseguradoraFicha').value),
        idContratante: parseInt(document.getElementById('contratanteFicha').value)
    };

    fetch('/app/paciente/actualizar', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    })
    .then(res => res.json())
    .then(res => {
        if (res.message) {
            Swal.fire({
                icon: 'warning',
                title: 'Aviso',
                text: res.message
            });
        } else {
            Swal.fire({
                icon: 'success',
                title: 'Paciente actualizado',
                text: 'Los datos del paciente se actualizaron correctamente.'
            });
            refrescarTablaSegunFiltro();
            deshabilitarEdicionPacienteFicha();
        }
    })
    .catch(err => {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Ocurrió un problema al enviar los datos: ' + err
        });
    });
}

