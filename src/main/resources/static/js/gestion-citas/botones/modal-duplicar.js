const modalDuplicar = new bootstrap.Modal(document.getElementById('modalDuplicarSesion'));

document.getElementById('btn-duplicar').addEventListener('click', () => {
    if (!idFichaSeleccionada) {
        Swal.fire("Error", "No hay ficha seleccionada para duplicar", "error");
        return;
    }

    fetch(`/app/cita/ficha/${idFichaSeleccionada}`)
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                // Llenar los campos del modal
                document.getElementById('fechaDuplicada').value = data.fechaCita;
                document.getElementById('horaDuplicada').value = data.HoraCita;
                document.getElementById('medicoFicha').value = data.medicoId;
                document.getElementById('nombrePacienteDuplicar').value = data.nombrePaciente;

                modalDuplicar.show();
            } else {
                Swal.fire("Advertencia", data.message || "No se pudo obtener los datos de la ficha.", "warning");
            }
        })
        .catch(error => {
            console.error("Error al cargar datos de la ficha:", error);
            Swal.fire("Error", "Ocurrió un error al cargar la información.", "error");
        });
});

document.getElementById('btn-confirmar-duplicar').addEventListener('click', () => {
    const fecha = document.getElementById('fechaDuplicada').value;
    const hora = document.getElementById('horaDuplicada').value;
    const idMedico = document.getElementById('medicoFicha').value;

    if (!fecha || !hora || !idMedico) {
        Swal.fire({
            icon: 'warning',
            title: 'Campos incompletos',
            text: 'Por favor, complete todos los campos requeridos.'
        });
        return;
    }

    const dto = {
        idFichaPaciente: idFichaSeleccionada,
        fecha: fecha,
        horaProgramada: hora,
        idMedico: idMedico
    };

    fetch('/app/gestion-citas/boton/duplicar', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(dto)
    })
    .then(res => res.json())
    .then(data => {
        if (data.success) {
            Swal.fire({
                icon: 'success',
                title: 'Cita duplicada',
                text: data.message,
                timer: 1500,
                showConfirmButton: false
            });

            if (typeof refrescarTablaSegunFiltro === 'function') {
                refrescarTablaSegunFiltro();
            }

            modalDuplicar.hide();
        } else {
            Swal.fire({
                icon: 'error',
                title: 'No se pudo duplicar la cita',
                text: data.message
            });
        }
    })
    .catch(error => {
        console.error("Error en la duplicación:", error);
        Swal.fire({
            icon: 'error',
            title: 'Error del servidor',
            text: 'Ocurrió un error inesperado. Por favor, intente nuevamente.'
        });
    });
});
