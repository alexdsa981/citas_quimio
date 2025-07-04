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
        return response.json();
    })
    .then(() => {
        // Nueva llamada al endpoint que trae lista y fecha
        return fetch(`/app/diagnostico/cie/lista/${idFichaSeleccionada}`);
    })
    .then(response => {
        if (!response.ok) throw new Error("No se pudo obtener diagnósticos actualizados");
        return response.json();
    })
    .then(data => {
        // Cargar lista y mostrar fecha
        llenarDetalleCieDesdeFicha({ idFicha: idFichaSeleccionada, ...data });
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
