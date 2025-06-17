function guardarCubiculo() {
    const codigo = document.getElementById("inputCodigoCubiculo").value.trim();

    if (!codigo) {
        Swal.fire({
            icon: 'warning',
            title: 'Campo vacío',
            text: 'Por favor ingresa el código del cubículo.'
        });
        return;
    }

    const formData = new URLSearchParams();
    formData.append("nombre", codigo);

    fetch("/app/clasificadores/cubiculo/nuevo", {
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
                title: 'Cubículo agregado',
                text: 'El nuevo cubículo ha sido registrado correctamente.',
                timer: 1500,
                showConfirmButton: false
            });
            // Cierra el modal
            const modal = bootstrap.Modal.getInstance(document.getElementById('modalAgregarCubiculo'));
            modal.hide();
            // Limpia el input
            document.getElementById("inputCodigoCubiculo").value = "";
            // Recarga tabla
            cargarCubiculos();
        } else {
            throw new Error("Error al registrar el cubículo");
        }
    })
    .catch(error => {
        console.error("Error:", error);
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'No se pudo registrar el cubículo.'
        });
    });
}