function llenarDetalleCieDesdeFicha(data) {
    const lista = document.getElementById("lista-cie-seleccionados");
    lista.innerHTML = ""; // Limpia la lista previa

    const detalleCies = data.detalleCies || [];

    detalleCies.forEach(detalle => {
        const cie = detalle.cie;
        const id = cie.id;
        const codigo = cie.codigo;
        const nombre = cie.descripcion;

        const item = document.createElement("li");
        item.className = "list-group-item d-flex justify-content-between align-items-center";
        item.innerHTML = `
            ${codigo} - ${nombre}
            <button class="btn btn-danger btn-sm" onclick="this.parentElement.remove()">Quitar</button>
            <input type="hidden" name="cieSeleccionados[]" value="${codigo}">
            <input type="hidden" name="cieIds[]" value="${id}">
        `;
        lista.appendChild(item);
    });
}
