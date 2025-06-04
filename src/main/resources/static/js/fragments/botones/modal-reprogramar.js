let modalReprogramarInstance = null;

// Botón para abrir el modal y cargar los datos
document.getElementById('btn-transferir').addEventListener('click', () => {
    if (!idFichaSeleccionada) {
        Swal.fire({
            icon: 'warning',
            title: 'Ficha no seleccionada',
            text: 'Selecciona una ficha antes de reprogramar.',
        });
        return;
    }

    fetch(`/app/cita/ficha/${idFichaSeleccionada}`, {
        method: 'POST'
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            // Llenar campos del modal
            document.getElementById('modalFechaCita').value = data.fechaCita;
            document.getElementById('modalHoraCita').value = data.HoraCita;

            // Seleccionar el médico correcto en el <select>
            document.getElementById('modalMedicoCita').value = data.medicoId;

            // Mostrar el modal
            const modalElement = document.getElementById('modalReprogramar');
            modalReprogramarInstance = new bootstrap.Modal(modalElement);
            modalReprogramarInstance.show();
        } else {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: data.message || 'No se pudo cargar la cita.',
            });
        }
    })
    .catch(error => {
        console.error("Error al cargar cita:", error);
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Error al obtener los datos de la cita.',
        });
    });
});

function guardarReprogramacion() {
    const dto = {
        idFicha: idFichaSeleccionada,
        fecha: document.getElementById('modalFechaCita').value,
        hora: document.getElementById('modalHoraCita').value,
        idMedico: document.getElementById('modalMedicoCita').value
    };

    fetch('/app/cita/reprogramar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dto)
    })
    .then(async response => {
        const data = await response.json();

        if (response.ok) {
            Swal.fire({
                icon: 'success',
                title: 'Reprogramada',
                text: data.message,
                timer: 1500,
                showConfirmButton: false
            });

            // Refrescar tabla si aplica
            if (typeof refrescarTablaSegunFiltro === 'function') {
                refrescarTablaSegunFiltro();
            }

            // Cerrar modal
            if (modalReprogramarInstance) {
                modalReprogramarInstance.hide();
            }
        } else {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: data.message || 'No se pudo reprogramar la cita.',
            });
        }
    })
    .catch(error => {
        console.error("Error al guardar:", error);
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Ocurrió un error al reprogramar la cita.',
        });
    });
}
