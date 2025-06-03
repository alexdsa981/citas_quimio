// EL BOTON GUARDAR CITA SE RELACIONE CON EL FORM
document.getElementById('btnGuardarCita').addEventListener('click', function () {
    document.getElementById('formCrearCita').requestSubmit(); // Enviar el formulario manualmente
});

document.getElementById('formCrearCita').addEventListener('submit', async function(e) {
    e.preventDefault(); // Evitar recarga

    // Validar que la fecha no sea pasada
    const fechaCitaStr = document.getElementById('fechaCitaSeleccionada').value;
    if (!fechaCitaStr) {
        Swal.fire({
            icon: 'warning',
            title: 'Fecha inválida',
            text: 'Por favor seleccione una fecha para la cita.'
        });
        return;
    }

    // Parsear fecha local yyyy-mm-dd (input date)
    const partes = fechaCitaStr.split('-');
    const fechaCita = new Date(partes[0], partes[1] - 1, partes[2]);
    fechaCita.setHours(0,0,0,0);

    const hoy = new Date();
    hoy.setHours(0,0,0,0);


    if (fechaCita < hoy) {
        Swal.fire({
            icon: 'error',
            title: 'Fecha inválida',
            text: 'La fecha de la cita no puede ser un día pasado. Seleccione una fecha válida.'
        });
        return;
    }

    // Recoger datos del formulario
    const data = {
        fechaCita: fechaCitaStr,
        horaProgramada: document.getElementById('horaProgramadaCita').value,

        numeroDocumento: document.getElementById('numeroDocumentoCita').value,
        idTipoPaciente: document.getElementById('tipoPacienteCita').value,
        idAseguradora: document.getElementById('aseguradoraCita').value,
        idContratante: document.getElementById('contratanteCita').value,
        apellidoPaterno: document.getElementById('apellidoPaternoCita').value,
        apellidoMaterno: document.getElementById('apellidoMaternoCita').value,
        nombres: document.getElementById('nombresCita').value,
        fechaNacimiento: document.getElementById('fechaNacimientoCita').value,
        edad: document.getElementById('edadCita').value,
        sexo: document.getElementById('sexoCita').value,
        idTipoDocIdentidad: document.getElementById('tipoDocIdentidadCita').value,
        telefono: document.getElementById('telefonoCita').value,
        tipoEntradaId: document.getElementById('tipoEntradaCita').value,

        medicoId: document.getElementById('idMedicoCita').value,

    };

    try {
        const response = await fetch('/app/cita/agendar', {
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
                text: resJson.message || 'Cita agendada correctamente',
                timer: 2000,
                showConfirmButton: false
            }).then(() => {
                // Resetear formulario
                document.getElementById('formCrearCita').reset();

                // Al terminar con éxito, desbloquear campo tipoDocIdentidad
                document.getElementById('tipoDocIdentidadCita').disabled = false;
                document.getElementById('btnBuscarPaciente').disabled = false;

                document.getElementById('btnLimpiarModalCita').disabled = true;
                document.getElementById('btnModificarPacienteModalCita').disabled = true;
                document.getElementById('btnGuardarCita').disabled = true;

                const campos = document.querySelectorAll(".bloqueable");
                campos.forEach(campo => {
                    campo.readOnly = true;
                    campo.disabled = true;
                    campo.style.backgroundColor = "#e9ecef";
                    campo.style.color = "#6c757d";
                    campo.style.cursor = "not-allowed";
                    campo.setAttribute("tabindex", "-1");
                    campo.onmousedown = (e) => e.preventDefault();
                });


                // Cerrar modal
                const modal = bootstrap.Modal.getInstance(document.getElementById('modalAgendarCita'));
                modal.hide();
                refrescarTablaSegunFiltro();

            });
        } else {
            Swal.fire({
                icon: 'error',
                title: 'Error al guardar',
                text: resJson.message || 'Ocurrió un error al guardar la cita del paciente.'
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
