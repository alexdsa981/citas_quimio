
function habilitarEdicionSignosVitalesFicha() {
    document.getElementById('datosModificablesFuncionesVitalesFicha').classList.remove('form-disabled');
    document.getElementById('btnGuardarSignosVitalesFicha').disabled = false;
    document.getElementById('btnModificarSignosVitalesFicha').disabled = true;

}

function deshabilitarEdicionSignosVitalesFicha() {
    document.getElementById('datosModificablesFuncionesVitalesFicha').classList.add('form-disabled');
    document.getElementById('btnGuardarSignosVitalesFicha').disabled = true;
    document.getElementById('btnModificarSignosVitalesFicha').disabled = false;
}



function guardarSignosVitalesFicha() {
    const presionSistolica = document.getElementById("presionSistolicaFicha").value.trim();
    const presionDiastolica = document.getElementById("presionDiastolicaFicha").value.trim();
    const frecuenciaRespiratoria = document.getElementById("frecuenciaRespiratoriaFicha").value.trim();
    const frecuenciaCardiaca = document.getElementById("frecuenciaCardiacaFicha").value.trim();
    const temperatura = document.getElementById("temperaturaFicha").value.trim();
    const saturacionOxigeno = document.getElementById("saturacionOxigenoFicha").value.trim();
    const peso = document.getElementById("pesoFicha").value.trim();
    const talla = document.getElementById("tallaFicha").value.trim();
    const superficieCorporalInput = document.getElementById("superficieCorporalFicha");

    // Validaciones obligatorias
    if (
        !presionSistolica || !presionDiastolica ||
        !frecuenciaRespiratoria || !frecuenciaCardiaca ||
        !temperatura || !saturacionOxigeno
    ) {
        Swal.fire({
            icon: 'warning',
            title: 'Campos obligatorios',
            text: 'Por favor complete todos los campos requeridos antes de guardar.',
        });
        return;
    }

    // Cálculo de superficie corporal (Mosteller)
    let superficieCorporal = null;
    const pesoNum = parseFloat(peso);
    const tallaNum = parseFloat(talla);

    if (!isNaN(pesoNum) && !isNaN(tallaNum) && peso && talla) {
        superficieCorporal = Math.sqrt((tallaNum * pesoNum) / 3600).toFixed(2);
        superficieCorporalInput.value = superficieCorporal;
    } else {
        superficieCorporalInput.value = "";
    }

    const data = {
        presionSistolica,
        presionDiastolica,
        frecuenciaRespiratoria,
        frecuenciaCardiaca,
        temperatura,
        saturacionOxigeno,
        peso: peso || null,
        talla: talla || null,
        superficieCorporal,
        idFicha: idFichaSeleccionada
    };

    fetch("/app/funciones-vitales/guardar", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error al guardar");
        }
        return response.json();
    })
    .then(result => {
        Swal.fire({
            icon: 'success',
            title: 'Guardado exitoso',
            text: result.mensaje || "Signos vitales guardados correctamente.",
            timer: 2500,
            showConfirmButton: false
        });
            deshabilitarEdicionSignosVitalesFicha();
    })
    .catch(error => {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Ocurrió un error al guardar los signos vitales.',
        });
        console.error("Error al guardar signos vitales:", error);
    });
}
