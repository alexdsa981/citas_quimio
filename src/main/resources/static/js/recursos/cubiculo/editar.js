function abrirModalEditarCubiculo(id, codigo) {
    document.getElementById("idCubiculoEditar").value = id;
    document.getElementById("inputEditarCodigoCubiculo").value = codigo;

    const modal = new bootstrap.Modal(document.getElementById('modalEditarCubiculo'));
    modal.show();
}

function guardarEdicionCubiculo() {
    const id = document.getElementById("idCubiculoEditar").value;
    const nuevoCodigo = document.getElementById("inputEditarCodigoCubiculo").value.trim();

    if (!nuevoCodigo) {
        Swal.fire({
            icon: 'warning',
            title: 'Campo vacío',
            text: 'Por favor ingresa el nuevo código del cubículo.'
        });
        return;
    }

    const formData = new URLSearchParams();
    formData.append("nombre", nuevoCodigo);

    fetch(`/app/clasificadores/cubiculo/actualizar/${id}`, {
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
                text: 'El cubículo ha sido modificado correctamente.',
                timer: 1000,
                showConfirmButton: false
            });

            const modal = bootstrap.Modal.getInstance(document.getElementById('modalEditarCubiculo'));
            modal.hide();
            cargarCubiculos();
        } else {
            throw new Error("No se pudo actualizar");
        }
    })
    .catch(error => {
        console.error("Error al editar:", error);
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'No se pudo actualizar el cubículo.'
        });
    });
}
