function llenarDetalleCieDesdeFicha(ficha) {
    if (!ficha?.ficha_id) return;

    fetch(`/app/diagnostico/cie/lista/${ficha.ficha_id}`)
        .then(res => {
            if (!res.ok) throw new Error("No se pudo obtener diagnósticos");
            return res.json();
        })
        .then(info => renderizarDetalleCie(info))
        .catch(err => {
            console.error("Error al obtener los CIE10:", err);
            document.getElementById("mensajeCieNoDiagnostico").style.display = "block";
        });
}

function habilitarModificacionCieFicha() {
    document.getElementById("busquedaCieContainer").style.display = "flex";
    document.getElementById("btnBuscarCie").disabled = false;
    document.getElementById("btnGuardarCie").disabled = false;

    // Re-renderiza con modo edición activo
    fetch(`/app/diagnostico/cie/lista/${idFichaSeleccionada}`)
        .then(res => res.json())
        .then(data => renderizarDetalleCie(data, true));
}

function deshabilitarModificacionCieFicha() {
    document.getElementById("busquedaCieContainer").style.display = "none";
    document.getElementById("btnBuscarCie").disabled = true;
    document.getElementById("btnGuardarCie").disabled = true;

    // Ocultar los botones de quitar nuevamente
    const acciones = document.querySelectorAll("#lista-cie-seleccionados .acciones-cie");
    acciones.forEach(div => div.style.display = "none");
}

function renderizarDetalleCie(info, modoEdicion = false) {
    const lista = document.getElementById("lista-cie-seleccionados");
    const mensajeFecha = document.getElementById("mensajeFechaCie");
    lista.innerHTML = "";
    mensajeFecha.textContent = "";

    const detalleCies = info.detalleCies || [];

    document.getElementById("mensajeCieNoDiagnostico").style.display =
        detalleCies.length > 0 ? "none" : "block";

    detalleCies.forEach(detalle => {
        const id = detalle.id;
        const codigo = detalle.codigo;
        const nombre = detalle.descripcion;

        const item = document.createElement("li");
        item.className = "list-group-item d-flex justify-content-between align-items-center";

        item.innerHTML = `
            <div>${codigo} - ${nombre}</div>
            <div class="acciones-cie" style="display: ${modoEdicion ? 'block' : 'none'};">
                <button class="btn btn-danger btn-sm" onclick="this.parentElement.parentElement.remove()">Quitar</button>
            </div>
            <input type="hidden" name="cieSeleccionados[]" value="${codigo}">
            <input type="hidden" name="cieIds[]" value="${id}">
        `;

        lista.appendChild(item);
    });

    if (info.fechaReferencia) {
        const fecha = new Date(info.fechaReferencia);
        const fechaTexto = fecha.toLocaleDateString('es-PE', {
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        });
        mensajeFecha.textContent = `Diagnósticos referenciados (última ficha con datos: ${fechaTexto})`;
        mensajeFecha.classList.remove("d-none");
    }
}
