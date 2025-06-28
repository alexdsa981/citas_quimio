document.addEventListener('DOMContentLoaded', function () {
    const modal = new bootstrap.Modal(document.getElementById('modalIniciarProtocolo'));

    document.getElementById('btn-iniciar').addEventListener('click', function () {
        if (!idFichaSeleccionada) {
            Swal.fire({
                icon: 'warning',
                title: '⚠️ Selección requerida',
                text: 'Por favor, selecciona una ficha antes de iniciar el protocolo.',
                confirmButtonText: 'Entendido'
            });
            return;
        }

        const ahora = new Date();
        const hh = String(ahora.getHours()).padStart(2, '0');
        const mm = String(ahora.getMinutes()).padStart(2, '0');
        document.getElementById('horaInicio').value = `${hh}:${mm}`;

        modal.show();
    });



document.querySelector('.btn-confirmar-inicio').addEventListener('click', function () {
    const horaInicio = document.getElementById('horaInicio').value;

    if (!horaInicio) {
        Swal.fire({
            icon: 'error',
            title: 'Hora inválida',
            text: 'Por favor, ingresa una hora válida'
        });
        return;
    }

    console.log(`Iniciando protocolo para ficha ${idFichaSeleccionada} a las ${horaInicio}`);

    fetch('/app/gestion-citas/boton/iniciar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            idFicha: idFichaSeleccionada,
            horaInicio: horaInicio
        })
    })
    .then(async res => {
        const data = await res.json();

        switch (data.status) {
            case 'INICIO_OK':
                Swal.fire({
                    title: '✅ Protocolo iniciado',
                    text: data.message,
                    icon: 'success'
                }).then(() => {
                    refrescarTablaSegunFiltro();
                });
                break;

            case 'NO_ASIGNADO':
                Swal.fire({
                    title: '⚠️ Atención no asignada',
                    text: data.message,
                    icon: 'warning'
                });
                break;

            case 'YA_ATENDIDO':
                Swal.fire({
                    title: 'ℹ️ Atención ya registrada',
                    text: data.message,
                    icon: 'info'
                });
                break;

            case 'EN_CURSO':
                Swal.fire({
                    title: '⛔ Atención en curso',
                    text: data.message,
                    icon: 'warning'
                });
                break;

            case 'ERROR':
            default:
                Swal.fire({
                    title: '❌ Error',
                    text: data.message,
                    icon: 'error'
                });
                break;
        }
    })
    .catch(err => {
        Swal.fire({
            title: '🚨 Error inesperado',
            text: 'No se pudo iniciar el protocolo. Intenta nuevamente.',
            icon: 'error'
        });
        console.error(err);
    });

    modal.hide();
});



});