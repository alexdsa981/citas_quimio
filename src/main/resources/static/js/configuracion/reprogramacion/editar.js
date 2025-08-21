function abrirModalEditarMotivo(id, nombre) {
    document.getElementById("idMotivoReprogramacionEditar").value = id;
    document.getElementById("inputEditarNombreMotivoReprogramacion").value = nombre;

    const modal = new bootstrap.Modal(document.getElementById('modalEditarMotivoReprogramacion'));
    modal.show();
}

function guardarEdicionMotivoReprogramacion() {
    const id = document.getElementById("idMotivoReprogramacionEditar").value;
    const nuevoNombre = document.getElementById("inputEditarNombreMotivoReprogramacion").value.trim();

    if (!nuevoNombre) {
        Swal.fire({
            icon: 'warning',
            title: 'Campo vacío',
            text: 'Por favor ingresa el nuevo nombre del motivo de reprogramación.'
        });
        return;
    }

    const formData = new URLSearchParams();
    formData.append("nombre", nuevoNombre);

    fetch(`/app/clasificadores/motivo-reprogramacion/actualizar/${id}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: formData
    })
    .then(response => {
        if (response.ok) {
            Swal.fire({
                icon: 'success',
                title: 'Actualizado',
                text: 'El motivo de reprogramación ha sido modificado correctamente.',
                timer: 1000,
                showConfirmButton: false
            });

            const modal = bootstrap.Modal.getInstance(document.getElementById('modalEditarMotivoReprogramacion'));
            modal.hide();
            cargarMotivosReprogramacion();
        } else {
            throw new Error("No se pudo actualizar");
        }
    })
    .catch(error => {
        console.error("Error al editar:", error);
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'No se pudo actualizar el motivo de reprogramación.'
        });
    });
}