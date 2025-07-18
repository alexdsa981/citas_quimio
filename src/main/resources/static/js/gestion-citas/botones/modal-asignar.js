document.getElementById('btn-asignar').addEventListener('click', () => {
    if (!idFichaSeleccionada) {
        Swal.fire({
            icon: 'warning',
            title: 'Ficha no seleccionada',
            text: 'Por favor, selecciona una ficha antes de asignar.',
        });
        return;
    }

    fetch(`/app/atencion-quimioterapia/ficha/${idFichaSeleccionada}`, {
        method: 'POST'
    })
    .then(response => response.json())
    .then(data => {
        // Mostrar el modal siempre
        const modalElement = document.getElementById('modalAsignar');
        const modalAsignarInstance = new bootstrap.Modal(modalElement);
        modalAsignarInstance.show();
        window.modalAsignarInstance = modalAsignarInstance;

        if (data.success) {
            // Si existe, llenar campos
            document.getElementById('modalEnfermera').value = data.enfermeraId ?? '';
            document.getElementById('modalMedico').value = data.medicoId ?? '';
            document.getElementById('modalCubiculo').value = data.cubiculoId ?? '';

        } else {
            // Si no hay datos, limpiar los campos
            document.getElementById('modalEnfermera').value = '';
            document.getElementById('modalMedico').value = '';
            document.getElementById('modalCubiculo').value = '';

        }


    })
    .catch(error => {
        console.error("Error en el fetch:", error);
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Ocurrió un error al obtener los datos de la ficha.',
        });
    });
});

function guardarAsignacion() {



    const dto = {
        idFicha: idFichaSeleccionada,
        idEnfermera: document.getElementById('modalEnfermera').value,
        idMedico: document.getElementById('modalMedico').value,
        idCubiculo: document.getElementById('modalCubiculo').value,
    };

    fetch('/app/gestion-citas/boton/asignar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dto)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            Swal.fire({
                icon: 'success',
                title: 'Asignación registrada',
                text: data.message || 'La asignación fue guardada exitosamente.',
                timer: 1500,
                showConfirmButton: false
            });

            refrescarTablaSegunFiltro();

            if (window.modalAsignarInstance) {
                window.modalAsignarInstance.hide();
            }
        } else {
            Swal.fire({
                icon: 'error',
                title: 'No se pudo completar la asignación',
                text: data.message || 'Ocurrió un inconveniente al intentar registrar la asignación.'
            });
        }
    })
    .catch(error => {
        console.error("Error en el guardado:", error);
        Swal.fire({
            icon: 'error',
            title: 'Error inesperado',
            text: 'Se produjo un error al guardar los datos. Intente nuevamente o contacte con soporte.'
        });
    });
}
