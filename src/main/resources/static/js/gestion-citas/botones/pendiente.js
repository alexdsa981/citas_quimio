document.addEventListener('click', function (e) {
    const btnPendiente = e.target.closest('#btn-pendiente');
    if (btnPendiente) {
        if (!idFichaSeleccionada) {
            Swal.fire({
                icon: 'warning',
                title: '⚠️ Selección requerida',
                text: 'Por favor, selecciona una ficha antes de continuar.',
                confirmButtonText: 'Entendido'
            });
            return;
        }

        Swal.fire({
            icon: 'question',
            title: '¿Estás seguro?',
            text: '¿Deseas regresar el protocolo a estado Pendiente?',
            showCancelButton: true,
            confirmButtonText: 'Sí, confirmar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                fetch('/app/gestion-citas/boton/pendiente', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ idFicha: idFichaSeleccionada })
                })
                .then(res => res.json())
                .then(data => {
                    Swal.fire('✅ Éxito', data.message, 'success');
                    refrescarTablaSegunFiltro();
                })
                .catch(err => {
                    Swal.fire('❌ Error', 'No se pudo cambiar a estado Pendiente', 'error');
                    console.error(err);
                });
            }
        });
    }
});
