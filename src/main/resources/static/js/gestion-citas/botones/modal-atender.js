document.addEventListener('DOMContentLoaded', function () {
    const modal = new bootstrap.Modal(document.getElementById('modalFinalizarProtocolo'));

document.addEventListener('click', function (e) {
    const btn = e.target.closest('#btn-atendido'); // usar el ID real
    if (btn) {
        if (!idFichaSeleccionada) {
            Swal.fire({
                icon: 'warning',
                title: 'Selección requerida',
                text: 'Por favor, selecciona una ficha antes de finalizar el protocolo.',
                confirmButtonText: 'Entendido'
            });
            return;
        }

        const ahora = new Date();
        const hh = String(ahora.getHours()).padStart(2, '0');
        const mm = String(ahora.getMinutes()).padStart(2, '0');
        document.getElementById('horaFin').value = `${hh}:${mm}`;

        modal.show();
    }
});


document.querySelector('.btn-confirmar-fin').addEventListener('click', function () {
    const horaFin = document.getElementById('horaFin').value;

    if (!horaFin) {
        Swal.fire({
            icon: 'warning',
            title: 'Hora no válida',
            text: 'Por favor, ingrese una hora válida para finalizar el protocolo.'
        });
        return;
    }

    fetch('/app/gestion-citas/boton/atender', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            idFicha: idFichaSeleccionada,
            horaFin: horaFin
        })
    })
    .then(async res => {
        const data = await res.json();

        if (res.ok && data.success) {
            Swal.fire({
                icon: 'success',
                title: 'Finalización exitosa',
                text: data.message,
                timer: 1500,
                showConfirmButton: false
            });

            if (typeof refrescarTablaSegunFiltro === 'function') {
                refrescarTablaSegunFiltro();
            }

            if (modal) {
                modal.hide();
            }
        } else {
            Swal.fire({
                icon: 'error',
                title: 'No se pudo finalizar el protocolo',
                text: data.message || 'Ha ocurrido un error inesperado durante la finalización.'
            });
        }
    })
    .catch(error => {
        console.error("Error al finalizar:", error);
        Swal.fire({
            icon: 'error',
            title: 'Error del servidor',
            text: 'No se pudo completar la operación debido a un error interno. Por favor, intente nuevamente más tarde.'
        });
    });
});

});
