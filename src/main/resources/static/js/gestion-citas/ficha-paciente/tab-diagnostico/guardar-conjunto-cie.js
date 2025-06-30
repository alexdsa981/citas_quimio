function guardarCieSeleccionados() {
    const lista = document.querySelectorAll('input[name="cieIds[]"]');
    const ids = [];

    lista.forEach(input => {
        ids.push(parseInt(input.value));
    });

    const payload = {
        idFicha: idFichaSeleccionada,
        cieIds: ids
    };

    fetch('/app/diagnostico/cie/guardar-lista', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
    })
    .then(response => {
        if (!response.ok) throw new Error('Error al guardar');
        return response.json(); // Aquí asumimos que devuelve los cie guardados
    })
    .then(data => {
        // Recargar lista limpia
        llenarDetalleCieDesdeFicha(data); // <- Reutilizas la función con los datos actualizados
        deshabilitarModificacionCieFicha();

        Swal.fire({
            icon: 'success',
            title: 'Guardado exitosamente',
            text: 'Se actualizaron los diagnósticos CIE10.',
            timer: 1000,
            showConfirmButton: false
        });
    })
    .catch(error => {
        console.error(error);
        Swal.fire({
            icon: 'error',
            title: 'Error al guardar',
            text: 'Ocurrió un error al guardar los CIE10.',
            confirmButtonText: 'Aceptar'
        });
    });
}
