function abrirModalCie() {
    const modalCie = new bootstrap.Modal(document.getElementById('modalCie'), {
        backdrop: 'static',
        keyboard: false
    });
    modalCie.show();
}

document.getElementById('filtro-cie').addEventListener('input', function () {
    const valor = this.value.trim();
    const tipoBusqueda = document.getElementById('tipo-busqueda').value;
    const tabla = document.getElementById('tabla-cie');
    tabla.innerHTML = '';  // Limpiamos tabla antes de cualquier acción

    if (valor.length === 0) {
        // Si el input está vacío, mostramos mensaje en la tabla
        tabla.innerHTML = `
            <tr>
                <td colspan="3" class="text-center text-muted">Ingrese un término para buscar</td>
            </tr>`;
        return; // No hacemos fetch
    }

    // Si el input tiene algo
    let url = '/app/clasificadores/cie/externo/buscar/' + tipoBusqueda + '/' + encodeURIComponent(valor);

    fetch(url)
        .then(res => {
            if (!res.ok) {
                throw new Error("Respuesta del servidor no OK");
            }
            return res.json();
        })
        .then(data => {
            if (!Array.isArray(data) || data.length === 0) {
                tabla.innerHTML = `
                    <tr>
                        <td colspan="3" class="text-center text-muted">No se encontró ningún elemento</td>
                    </tr>`;
                return;
            }

            // Si hay resultados, pintarlos
            data.forEach(cie => {
                tabla.innerHTML += `
                    <tr>
                        <td>${cie.codigoCie}</td>
                        <td>${cie.nombre}</td>
                        <td><button class="btn btn-success btn-sm" onclick='seleccionarCie("${cie.codigoCie}", "${cie.nombre}")'>Seleccionar</button></td>
                    </tr>`;
            });
        })
        .catch(error => {
            console.error("Error en la búsqueda:", error);
            tabla.innerHTML = `
                <tr>
                    <td colspan="3" class="text-center text-danger">Error al realizar la búsqueda</td>
                </tr>`;
        });
});


function seleccionarCie(codigo, nombre) {
    const lista = document.getElementById('lista-cie-seleccionados');
    const item = document.createElement('li');
    item.className = 'list-group-item d-flex justify-content-between align-items-center';
    item.innerHTML = `
        ${codigo} - ${nombre}
        <button class="btn btn-danger btn-sm" onclick="this.parentElement.remove()">Quitar</button>
        <input type="hidden" name="cieSeleccionados[]" value="${codigo}">
    `;
    lista.appendChild(item);

    const modal = bootstrap.Modal.getInstance(document.getElementById('modalCie'));
    modal.hide();
}
