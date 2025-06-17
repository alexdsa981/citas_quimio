function cargarCubiculos() {
    fetch("/app/clasificadores/cubiculo/listar")
        .then(response => response.json())
        .then(data => {
            const cuerpoTabla = document.getElementById("tablaCubiculos");
            cuerpoTabla.innerHTML = "";

            data.forEach((cubiculo, index) => {
                const fila = document.createElement("tr");

                const estadoTexto = cubiculo.isActive ? "Activo" : "Inactivo";
                const estadoClase = cubiculo.isActive ? "bg-success" : "bg-secondary";
                const nuevaRuta = cubiculo.isActive
                    ? `/app/clasificadores/cubiculo/desactivar/${cubiculo.id}`
                    : `/app/clasificadores/cubiculo/activar/${cubiculo.id}`;

                fila.innerHTML = `
                    <td class="text-center">${index + 1}</td>
                    <td class="text-center">${cubiculo.codigo}</td>
                    <td class="text-center">
                        <span class="badge ${estadoClase}" style="cursor:pointer;" onclick="cambiarEstadoCubiculo('${nuevaRuta}')">
                            ${estadoTexto}
                        </span>
                    </td>
                    <td class="text-center">
                        <button class="btn btn-sm btn-outline-primary" onclick="abrirModalEditarCubiculo(${cubiculo.id}, '${cubiculo.codigo}')">
                            <i class="bi bi-pencil-square"></i>
                        </button>
                    </td>
                `;


                cuerpoTabla.appendChild(fila);
            });
        })
        .catch(error => {
            console.error("Error al cargar cubículos:", error);
        });
}

function cambiarEstadoCubiculo(url) {
    fetch(url, { method: 'GET' })
        .then(response => {
            if (response.ok) {
                Swal.fire({
                    icon: 'success',
                    title: 'Éxito',
                    text: 'El estado del cubículo ha sido actualizado.',
                    timer: 1000,
                    showConfirmButton: false
                });
                cargarCubiculos();
            } else {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'No se pudo actualizar el estado del cubículo.'
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


document.addEventListener("DOMContentLoaded", cargarCubiculos);
