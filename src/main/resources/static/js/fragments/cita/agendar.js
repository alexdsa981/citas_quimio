document.getElementById("btnGuardarCita").addEventListener("click", async function () {
    const dto = {
        // Datos de la cita
        fechaCita: document.querySelector("input[type='date']").value,
        horaProgramada: document.getElementById("horaProgramadaCita").value,
        medicoId: parseInt(document.getElementById("idMedicoCita").value),
        tipoPaciente: document.getElementById("tipoPacienteCita").value.trim(),
        aseguradora: document.getElementById("aseguradoraCita").value.trim(),
        contratante: document.getElementById("contratanteCita").value.trim(),
        numeroPresupuesto: parseInt(document.getElementById("numeroPresupuestoCita").value),

        // Datos del paciente
        idPaciente: parseInt(document.getElementById("idPaciente").value),
        tipoDocumento: document.getElementById("tipoDocumentoCita").value.trim(),
        numeroDocumento: document.getElementById("documentoCita").value.trim(),

        apellidoPaterno: document.getElementById("apellidoPaternoCita").value.trim(),
        apellidoMaterno: document.getElementById("apellidoMaternoCita").value.trim(),
        nombres: document.getElementById("nombresCita").value.trim(),

        fechaNacimiento: document.getElementById("fechaNacimientoCita").value.trim(),
        edad: parseInt(document.getElementById("edadCita").value),
        sexo: document.getElementById("sexoCita").value.trim(),

        celular: document.getElementById("celularCita").value.trim() || "No asignado",
        telefono: document.getElementById("telefonoCita").value.trim() || "No asignado",

    };

    // Validación básica de campos obligatorios
    if (!dto.fechaCita || !dto.horaProgramada || !dto.medicoId || !dto.numeroPresupuesto ||
        !dto.idPaciente || !dto.numeroDocumento || !dto.apellidoPaterno || !dto.nombres || !dto.sexo) {
        return Swal.fire({
            icon: 'warning',
            title: 'Campos obligatorios faltantes',
            text: 'Por favor, completa todos los campos requeridos antes de continuar.'
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
            refrescarTablaSegunFiltro()
            const modal = bootstrap.Modal.getInstance(document.getElementById("modalAgendarCita"));

            // LIMPIAR FORMULARIO
            document.querySelector("input[type='date']").value = new Date().toISOString().split("T")[0]; // Hoy
            document.getElementById("horaProgramadaCita").value = "07:00";
            document.getElementById("idMedicoCita").selectedIndex = 0; // Primer médico
            [
                "tipoPacienteCita", "aseguradoraCita", "contratanteCita", "numeroPresupuestoCita",
                "idPaciente", "tipoDocumentoCita", "documentoCita", "apellidoPaternoCita",
                "apellidoMaternoCita", "nombresCita", "fechaNacimientoCita",
                "edadCita", "sexoCita", "celularCita", "telefonoCita"
            ].forEach(id => {
                const el = document.getElementById(id);
                if (el) el.value = "";
            });


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