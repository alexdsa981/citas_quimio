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



function llenarFormularioFichaFuncionesVitales(ficha) {
    const idPaciente = ficha.paciente_idPaciente ? ficha.paciente_idPaciente : null;

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

    if (ficha) {
        document.getElementById("presionSistolicaFicha").value = ficha.fv_presionSistolica ?? '';
        document.getElementById("presionDiastolicaFicha").value = ficha.fv_presionDiastolica ?? '';
        document.getElementById("frecuenciaRespiratoriaFicha").value = ficha.fv_frecuenciaRespiratoria ?? '';
        document.getElementById("frecuenciaCardiacaFicha").value = ficha.fv_frecuenciaCardiaca ?? '';
        document.getElementById("temperaturaFicha").value = ficha.fv_temperatura ?? '';
        document.getElementById("saturacionOxigenoFicha").value = ficha.fv_saturacionOxigeno ?? '';
        document.getElementById("pesoFicha").value = ficha.fv_pesoKg ?? '';
        document.getElementById("tallaFicha").value = ficha.fv_tallaCm ?? '';
        document.getElementById("superficieCorporalFicha").value = ficha.fv_superficieCorporal ?? '';
    } else {
        limpiarCampos();

        // Solo intenta obtener la última talla si tenemos ID del paciente
        if (idPaciente) {
            fetch(`/app/funciones-vitales/${idPaciente}/ultima-talla`)
                .then(response => {
                    if (!response.ok) throw new Error("No se pudo obtener la última talla");
                    return response.json();
                })
                .then(talla => {
                    if (talla !== null) {
                        document.getElementById("tallaFicha").value = talla;
                    }
                })
                .catch(error => {
                    console.warn("Error al obtener la última talla del paciente:", error);
                });
        }
    }
}
