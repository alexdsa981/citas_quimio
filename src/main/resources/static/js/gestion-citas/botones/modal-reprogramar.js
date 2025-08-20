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

            modalReprogramar.show();
        })
        .catch(error => {
            console.error("Error al cargar datos de la ficha:", error);
            Swal.fire("Error", "Ocurrió un error al cargar la información.", "error");
        });
});




function guardarReprogramacion() {
    const selectMotivo = document.getElementById('idMotivoReprogramacion');
    const descripcionInput = document.querySelector('textarea[placeholder="Describe el motivo de la reprogramación..."]');

    const idMotivo = selectMotivo.value;
    const descripcion = descripcionInput.value;

    fetch('/app/gestion-citas/boton/reprogramar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            idFichaSeleccionada,
            idMotivo,
            descripcion
        })
    })
    .then(response => response.json())
    .then(data => {
        Swal.fire('Aviso', data.mensaje, data.mensaje === "Reprogramación guardada correctamente" ? 'success' : 'info')
            .then(() => {
                if (data.mensaje === "Reprogramación guardada correctamente") {
                    // Limpiar los inputs y seleccionar el primer valor del select
                    descripcionInput.value = "";
                    selectMotivo.selectedIndex = 0;
                }
                refrescarTablaSegunFiltro();
                modalReprogramar.hide();
            });
    })
    .catch(error => {
        console.log(error);
        Swal.fire('Error', 'No se pudo guardar la reprogramación', 'error');
    });
}