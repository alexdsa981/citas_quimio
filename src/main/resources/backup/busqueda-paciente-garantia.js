document.addEventListener("DOMContentLoaded", function () {
    const btnBuscar = document.getElementById("btnBuscarPorPresupuesto");

    btnBuscar.addEventListener("click", async function () {
        const inputPresupuesto = document.getElementById("numeroPresupuestoCita");
        const numeroPresupuesto = inputPresupuesto.value.trim();

        if (!numeroPresupuesto) {
            Swal.fire({
                icon: 'warning',
                title: 'Número requerido',
                text: 'Ingrese un número de presupuesto.',
            });
            return;
        }

        try {
            const response = await fetch(`/app/paciente/desde-garantia/${numeroPresupuesto}`);

            if (!response.ok) {
                Swal.fire({
                    icon: 'error',
                    title: 'Paciente no encontrado',
                    text: 'No se encontró un paciente con ese número de presupuesto.',
                });
                return;
            }

            const data = await response.json();

            // Rellenar campos
            document.getElementById("idPaciente").value = data.idPaciente;
            document.getElementById("apellidoPaternoCita").value = limpiarTexto(data.apellidoPaterno);
            document.getElementById("apellidoMaternoCita").value = limpiarTexto(data.apellidoMaterno);
            document.getElementById("nombresCita").value = limpiarTexto(data.nombres);
            document.getElementById("tipoDocumentoCita").value = data.tipoDocumentoNombre || '';
            document.getElementById("documentoCita").value = data.documento?.trim() || '';
            document.getElementById("fechaNacimientoCita").value = data.fechaNacimiento?.split(" ")[0] || '';
            document.getElementById("edadCita").value = data.edad || '';
            document.getElementById("sexoCita").value = data.sexo || '';
            document.getElementById("celularCita").value = data.celular || '';
            document.getElementById("telefonoCita").value = data.telefono || '';
            document.getElementById("tipoPacienteCita").value = data.tipoPaciente || '';
            document.getElementById("aseguradoraCita").value = data.empresaAseguradora || '';
            document.getElementById("contratanteCita").value = data.empresaEmpleadora || '';
            document.getElementById("btnGuardarCita").disabled = false;
        } catch (error) {
            console.error("Error al obtener datos del paciente:", error);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Ocurrió un error al buscar el paciente. Intente nuevamente.',
            });
        }
    });
});
function limpiarTexto(texto) {
    return texto?.trim().replace(/,+$/, '') || '';
}