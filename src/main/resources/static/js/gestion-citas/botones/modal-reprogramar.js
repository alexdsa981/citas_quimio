let modalReprogramarInstance = null;

const modalFechaCitaInput = document.getElementById('modalFechaCita');
if (modalFechaCitaInput && typeof modalFechaCitaInput.showPicker === 'function') {
    modalFechaCitaInput.addEventListener('focus', function () {
        this.showPicker();
    });
}
const modalHoraCitaInput = document.getElementById('modalHoraCita');
if (modalHoraCitaInput && typeof modalHoraCitaInput.showPicker === 'function') {
    modalHoraCitaInput.addEventListener('focus', function () {
        this.showPicker();
    });
}


document.getElementById('btn-transferir').addEventListener('click', () => {
    if (!idFichaSeleccionada) {
        Swal.fire({
            icon: 'warning',
            title: 'Ficha no seleccionada',
            text: 'Por favor, selecciona una ficha antes de reprogramar.',
        });
        return;
    }

    fetch(`/app/cita/ficha/${idFichaSeleccionada}`)
        .then(response => response.json())
        .then(data => {
            // Guardar en inputs ocultos
            document.getElementById('hiddenTipoDoc').value = data.tipoDoc;
            document.getElementById('hiddenNumDoc').value = data.numDoc;

            document.getElementById('nombrePacienteReprogramar').value = data.nombrePaciente;


            // Aseguradora
            const aseguradoraInput = document.getElementById('reprogramarAseguradoraCita');
            aseguradoraInput.value = data.aseguradora || '';

            // Checkbox CRP si es RICARDO PALMA
            const checkCRP = document.getElementById('reprogramarCheckCRP');
            checkCRP.checked = (data.aseguradora === 'CLÍNICA RICARDO PALMA');

            // Fecha y hora
            document.getElementById('modalFechaCita').value = data.fechaCita;
            document.getElementById('modalHoraCita').value = data.horaCita;

            // Médico
            document.getElementById('modalMedicoCita').value = data.medicoId;

            // Duración
            const duracion = data.duracion || 0;
            document.getElementById('reprogramarHorasProtocoloCita').value = Math.floor(duracion / 60).toString();
            document.getElementById('reprogramarMinutosProtocoloCita').value = (duracion % 60).toString();

            // Observaciones, medicinas, tratamiento
            document.getElementById('reprogramarTratamientoCita').value = data.tratamiento || '';
            document.getElementById('reprogramarObservacionesCita').value = data.observaciones || '';
            document.getElementById('reprogramarMedicinasCita').value = data.medicina || '';

            // Mostrar modal
            const modalElement = document.getElementById('modalReprogramar');
            modalReprogramarInstance = new bootstrap.Modal(modalElement);
            modalReprogramarInstance.show();
        })
        .catch(error => {
            console.error("Error:", error);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'No se pudo obtener la ficha.',
            });
        });
});


document.getElementById('reprogramarCheckCRP').addEventListener('change', function () {
    const checkbox = this;
    const inputAseg = document.getElementById('reprogramarAseguradoraCita');

    if (!checkbox.checked) {
        // Obtener valores ocultos
        const tipoDocTexto = document.getElementById('hiddenTipoDoc').value;
        const numDoc = document.getElementById('hiddenNumDoc').value;

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
});


function guardarReprogramacion() {
        const horas = parseInt(document.getElementById('reprogramarHorasProtocoloCita').value || 0);
        const minutos = parseInt(document.getElementById('reprogramarMinutosProtocoloCita').value || 0);
        const duracionTotal = horas * 60 + minutos;


        if (duracionTotal === 0) {
            Swal.fire({
                icon: 'warning',
                title: 'Duración inválida',
                text: 'La duración total del protocolo no puede ser 0 minutos.'
            });
            return;
        }


    const dto = {
        idFicha: idFichaSeleccionada,
        fecha: document.getElementById('modalFechaCita').value,
        hora: document.getElementById('modalHoraCita').value,
        idMedico: document.getElementById('modalMedicoCita').value,

        aseguradora: document.getElementById("reprogramarAseguradoraCita").value.trim(),



        duracionMinutos: duracionTotal,

        medicamentos: document.getElementById("reprogramarMedicinasCita").value.trim(),

        observaciones: document.getElementById("reprogramarObservacionesCita").value.trim(),

        tratamiento: document.getElementById("reprogramarTratamientoCita").value.trim(),


    };

    fetch('/app/gestion-citas/boton/editar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dto)
    })
    .then(async response => {
        const data = await response.json();

        if (response.ok && data.success) {
            Swal.fire({
                icon: 'success',
                title: 'Reprogramación exitosa',
                text: data.message,
                timer: 1500,
                showConfirmButton: false
            });

            if (typeof refrescarTablaSegunFiltro === 'function') {
                refrescarTablaSegunFiltro();
            }

            if (modalReprogramarInstance) {
                modalReprogramarInstance.hide();
            }

        } else {
            Swal.fire({
                icon: 'warning',
                title: 'No fue posible reprogramar',
                text: data.message || 'No se pudo completar la reprogramación de la cita.',
            });
        }
    })
    .catch(error => {
        console.error("Error al guardar:", error);
        Swal.fire({
            icon: 'error',
            title: 'Error inesperado',
            text: 'Se produjo un error al intentar reprogramar la cita. Por favor, intente nuevamente.'
        });
    });
}
