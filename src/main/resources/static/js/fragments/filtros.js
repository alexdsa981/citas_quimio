document.addEventListener('DOMContentLoaded', function () {
    const filtroPaciente = document.getElementById('filtroPaciente');
    const filtroEstado = document.getElementById('filtroEstado');
    const filtroCubiculo = document.getElementById('filtroCubiculo');

    filtroPaciente.addEventListener('input', aplicarFiltros);
    filtroEstado.addEventListener('change', aplicarFiltros);
    filtroCubiculo.addEventListener('change', aplicarFiltros);
});

function aplicarFiltros() {
    const pacienteValor = document.getElementById('filtroPaciente').value.toLowerCase().trim();
    const estadoValor = document.getElementById('filtroEstado').value.toLowerCase().trim();
    const cubiculoValor = document.getElementById('filtroCubiculo').options[document.getElementById('filtroCubiculo').selectedIndex]?.text.toLowerCase().trim();

    const filas = document.querySelectorAll('.fila-ficha');
    let totalVisibles = 0;

    filas.forEach(fila => {
        const columnas = fila.querySelectorAll('td');

        const textoPaciente = (columnas[0]?.textContent + ' ' + columnas[1]?.textContent).toLowerCase();
        const textoEstado = columnas[8]?.textContent.toLowerCase();
        const textoCubiculo = columnas[3]?.textContent.toLowerCase();

        const coincidePaciente = !pacienteValor || textoPaciente.includes(pacienteValor);
        const coincideEstado = !estadoValor || textoEstado === estadoValor;
        const coincideCubiculo = !cubiculoValor || textoCubiculo === cubiculoValor;

        const visible = coincidePaciente && coincideEstado && coincideCubiculo;
        fila.style.display = visible ? '' : 'none';
        if (visible) totalVisibles++;
    });

    // Manejo del mensaje si no hay resultados
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
        if (mensajeNoResultados) {
            mensajeNoResultados.remove();
        }
    }
}
