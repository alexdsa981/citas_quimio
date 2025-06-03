let idFichaSeleccionada = null;

document.addEventListener('DOMContentLoaded', function () {
    document.addEventListener('click', function (e) {
        const fila = e.target.closest('.fila-ficha');
        if (fila) {
            idFichaSeleccionada = fila.dataset.idFicha;
            console.log('ID de ficha seleccionada:', idFichaSeleccionada);

            // Quitar selección anterior
            document.querySelectorAll('.fila-ficha.seleccionada').forEach(f => {
                f.classList.remove('seleccionada');
            });

            // Marcar como seleccionada
            fila.classList.add('seleccionada');
        }
    });
});



