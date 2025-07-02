document.addEventListener('click', function (e) {
    const btnRetroceso = e.target.closest('#btn-retroceso');
    if (!btnRetroceso) return;

    if (!idFichaSeleccionada) {
        Swal.fire({
            icon: 'warning',
            title: 'Ficha no seleccionada',
            text: 'Por favor, seleccione una ficha antes de intentar retroceder el estado.',
            confirmButtonText: 'Entendido'
        });
        return;
    }

    Swal.fire({
        icon: 'question',
        title: '¿Confirmar retroceso?',
        text: 'Esta acción regresará la cita al estado anterior. ¿Desea continuar?',
        showCancelButton: true,
        confirmButtonText: 'Sí, retroceder',
        cancelButtonText: 'Cancelar'
    }).then(result => {
        if (!result.isConfirmed) return;

        fetch('/app/gestion-citas/boton/retroceso', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ idFicha: idFichaSeleccionada })
        })
        .then(res => res.json())
        .then(data => {
            let icono = 'info';
            let titulo = 'ℹ️ Resultado';

            switch (data.status) {
                case 'CAMBIO_OK':
                    icono = 'success';
                    titulo = '✅ Estado actualizado';
                    break;
                case 'YA_ATENDIDO':
                    icono = 'info';
                    titulo = 'ℹ️ Atención finalizada';
                    break;
                case 'NO_ASIGNADO':
                    icono = 'warning';
                    titulo = '⚠️ Estado inicial alcanzado';
                    break;
                case 'NO_AUTORIZADO':
                    icono = 'warning';
                    titulo = '🚫 Acceso denegado';
                    break;
                case 'ERROR':
                    icono = 'error';
                    titulo = '❌ Error';
                    break;
                default:
                    icono = 'info';
                    titulo = 'ℹ️ Estado desconocido';
                    break;
            }

            Swal.fire({
                icon: icono,
                title: titulo,
                text: data.message || 'No se pudo procesar la solicitud.'
            });

            if (typeof refrescarTablaSegunFiltro === 'function') {
                refrescarTablaSegunFiltro();
            }
        })
        .catch(err => {
            console.error("Error al retroceder:", err);
            Swal.fire({
                icon: 'error',
                title: '❌ Error del servidor',
                text: 'Ocurrió un error inesperado al intentar retroceder el estado.'
            });
        });
    });
});
