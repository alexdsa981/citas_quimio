document.addEventListener('click', function (e) {
    const btn = e.target.closest('#btn-cancelar');
    if (btn) {
        if (!idFichaSeleccionada) {
            Swal.fire({
                icon: 'warning',
                title: 'Ficha no seleccionada',
                text: 'Por favor, selecciona una ficha antes de cancelar.',
            });
            return;
        }

        Swal.fire({
            icon: 'question',
            title: '¿Está seguro de cancelar esta cita?',
            text: 'Esta acción no se puede deshacer.',
            showCancelButton: true,
            confirmButtonText: 'Sí, cancelar',
            cancelButtonText: 'No, mantener',
        }).then((result) => {
            if (result.isConfirmed) {
                fetch('/app/gestion-citas/boton/cancelar', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(idFichaSeleccionada)
                })
                .then(resp => resp.json())
                .then(data => {
                    if (data.success) {
                        Swal.fire({
                            icon: 'success',
                            title: 'Cancelado',
                            text: data.message
                        });

                        // Limpiar la selección para evitar acciones posteriores
                        idFichaSeleccionada = null;

                        refrescarTablaSegunFiltro();
                    } else {
                        Swal.fire({
                            icon: 'error',
                            title: 'Error al cancelar',
                            text: data.message || 'No se pudo cancelar la cita.'
                        });
                    }
                })
                .catch(err => {
                    console.error('Error al cancelar:', err);
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: 'Ocurrió un error inesperado. Inténtalo nuevamente.'
                    });
                });
            }
        });
    }
});
