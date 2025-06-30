function llenarDetalleCieDesdeFicha(data) {
    const lista = document.getElementById("lista-cie-seleccionados");
    lista.innerHTML = "";

    const detalleCies = data.detalleCies || [];

    // Mostrar u ocultar mensaje informativo
    document.getElementById("mensajeCieNoDiagnostico").style.display = detalleCies.length > 0 ? "none" : "block";

    detalleCies.forEach(detalle => {
        const cie = detalle.cie;
        const id = cie.id;
        const codigo = cie.codigo;
        const nombre = cie.descripcion;

        const item = document.createElement("li");
        item.className = "list-group-item d-flex justify-content-between align-items-center";

        item.innerHTML = `
            <div>${codigo} - ${nombre}</div>
            <div class="acciones-cie" style="display: none;">
                <button class="btn btn-danger btn-sm" onclick="this.parentElement.parentElement.remove()">Quitar</button>
            </div>
            <input type="hidden" name="cieSeleccionados[]" value="${codigo}">
            <input type="hidden" name="cieIds[]" value="${id}">
        `;

        lista.appendChild(item);
    });
}

function habilitarModificacionCieFicha() {
    document.getElementById("busquedaCieContainer").style.display = "flex";
    document.getElementById("btnBuscarCie").disabled = false;
    document.getElementById("btnGuardarCie").disabled = false;

    // Mostrar los botones de quitar
    const acciones = document.querySelectorAll("#lista-cie-seleccionados .acciones-cie");
    acciones.forEach(div => div.style.display = "block");
}

function deshabilitarModificacionCieFicha() {
    document.getElementById("busquedaCieContainer").style.display = "none";
    document.getElementById("btnBuscarCie").disabled = true;
    document.getElementById("btnGuardarCie").disabled = true;

    // Ocultar los botones de quitar nuevamente
    const acciones = document.querySelectorAll("#lista-cie-seleccionados .acciones-cie");
    acciones.forEach(div => div.style.display = "none");
}
