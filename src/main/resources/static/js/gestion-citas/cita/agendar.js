document.getElementById("btnGuardarCita").addEventListener("click", async function () {
    const fechaCitaInput = document.querySelector("input[type='date']");
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
        fechaNacimiento: document.getElementById("fechaNacimientoCita").value.trim(),
        edad: parseInt(document.getElementById("edadCita").value),
        sexo: document.getElementById("sexoCita").value.trim(),
        celular: document.getElementById("celularCita").value.trim() || "No asignado",
        telefono: document.getElementById("telefonoCita").value.trim() || "No asignado"
    };

    // Validación
    if (!dto.fechaCita || !dto.horaProgramada || isNaN(dto.medicoId)) {
        return Swal.fire({
            icon: 'warning',
            title: 'Campos obligatorios faltantes',
            text: 'Debes ingresar fecha, hora y médico para la cita.'
        });
    }

    if (isNaN(dto.idPaciente)) {
        return Swal.fire({
            icon: 'warning',
            title: 'Paciente no seleccionado',
            text: 'Debes seleccionar un paciente antes de agendar la cita.'
        });
    }

    try {
        const response = await fetch("/app/cita/agendar", {
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
            fechaCitaInput.value = new Date().toISOString().split("T")[0];
            document.getElementById("horaProgramadaCita").value = "07:00";
            document.getElementById("idMedicoCita").selectedIndex = 0;

            [
                "idPaciente", "tipoDocumentoCita", "documentoCita", "apellidoPaternoCita",
                "apellidoMaternoCita", "nombresCita", "fechaNacimientoCita",
                "edadCita", "sexoCita", "celularCita", "telefonoCita", "pacienteSeleccionadoCita"
            ].forEach(id => {
                const el = document.getElementById(id);
                if (el) el.value = "";
            });

            document.getElementById("btnGuardarCita").disabled = true;

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
