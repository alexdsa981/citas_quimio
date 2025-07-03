let timeoutId = null;

document.getElementById("inputBuscarNombrePaciente").addEventListener("input", function () {
    clearTimeout(timeoutId);
    const valor = this.value.trim();
    if (valor.length < 3) {
        document.getElementById("resultadoPacientes").innerHTML = `
            <div class="text-muted">
                <i class="bi bi-info-circle"></i> Ingrese al menos 3 letras.
            </div>`;
        return;
    }

timeoutId = setTimeout(() => {
    fetch(`/app/paciente/buscar?nombre=${encodeURIComponent(valor)}`)
        .then(async res => {
            if (!res.ok) {
                return [];
            }
            try {
                return await res.json();
            } catch (e) {
                console.warn("Respuesta sin JSON válido");
                return [];
            }
        })
        .then(data => mostrarResultadosPacientes(data))
        .catch(err => {
            console.error("Error al buscar pacientes:", err);
            document.getElementById("resultadoPacientes").innerHTML = `
                <div class="text-danger">
                    <i class="bi bi-exclamation-triangle-fill"></i> Error al procesar la búsqueda.
                </div>`;
        });
}, 400);

});

function buscarPacientePorDocumento() {
    const tipo = document.getElementById("selectTipoDocumento").value;
    const numero = document.getElementById("inputNumeroDocumento").value.trim();
    if (!tipo || !numero) return;

    fetch(`/app/paciente/buscar-por-documento?tipoDocumento=${tipo}&documento=${encodeURIComponent(numero)}`)
        .then(res => {
            if (!res.ok) throw new Error("Paciente no encontrado");
            return res.json();
        })
        .then(data => mostrarResultadosPacientes([data]))
        .catch(() => {
            Swal.fire({
                icon: 'warning',
                title: 'Sin resultados',
                text: 'No se encontró ningún paciente con ese documento.',
                confirmButtonText: 'Entendido',
                backdrop: false
            });
        });
}

function mostrarResultadosPacientes(data) {
    const contenedor = document.getElementById("resultadoPacientes");
    contenedor.innerHTML = "";

    if (!data || data.length === 0) {
        contenedor.innerHTML = `
            <div class="text-danger">
                <i class="bi bi-exclamation-triangle-fill"></i> No se encontraron pacientes.
            </div>`;
        return;
    }

    data.forEach(paciente => {
        const item = document.createElement("button");
        item.className = "list-group-item list-group-item-action";
        item.innerHTML = `<strong>${paciente.nombreCompleto}</strong> — ${paciente.documento}`;
        item.onclick = () => seleccionarPacienteDesdeModal(paciente);
        contenedor.appendChild(item);
    });
}

function seleccionarPacienteDesdeModal(paciente) {
    document.getElementById("pacienteSeleccionadoCita").value = paciente.nombreCompleto;
    document.getElementById("idPaciente").value = paciente.idPersona;
    document.getElementById("tipoDocumentoCita").value = paciente.tipoDocumentoNombre;
    document.getElementById("documentoCita").value = paciente.documento;
    document.getElementById("apellidoPaternoCita").value = paciente.apellidoPaterno;
    document.getElementById("apellidoMaternoCita").value = paciente.apellidoMaterno;
    document.getElementById("nombresCita").value = paciente.nombres;
    document.getElementById("nombreCompletoCita").value = paciente.nombreCompleto;
    document.getElementById("fechaNacimientoCita").value = paciente.fechaNacimiento;
    document.getElementById("edadCita").value = paciente.edad;
    document.getElementById("sexoCita").value = paciente.sexo;
    document.getElementById("celularCita").value = paciente.celular;
    document.getElementById("telefonoCita").value = paciente.telefono;
    document.getElementById("aseguradoraCita").value = paciente.aseguradora;

    // ✅ Habilita el botón de agendar
    document.getElementById("btnGuardarCita").disabled = false;
    document.getElementById("checkCRP").disabled = false;
    document.getElementById("horasProtocoloCita").disabled = false;
    document.getElementById("minutosProtocoloCita").disabled = false;
    document.getElementById("medicinasCita").disabled = false;
    document.getElementById("tratamientoCita").disabled = false;
    document.getElementById("observacionesCita").disabled = false;

    // Cierra solo el modal de búsqueda
    const modal = bootstrap.Modal.getInstance(document.getElementById("modalBuscarPaciente"));
    modal.hide();
}
