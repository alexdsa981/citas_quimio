function guardarMotivoReprogramacion() {
    const nombre = document.getElementById("inputNombreMotivoReprogramacion").value.trim();

    if (!nombre) {
        Swal.fire({
            icon: 'warning',
            title: 'Campo vacío',
            text: 'Por favor ingresa el nombre del motivo de reprogramación.'
        });
        return;
    }

    const formData = new URLSearchParams();
    formData.append("nombre", nombre);

    fetch("/app/clasificadores/motivo-reprogramacion/nuevo", {
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
                title: 'Motivo agregado',
                text: 'El nuevo motivo de reprogramación ha sido registrado correctamente.',
                timer: 1500,
                showConfirmButton: false
            });
            // Cierra el modal
            const modal = bootstrap.Modal.getInstance(document.getElementById('modalAgregarMotivoReprogramacion'));
            modal.hide();
            // Limpia el input
            document.getElementById("inputNombreMotivoReprogramacion").value = "";
            // Recarga tabla/lista
            cargarMotivosReprogramacion();
        } else {
            throw new Error("Error al registrar el motivo de reprogramación");
        }
    })
    .catch(error => {
        console.error("Error:", error);
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'No se pudo registrar el motivo de reprogramación.'
        });
    });
}