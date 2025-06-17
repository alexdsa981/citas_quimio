document.addEventListener('DOMContentLoaded', function () {
    const modal = new bootstrap.Modal(document.getElementById('modalFinalizarProtocolo'));

document.addEventListener('click', function (e) {
    const btn = e.target.closest('#btn-atendido'); // usar el ID real
    if (btn) {
        if (!idFichaSeleccionada) {
            Swal.fire({
                icon: 'warning',
                title: '⚠️ Selección requerida',
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
            Swal.fire("Hora inválida", "Por favor, ingresa una hora válida", "error");
            return;
        }


        fetch('/app/protocolo/atender', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                idFicha: idFichaSeleccionada,
                horaFin: horaFin
            })
        })
        .then(res => res.json())
        .then(data => {
            Swal.fire('✅ Éxito', data.message, 'success');
            refrescarTablaSegunFiltro();
        })
        .catch(err => {
            Swal.fire('❌ Error', 'No se pudo finalizar el protocolo', 'error');
            console.error(err);
        });

        modal.hide();
    });
});
