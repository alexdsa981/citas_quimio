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
            Swal.fire("Hora inválida", "Por favor, ingresa una hora válida", "error");
            return;
        }

        console.log(`Iniciando protocolo para ficha ${idFichaSeleccionada} a las ${horaInicio}`);

        fetch('/app/protocolo/iniciar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                idFicha: idFichaSeleccionada,
                horaInicio: horaInicio
            })
        })
        .then(res => res.json())
        .then(data => {
            Swal.fire('✅ Éxito', data.message, 'success');
            refrescarTablaSegunFiltro();
        })
        .catch(err => {
            Swal.fire('❌ Error', 'No se pudo iniciar el protocolo', 'error');
            console.error(err);
        });

        modal.hide();
    });

});