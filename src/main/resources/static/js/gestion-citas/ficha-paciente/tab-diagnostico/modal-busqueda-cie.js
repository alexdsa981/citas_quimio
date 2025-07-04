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
    tabla.innerHTML = '';

    if (valor.length === 0) {
        tabla.innerHTML = `
            <tr>
                <td colspan="3" class="text-center text-muted">Ingrese un término para buscar</td>
            </tr>`;
        return;
    }

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
                        <td>
                            <button class="btn btn-success btn-sm"
                                    onclick='seleccionarCie(${cie.id}, "${cie.codigoCie}", "${cie.nombre}")'>
                                Seleccionar
                            </button>
                        </td>
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


function seleccionarCie(id, codigo, nombre) {
    console.log("ID del CIE seleccionado:", id);

    const dto = {
        id: id,
        codigo: codigo,
        descripcion: nombre
    };

    fetch('/app/diagnostico/cie/guardar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dto)
    })
    .then(res => {
        if (!res.ok) throw new Error('Error al guardar el CIE');
        console.log("CIE guardado con éxito");

        const lista = document.getElementById('lista-cie-seleccionados');

        // Verificar si ya existe un input con ese ID (para evitar duplicados)
        const yaExiste = lista.querySelector(`input[name="cieIds[]"][value="${id}"]`);
        if (yaExiste) {
            const modal = bootstrap.Modal.getInstance(document.getElementById('modalCie'));
            modal.hide();
            return;
        }

        // Crear el nuevo item
        const item = document.createElement('li');
        item.className = 'list-group-item d-flex justify-content-between align-items-center';
        item.innerHTML = `
            <div>${codigo} - ${nombre}</div>
            <div class="acciones-cie" style="display: none;">
                <button class="btn btn-danger btn-sm" onclick="this.parentElement.parentElement.remove()">Quitar</button>
            </div>
            <input type="hidden" name="cieSeleccionados[]" value="${codigo}">
            <input type="hidden" name="cieIds[]" value="${id}">
        `;

        // Mostrar botón de quitar si está en modo edición
        if (!document.getElementById("btnGuardarCie").disabled) {
            item.querySelector('.acciones-cie').style.display = 'block';
        }

        lista.appendChild(item);

        // Cerrar modal
        const modal = bootstrap.Modal.getInstance(document.getElementById('modalCie'));
        modal.hide();
    })
    .catch(err => {
        console.error("Error al guardar CIE:", err);
        alert("No se pudo guardar el diagnóstico. Intenta nuevamente.");
    });
}
