document.addEventListener('DOMContentLoaded', function () {
    const modal = new bootstrap.Modal(document.getElementById('modalFinalizarProtocolo'));

    document.addEventListener('click', function (e) {
        const btn = e.target.closest('.btn-finalizar');
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

            // Prellenar hora actual en el input de horaFin
            const ahora = new Date();
            const hh = String(ahora.getHours()).padStart(2, '0');
            const mm = String(ahora.getMinutes()).padStart(2, '0');
            document.getElementById('horaFin').value = `${hh}:${mm}`;

            // Limpiar selección del médico
            const medicoSelect = document.getElementById('medicoSeleccionado');
            if (medicoSelect) medicoSelect.value = "";

            modal.show();
        }
    });

    document.querySelector('.btn-confirmar-fin').addEventListener('click', function () {
        const horaFin = document.getElementById('horaFin').value;
        const medicoSelect = document.getElementById('medicoSeleccionado');
        const idMedico = medicoSelect ? medicoSelect.value : null;

        if (!horaFin) {
            Swal.fire("Hora inválida", "Por favor, ingresa una hora válida", "error");
            return;
        }

        if (!idMedico) {
            Swal.fire("Médico no seleccionado", "Por favor, selecciona un médico", "error");
            return;
        }

        console.log(`Finalizando protocolo para ficha ${idFichaSeleccionada} a las ${horaFin}, médico ID: ${idMedico}`);

        fetch('/app/protocolo/finalizar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                idFicha: idFichaSeleccionada,
                horaFin: horaFin,
                idMedico: idMedico
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
