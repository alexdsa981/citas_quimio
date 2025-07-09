document.addEventListener('DOMContentLoaded', function () {
    const filtroPaciente = document.getElementById('filtroPaciente');
    const filtroEstado = document.getElementById('filtroEstado');
    const filtroCubiculo = document.getElementById('filtroCubiculo');

    filtroPaciente.addEventListener('input', aplicarFiltrosOtros);
    filtroEstado.addEventListener('change', aplicarFiltrosOtros);
    filtroCubiculo.addEventListener('change', aplicarFiltrosOtros);

});

function aplicarFiltrosOtros() {
    const pacienteValor = document.getElementById('filtroPaciente').value.toLowerCase().trim();
    let estadoValor = document.getElementById('filtroEstado').value.toLowerCase().trim();
    let cubiculoValor = document.getElementById('filtroCubiculo').value.toLowerCase().trim();

    if (estadoValor === "todo") estadoValor = "";
    if (cubiculoValor === "todo") cubiculoValor = "";

    const filas = document.querySelectorAll('.fila-ficha');
    let totalVisibles = 0;

    filas.forEach(fila => {
        const columnas = fila.querySelectorAll('td');

        const textoPaciente = (columnas[4]?.textContent + ' ' + columnas[3]?.textContent).toLowerCase();
        const textoEstado = columnas[11]?.textContent.toLowerCase();
        const textoCubiculo = columnas[7]?.textContent.toLowerCase();

        const coincidePaciente = !pacienteValor || textoPaciente.includes(pacienteValor);
        const coincideEstado = !estadoValor || textoEstado === estadoValor;
        const coincideCubiculo = !cubiculoValor || textoCubiculo === cubiculoValor;

        const visible = coincidePaciente && coincideEstado && coincideCubiculo;
        fila.style.display = visible ? '' : 'none';
        if (visible) totalVisibles++;
    });

    const tbody = document.querySelector('table tbody');
    let mensajeNoResultados = document.getElementById('fila-sin-resultados');

    if (totalVisibles === 0) {
        if (!mensajeNoResultados) {
            const tr = document.createElement('tr');
            tr.id = 'fila-sin-resultados';
            tr.innerHTML = `
                <td colspan="11" class="text-center text-muted">
                    ⚠️ No se encontraron fichas que coincidan con los filtros seleccionados.
                </td>`;
            tbody.appendChild(tr);
        }
    } else {
        if (mensajeNoResultados) mensajeNoResultados.remove();
    }
}
