let aseguradorasCargadas = [];
let inputAseguradoraDestino = null; // <-- aquí guardamos el input que se va a llenar

// Cuando se abre el modal, cargar aseguradoras si no están cargadas
document.getElementById("modalBuscarAseguradora").addEventListener("show.bs.modal", () => {
    if (aseguradorasCargadas.length === 0) {
        fetch("/app/aseguradora/externo/lista")
            .then(res => res.ok ? res.json() : [])
            .then(data => {
                aseguradorasCargadas = data || [];
                mostrarResultadosAseguradora(aseguradorasCargadas);
            })
            .catch(err => {
                console.error("Error al cargar aseguradoras:", err);
                document.getElementById("resultadoAseguradoras").innerHTML = `
                    <div class="text-danger">
                        <i class="bi bi-exclamation-triangle-fill"></i> Error al cargar aseguradoras.
                    </div>`;
            });
    } else {
        mostrarResultadosAseguradora(aseguradorasCargadas);
    }
});

// Quitar tildes y normalizar texto
function quitarTildes(texto) {
    return texto
        .normalize("NFD")
        .replace(/[\u0300-\u036f]/g, "")
        .toLowerCase();
}

// Filtrado en el front ignorando tildes
document.getElementById("inputBuscarNombreAseguradora").addEventListener("input", function () {
    const valor = quitarTildes(this.value.trim());
    if (valor.length === 0) {
        mostrarResultadosAseguradora(aseguradorasCargadas);
        return;
    }
    const filtradas = aseguradorasCargadas.filter(a =>
        quitarTildes(a).includes(valor)
    );
    mostrarResultadosAseguradora(filtradas);
});

function mostrarResultadosAseguradora(lista) {
    const contenedor = document.getElementById("resultadoAseguradoras");
    contenedor.innerHTML = "";

    if (!lista || lista.length === 0) {
        contenedor.innerHTML = `
            <div class="text-muted">
                <i class="bi bi-info-circle"></i> No se encontraron aseguradoras.
            </div>`;
        return;
    }

    lista.forEach(nombre => {
        const item = document.createElement("button");
        item.className = "list-group-item list-group-item-action";
        item.textContent = nombre;
        item.onclick = () => seleccionarAseguradoraDesdeModal(nombre);
        contenedor.appendChild(item);
    });
}

function seleccionarAseguradoraDesdeModal(nombre) {
    if (inputAseguradoraDestino) {
        document.getElementById(inputAseguradoraDestino).value = nombre;
    }

    document.getElementById("inputBuscarNombreAseguradora").value = "";
    mostrarResultadosAseguradora(aseguradorasCargadas);

    // Cerrar modal
    const modal = bootstrap.Modal.getInstance(document.getElementById("modalBuscarAseguradora"));
    modal.hide();
}

function abrirModalAseguradora(idInputDestino) {
    inputAseguradoraDestino = idInputDestino; // <-- guardamos cuál input se llenará
    const modal = new bootstrap.Modal(document.getElementById("modalBuscarAseguradora"));
    modal.show();
}
