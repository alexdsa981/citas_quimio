const modalReprogramar = new bootstrap.Modal(document.getElementById('modalReprogramar'));

document.getElementById('btn-reprogramar').addEventListener('click', () => {
    if (!idFichaSeleccionada) {
        Swal.fire("Ficha no seleccionada", "Por favor, selecciona una ficha antes de reprogramar.", "warning");
        return;
    }

    fetch(`/app/cita/ficha/${idFichaSeleccionada}`)
        .then(response => {
            if (!response.ok) throw new Error("Error al obtener datos");
            return response.json();
        })
        .then(data => {
            document.getElementById('nombrePacienteReprogramar').value = data.nombrePaciente || '';
            document.getElementById('modalFechaReprogramacion').value = data.fechaCita || '';
            document.getElementById('modalHoraReprogramacion').value = data.horaCita || '';

            const duracion = data.duracion || 0;
            document.getElementById('reprogramarHorasProtocoloCita').value = Math.floor(duracion / 60).toString();
            document.getElementById('reprogramarMinutosProtocoloCita').value = (duracion % 60).toString();

            modalReprogramar.show();
        })
        .catch(error => {
            console.error("Error al cargar datos de la ficha:", error);
            Swal.fire("Error", "Ocurrió un error al cargar la información.", "error");
        });
});