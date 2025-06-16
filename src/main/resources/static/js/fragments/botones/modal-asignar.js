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

            const duracion = data.duracion ?? 0;
            const horas = Math.floor(duracion / 60);
            const minutos = duracion % 60;

            document.getElementById('modalHorasProtocolo').value = horas;
            document.getElementById('modalMinutosProtocolo').value = minutos;
        } else {
            // Si no hay datos, limpiar los campos
            document.getElementById('modalEnfermera').value = '';
            document.getElementById('modalMedico').value = '';
            document.getElementById('modalCubiculo').value = '';
            document.getElementById('modalHorasProtocolo').value = '';
            document.getElementById('modalMinutosProtocolo').value = '';
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
    const horas = parseInt(document.getElementById('modalHorasProtocolo').value || 0);
    const minutos = parseInt(document.getElementById('modalMinutosProtocolo').value || 0);
    const duracionTotal = horas * 60 + minutos;

    // Validación: al menos una duración válida
    if (duracionTotal <= 0) {
        Swal.fire({
            icon: 'warning',
            title: 'Duración inválida',
            text: 'Debe ingresar al menos 1 minuto o 1 hora en el protocolo.'
        });
        return;
    }

    const dto = {
        idFicha: idFichaSeleccionada,
        idEnfermera: document.getElementById('modalEnfermera').value,
        idMedico: document.getElementById('modalMedico').value,
        idCubiculo: document.getElementById('modalCubiculo').value,
        duracionMinutos: duracionTotal
    };

    fetch('/app/atencion-quimioterapia/guardar', {
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
                title: 'Guardado',
                text: 'La asignación se guardó correctamente.',
                timer: 1500,
                showConfirmButton: false
            });

            refrescarTablaSegunFiltro();

            // Cerrar el modal si existe
            if (window.modalAsignarInstance) {
                window.modalAsignarInstance.hide();
            }
        } else {
            Swal.fire({
                icon: 'error',
                title: 'Error al guardar',
                text: data.message || 'No se pudo guardar la asignación.',
            });
        }
    })
    .catch(error => {
        console.error("Error en el guardado:", error);
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Ocurrió un error al guardar los datos.',
        });
    });
}
