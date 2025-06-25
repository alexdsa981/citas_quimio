function guardarCieSeleccionados() {
    const lista = document.querySelectorAll('input[name="cieIds[]"]');
    const ids = [];

    lista.forEach(input => {
        ids.push(parseInt(input.value));
    });

    // Asegúrate de que idFichaSeleccionada esté definido en el scope global
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
        return response.text();
    })
    .then(data => {
        alert(data);
    })
    .catch(error => {
        console.error(error);
        alert("Ocurrió un error al guardar los CIE10.");
    });
}
