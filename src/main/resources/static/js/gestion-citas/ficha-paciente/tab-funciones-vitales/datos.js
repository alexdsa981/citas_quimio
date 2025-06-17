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

    if (funcionesVitales && funcionesVitales.length > 0) {
        const vitales = funcionesVitales[0];

        document.getElementById("presionSistolicaFicha").value = vitales.presionSistolica ?? '';
        document.getElementById("presionDiastolicaFicha").value = vitales.presionDiastolica ?? '';
        document.getElementById("frecuenciaRespiratoriaFicha").value = vitales.frecuenciaRespiratoria ?? '';
        document.getElementById("frecuenciaCardiacaFicha").value = vitales.frecuenciaCardiaca ?? '';
        document.getElementById("temperaturaFicha").value = vitales.temperatura ?? '';
        document.getElementById("saturacionOxigenoFicha").value = vitales.saturacionOxigeno ?? '';
        document.getElementById("pesoFicha").value = vitales.pesoKg ?? '';
        document.getElementById("tallaFicha").value = vitales.tallaCm ?? '';
        document.getElementById("superficieCorporalFicha").value = vitales.superficieCorporal ?? '';
    } else {
        limpiarCampos();
    }
}
