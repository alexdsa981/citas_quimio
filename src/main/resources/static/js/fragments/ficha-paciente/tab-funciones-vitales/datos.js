function llenarFormularioFichaFuncionesVitales(data) {
    const funcionesVitales = data.funcionesVitales;

    if (funcionesVitales && funcionesVitales.length > 0) {
        const vitales = funcionesVitales[0]; // tomamos el primero por defecto

        // Asignar valores a los campos del formulario
        document.getElementById("presionSistolicaFicha").value = vitales.presionSistolica ?? '';
        document.getElementById("presionDiastolicaFicha").value = vitales.presionDiastolica ?? '';
        document.getElementById("frecuenciaRespiratoriaFicha").value = vitales.frecuenciaRespiratoria ?? '';
        document.getElementById("frecuenciaCardiacaFicha").value = vitales.frecuenciaCardiaca ?? '';
        document.getElementById("temperaturaFicha").value = vitales.temperatura ?? '';
        document.getElementById("saturacionOxigenoFicha").value = vitales.saturacionOxigeno ?? '';
        document.getElementById("pesoFicha").value = vitales.pesoKg ?? '';
        document.getElementById("tallaFicha").value = vitales.tallaCm ?? '';
        document.getElementById("superficieCorporalFicha").value = vitales.superficieCorporal ?? '';
    }
}
