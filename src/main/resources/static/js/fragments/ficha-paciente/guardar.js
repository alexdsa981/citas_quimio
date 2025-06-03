//EL BOTON GUARDAR CITA SE RELACIONE CON EL FORM
document.getElementById('btnGuardarCita').addEventListener('click', function () {
    document.getElementById('formFichaPaciente').requestSubmit(); // Enviar el formulario manualmente
});

document.getElementById('formFichaPaciente').addEventListener('submit', async function(e) {
    e.preventDefault(); // Evitar recarga

    // Recoger datos del formulario
    const data = {
        fechaCita: document.getElementById('fechaCitaSeleccionada').value,
        idCubiculo: document.getElementById('cubiculoSelect').value,
        horaProgramada: document.getElementById('horaProgramada').value,
        horasProtocolo: document.getElementById('horasProtocolo').value,
        minutosProtocolo: document.getElementById('minutosProtocolo').value,

        //nroHistoria: document.getElementById('nroHistoria').value,
        numeroDocumento: document.getElementById('numeroDocumento').value,
        idTipoPaciente: document.getElementById('tipoPaciente').value,
        idAseguradora: document.getElementById('aseguradora').value,
        idContratante: document.getElementById('contratante').value,
        apellidoPaterno: document.getElementById('apellidoPaterno').value,
        apellidoMaterno: document.getElementById('apellidoMaterno').value,
        nombres: document.getElementById('nombres').value,
        fechaNacimiento: document.getElementById('fechaNacimiento').value,
        edad: document.getElementById('edad').value,
        sexo: document.getElementById('sexo').value,
        idTipoDocIdentidad: document.getElementById('tipoDocIdentidad').value,
        telefono: document.getElementById('telefono').value,
        tipoEntradaId: document.getElementById('tipoEntrada').value,


        peso: document.getElementById('peso').value,
        talla: document.getElementById('talla').value,
        presionSistolica: document.getElementById('presionSistolica').value,
        presionDiastolica: document.getElementById('presionDiastolica').value,
        frecuenciaCardiaca: document.getElementById('frecuenciaCardiaca').value,
        frecuenciaRespiratoria: document.getElementById('frecuenciaRespiratoria').value,
        temperatura: document.getElementById('temperatura').value,
        saturacionOxigeno: document.getElementById('saturacionOxigeno').value,
        superficieCorporal: document.getElementById('superficieCorporal').value
    };

    try {
        const response = await fetch('/app/cita/ficha-paciente', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        const resJson = await response.json();

        if (response.ok) {
            Swal.fire({
                icon: 'success',
                title: 'Éxito',
                text: resJson.message || 'Ficha guardada correctamente',
                timer: 2000,
                showConfirmButton: false
            }).then(() => {
                // Resetear formulario
                document.getElementById('formFichaPaciente').reset();







                // Cerrar modal (si usas Bootstrap)
                const modal = bootstrap.Modal.getInstance(document.getElementById('modalFichaPaciente'));
                modal.hide();
            });
        } else {
            Swal.fire({
                icon: 'error',
                title: 'Error al guardar',
                text: resJson.message || 'Ocurrió un error al guardar la ficha del paciente.'
            });
        }
    } catch (error) {
        Swal.fire({
            icon: 'error',
            title: 'Error de red',
            text: error.message || 'No se pudo conectar con el servidor.'
        });
    }
});
