const modalDuplicar = new bootstrap.Modal(document.getElementById('modalDuplicarSesion'));

const fechaDuplicadaInput = document.getElementById('fechaDuplicada');
if (fechaDuplicadaInput && typeof fechaDuplicadaInput.showPicker === 'function') {
    fechaDuplicadaInput.addEventListener('focus', function () {
        this.showPicker();
    });
}
const horaDuplicadaInput = document.getElementById('horaDuplicada');
if (horaDuplicadaInput && typeof horaDuplicadaInput.showPicker === 'function') {
    horaDuplicadaInput.addEventListener('focus', function () {
        this.showPicker();
    });
}


document.getElementById('btn-duplicar').addEventListener('click', () => {
    if (!idFichaSeleccionada) {
        Swal.fire("Ficha no seleccionada", "Por favor, selecciona una ficha antes de duplicar.", "warning");
        return;
    }

    fetch(`/app/cita/ficha/${idFichaSeleccionada}`)
        .then(response => {
            if (!response.ok) throw new Error("Error al obtener datos");
            return response.json();
        })
        .then(data => {
            // Llenar campos visibles
            document.getElementById('nombrePacienteDuplicar').value = data.nombrePaciente;
            document.getElementById('fechaDuplicada').value = data.fechaCita;
            document.getElementById('horaDuplicada').value = data.horaCita;
            document.getElementById('duplicadaMedicoCita').value = data.medicoId;

            // Llenar campos ocultos/auxiliares
            document.getElementById('duplicadaTipoDoc').value = data.tipoDoc;
            document.getElementById('duplicadaNumDoc').value = data.numDoc;

            document.getElementById('duplicadaAseguradora').value = data.aseguradora;
            const checkCRPdup = document.getElementById('duplicadaCheckCRP');
            checkCRPdup.checked = (data.aseguradora === 'CLÍNICA RICARDO PALMA');


            document.getElementById('duplicadaTratamiento').value = data.tratamiento;
            document.getElementById('duplicadaObservaciones').value = data.observaciones;
            document.getElementById('duplicadaMedicinas').value = data.medicina;

            // Duración separada
            const duracion = data.duracion || 0;
            document.getElementById('duplicadaHorasProtocoloCita').value = Math.floor(duracion / 60).toString();
            document.getElementById('duplicadaMinutosProtocoloCita').value = (duracion % 60).toString();

            // Mostrar modal
            modalDuplicar.show();
        })
        .catch(error => {
            console.error("Error al cargar datos de la ficha:", error);
            Swal.fire("Error", "Ocurrió un error al cargar la información.", "error");
        });
});


function toggleCRPduplicada() {
    const checkbox = document.getElementById('duplicadaCheckCRP');
    const inputAseg = document.getElementById('duplicadaAseguradora');

    if (!checkbox.checked) {
        // Obtener valores ocultos
        const tipoDocTexto = document.getElementById('duplicadaTipoDoc').value;
        const numDoc = document.getElementById('duplicadaNumDoc').value;

        // Mapa de texto -> código
        const tipoDocMap = {
            "D.N.I./Cédula/L.E.": "D",
            "Carnet Extranjería": "X",
            "Pasaporte": "P",
            "RUC / NIT": "R",
            "Otros": "O"
        };

        const tipoDocCodigo = tipoDocMap[tipoDocTexto];

        if (!tipoDocCodigo || !numDoc) {
            console.warn("Tipo de documento o número inválido");
            return;
        }

        fetch(`/app/paciente/buscar-por-documento?tipoDocumento=${encodeURIComponent(tipoDocCodigo)}&documento=${encodeURIComponent(numDoc)}`)
            .then(response => {
                if (!response.ok) throw new Error("No encontrado");
                return response.json();
            })
            .then(data => {
                inputAseg.value = data.aseguradora || '';
            })
            .catch(error => {
                console.error("Error al obtener aseguradora original:", error);
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'No se pudo recuperar la aseguradora original.',
                });
            });
    } else {
        // Si el checkbox se marca, colocamos "CLÍNICA RICARDO PALMA"
        inputAseg.value = "CLÍNICA RICARDO PALMA";
    }
}



document.getElementById('btn-confirmar-duplicar').addEventListener('click', () => {
    const fecha = document.getElementById('fechaDuplicada').value;
    const hora = document.getElementById('horaDuplicada').value;
    const idMedico = document.getElementById('duplicadaMedicoCita').value;
    const horas = parseInt(document.getElementById('duplicadaHorasProtocoloCita').value) || 0;
    const minutos = parseInt(document.getElementById('duplicadaMinutosProtocoloCita').value) || 0;
    const duracionTotal = (horas * 60) + minutos;
    const aseguradora = document.getElementById('duplicadaAseguradora').value;



    const medicamentos = document.getElementById('duplicadaMedicinas').value;
    const observaciones = document.getElementById('duplicadaObservaciones').value;
    const tratamiento = document.getElementById('duplicadaTratamiento').value;

    if (!fecha || !hora || !idMedico) {
        Swal.fire({
            icon: 'warning',
            title: 'Campos incompletos',
            text: 'Por favor, complete todos los campos requeridos.'
        });
        return;
    }
    if (duracionTotal === 0) {
        Swal.fire({
            icon: 'warning',
            title: 'Duración inválida',
            text: 'La duración total del protocolo no puede ser 0 minutos.'
        });
        return;
    }

    const dto = {
        idFichaPaciente: idFichaSeleccionada,
        fecha: fecha,
        horaProgramada: hora,
        idMedico: idMedico,
        duracionMinutos: duracionTotal,
        aseguradora: aseguradora,
        medicamentos: medicamentos,
        observaciones: observaciones,
        tratamiento: tratamiento
    };

    fetch('/app/gestion-citas/boton/duplicar', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(dto)
    })
    .then(res => res.json())
    .then(data => {
        if (data.success) {
            Swal.fire({
                icon: 'success',
                title: 'Cita duplicada',
                text: data.message,
                timer: 1500,
                showConfirmButton: false
            });

            if (typeof refrescarTablaSegunFiltro === 'function') {
                refrescarTablaSegunFiltro();
            }

            modalDuplicar.hide();
        } else {
            Swal.fire({
                icon: 'error',
                title: 'No se pudo duplicar la cita',
                text: data.message
            });
        }
    })
    .catch(error => {
        console.error("Error en la duplicación:", error);
        Swal.fire({
            icon: 'error',
            title: 'Error del servidor',
            text: 'Ocurrió un error inesperado. Por favor, intente nuevamente.'
        });
    });
});
