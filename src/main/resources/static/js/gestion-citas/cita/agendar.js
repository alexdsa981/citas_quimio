document.getElementById("btnGuardarCita").addEventListener("click", async function () {

    const horas = parseInt(document.getElementById('horasProtocoloCita').value || 0);
    const minutos = parseInt(document.getElementById('minutosProtocoloCita').value || 0);
    const duracionTotal = horas * 60 + minutos;


    const fechaCitaInput = document.getElementById("fechaProgramadaCita");
    const fechaCita = fechaCitaInput ? fechaCitaInput.value : "";

    const idPacienteRaw = document.getElementById("idPaciente").value;
    const idPaciente = parseInt(idPacienteRaw);

    const dto = {
        fechaCita: fechaCita,
        horaProgramada: document.getElementById("horaProgramadaCita").value,
        medicoId: parseInt(document.getElementById("idMedicoCita").value),

        idPaciente: idPaciente,
        tipoDocumento: document.getElementById("tipoDocumentoCita").value.trim(),
        numeroDocumento: document.getElementById("documentoCita").value.trim(),
        apellidoPaterno: document.getElementById("apellidoPaternoCita").value.trim(),
        apellidoMaterno: document.getElementById("apellidoMaternoCita").value.trim(),
        nombres: document.getElementById("nombresCita").value.trim(),
        nombreCompleto: document.getElementById("nombreCompletoCita").value.trim(),
        fechaNacimiento: document.getElementById("fechaNacimientoCita").value.trim(),
        edad: parseInt(document.getElementById("edadCita").value),
        sexo: document.getElementById("sexoCita").value.trim(),
        celular: document.getElementById("celularCita").value.trim() || "No asignado",
        telefono: document.getElementById("telefonoCita").value.trim() || "No asignado",


        aseguradora: document.getElementById("aseguradoraCita").value.trim(),

        duracionMinutos: duracionTotal,

        medicamentos: document.getElementById("medicinasCita").value.trim(),

        observaciones: document.getElementById("observacionesCita").value.trim(),

        tratamiento: document.getElementById("tratamientoCita").value.trim(),



    };

    // Validación
    if (!dto.fechaCita || !dto.horaProgramada || isNaN(dto.medicoId)) {
        return Swal.fire({
            icon: 'warning',
            title: 'Campos obligatorios faltantes',
            text: 'Debes ingresar fecha, hora y médico para la cita.'
        });
    }


    if (duracionTotal <= 0) {
        Swal.fire({
            icon: 'warning',
            title: 'Duración no válida',
            text: 'Por favor, indique una duración válida para el protocolo.'
        });
        return;
    }

    if (isNaN(dto.idPaciente)) {
        return Swal.fire({
            icon: 'warning',
            title: 'Paciente no seleccionado',
            text: 'Debes seleccionar un paciente antes de agendar la cita.'
        });
    }

    try {
        const response = await fetch("/app/gestion-citas/boton/agendar", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(dto)
        });

        const data = await response.json();

        if (response.ok) {
            await Swal.fire({
                icon: "success",
                title: "Cita agendada",
                text: data.message || "Cita agendada correctamente."
            });

            refrescarTablaSegunFiltro();

            const modal = bootstrap.Modal.getInstance(document.getElementById("modalAgendarCita"));

            // Limpiar formulario
            document.getElementById("horaProgramadaCita").value = "07:00";
            document.getElementById("idMedicoCita").selectedIndex = 0;

            [
                "idPaciente", "tipoDocumentoCita", "documentoCita", "apellidoPaternoCita",
                "apellidoMaternoCita", "nombresCita", "nombreCompletoCita", "fechaNacimientoCita",
                "edadCita", "sexoCita", "celularCita", "telefonoCita", "pacienteSeleccionadoCita", "aseguradoraCita"
            ].forEach(id => {
                const el = document.getElementById(id);
                if (el) el.value = "";
            });

            // Selects de duración
            const horas = document.getElementById("horasProtocoloCita");
            const minutos = document.getElementById("minutosProtocoloCita");

            horas.value = "0";
            horas.disabled = true;

            minutos.value = "0";
            minutos.disabled = true;

            // Textareas de detalle quimioterapia
            const medicinas = document.getElementById("medicinasCita");
            const tratamiento = document.getElementById("tratamientoCita");
            const observaciones = document.getElementById("observacionesCita");

            medicinas.value = "";
            medicinas.disabled = true;

            tratamiento.value = "";
            tratamiento.disabled = true;

            observaciones.value = "";
            observaciones.disabled = true;

            const checkCRP = document.getElementById("checkCRP");
            if (checkCRP) checkCRP.checked = false;
            if (checkCRP) checkCRP.disabled = true;

            document.getElementById("btnGuardarCita").disabled = true;
            document.getElementById("btn-busqueda-aseguradoras").disabled = true;

            modal.hide();
        } else {
            await Swal.fire({
                icon: "error",
                title: "Error",
                text: data.message || "No se pudo guardar la cita."
            });
        }
    } catch (error) {
        await Swal.fire({
            icon: "error",
            title: "Error de red",
            text: error.message || "Hubo un problema al conectar con el servidor."
        });
    }
});

let valorOriginalAseguradora = "";

function toggleCRP() {
    const checkbox = document.getElementById("checkCRP");
    const input = document.getElementById("aseguradoraCita");

    if (checkbox.checked) {
        // Guardamos el valor actual antes de cambiarlo
        valorOriginalAseguradora = input.value;
        input.value = "CLÍNICA RICARDO PALMA";
    } else {
        // Restauramos el valor original
        input.value = valorOriginalAseguradora;
    }
}