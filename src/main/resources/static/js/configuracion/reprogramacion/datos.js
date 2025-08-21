function cargarMotivosReprogramacion() {
    fetch("/app/clasificadores/motivo-reprogramacion/listar")
        .then(response => response.json())
        .then(data => {
            const cuerpoTabla = document.getElementById("tablaMotivoReprogramacion");
            cuerpoTabla.innerHTML = "";

            data.forEach((motivo, index) => {
                const estadoActivo = motivo.isActive !== undefined ? motivo.isActive : motivo.activo; // Soporta ambos nombres
                const estadoTexto = estadoActivo ? "Activo" : "Inactivo";
                const estadoClase = estadoActivo ? "bg-success" : "bg-secondary";
                const nuevaRuta = estadoActivo
                    ? `/app/clasificadores/motivo-reprogramacion/desactivar/${motivo.id}`
                    : `/app/clasificadores/motivo-reprogramacion/activar/${motivo.id}`;

                const fila = document.createElement("tr");
                fila.innerHTML = `
                    <td class="text-center">${index + 1}</td>
                    <td class="text-center">${motivo.nombre}</td>
                    <td class="text-center">
                        <span class="badge ${estadoClase}" style="cursor:pointer;" onclick="cambiarEstadoMotivoReprogramacion('${nuevaRuta}')">
                            ${estadoTexto}
                        </span>
                    </td>
                    <td class="text-center">
                        <button class="btn btn-sm btn-outline-primary" onclick="abrirModalEditarMotivo(${motivo.id}, '${motivo.nombre}')">
                            <i class="bi bi-pencil-square"></i>
                        </button>
                    </td>
                `;
                cuerpoTabla.appendChild(fila);
            });
        })
        .catch(error => {
            console.error("Error al cargar motivos de reprogramación:", error);
        });
}

function cambiarEstadoMotivoReprogramacion(url) {
    fetch(url, { method: 'GET' })
        .then(response => {
            if (response.ok) {
                Swal.fire({
                    icon: 'success',
                    title: 'Éxito',
                    text: 'El estado del registro ha sido actualizado.',
                    timer: 1000,
                    showConfirmButton: false
                });
                cargarMotivosReprogramacion();
            } else {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'No se pudo actualizar el estado de este registro.'
                });
            }
        })
        .catch(error => {
            console.error("Error en la solicitud:", error);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Hubo un problema al comunicarse con el servidor.'
            });
        });
}

document.addEventListener("DOMContentLoaded", cargarMotivosReprogramacion);