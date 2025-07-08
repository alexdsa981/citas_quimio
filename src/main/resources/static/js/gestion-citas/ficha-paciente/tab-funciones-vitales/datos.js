document.addEventListener("DOMContentLoaded", function () {
    const pesoInput = document.getElementById("pesoFicha");
    const tallaInput = document.getElementById("tallaFicha");

    pesoInput.addEventListener("input", calcularSuperficieCorporalMosteller);
    tallaInput.addEventListener("input", calcularSuperficieCorporalMosteller);
});
function calcularSuperficieCorporalMosteller() {
    const pesoInput = document.getElementById("pesoFicha");
    const tallaInput = document.getElementById("tallaFicha");
    const superficieCorporalInput = document.getElementById("superficieCorporalFicha");

    const peso = parseFloat(pesoInput.value.trim());
    const talla = parseFloat(tallaInput.value.trim());

    if (!isNaN(peso) && !isNaN(talla) && peso > 0 && talla > 0) {
        const sc = Math.sqrt((talla * peso) / 3600).toFixed(2);
        superficieCorporalInput.value = sc;
    } else {
        superficieCorporalInput.value = "";
    }
}




function llenarFormularioFichaFuncionesVitales(data) {
    const funcionesVitales = data.funcionesVitales;

    // Función auxiliar para limpiar todos los campos
    const limpiarCampos = () => {
        document.getElementById("presionSistolicaFicha").value = '';
        document.getElementById("presionDiastolicaFicha").value = '';
        document.getElementById("frecuenciaRespiratoriaFicha").value = '';
        document.getElementById("frecuenciaCardiacaFicha").value = '';
        document.getElementById("temperaturaFicha").value = '';
        document.getElementById("saturacionOxigenoFicha").value = '';
        document.getElementById("pesoFicha").value = '';
        document.getElementById("tallaFicha").value = '';
        document.getElementById("superficieCorporalFicha").value = '';
    };

    if (funcionesVitales) {
        document.getElementById("presionSistolicaFicha").value = funcionesVitales.presionSistolica ?? '';
        document.getElementById("presionDiastolicaFicha").value = funcionesVitales.presionDiastolica ?? '';
        document.getElementById("frecuenciaRespiratoriaFicha").value = funcionesVitales.frecuenciaRespiratoria ?? '';
        document.getElementById("frecuenciaCardiacaFicha").value = funcionesVitales.frecuenciaCardiaca ?? '';
        document.getElementById("temperaturaFicha").value = funcionesVitales.temperatura ?? '';
        document.getElementById("saturacionOxigenoFicha").value = funcionesVitales.saturacionOxigeno ?? '';
        document.getElementById("pesoFicha").value = funcionesVitales.pesoKg ?? '';
        document.getElementById("tallaFicha").value = funcionesVitales.tallaCm ?? '';
        document.getElementById("superficieCorporalFicha").value = funcionesVitales.superficieCorporal ?? '';
    } else {
        limpiarCampos();
    }
}
