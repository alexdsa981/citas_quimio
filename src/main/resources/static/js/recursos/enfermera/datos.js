async function cargarEnfermeras() {
    const cuerpoTabla = document.getElementById('tablaEnfermerasBody');

    try {
        const response = await fetch('/app/personal/enfermeras');
        if (!response.ok) throw new Error('Error al obtener enfermeras');

        const enfermeras = await response.json();
        cuerpoTabla.innerHTML = ''; // Limpiar tabla

        enfermeras.forEach((enfermera, index) => {
            const estadoHTML = enfermera.isActive
                ? `<span class="badge bg-success estado-toggle" data-id="${enfermera.idPersona}" style="cursor:pointer;">Activo</span>`
                : `<span class="badge bg-secondary">Inactivo</span>`;

            const fila = `
                <tr>
                    <td class="text-center">${index + 1}</td>
                    <td>${enfermera.nombre}</td>
                    <td>${enfermera.apellidoP}</td>
                    <td>${enfermera.apellidoM}</td>
                    <td>${enfermera.nombreCompleto}</td>
                    <td class="text-center">${estadoHTML}</td>
                </tr>
            `;
            cuerpoTabla.insertAdjacentHTML('beforeend', fila);
        });

        // Delegar evento para desactivación
        cuerpoTabla.querySelectorAll('.estado-toggle').forEach(span => {
            span.addEventListener('click', async function () {
                const id = this.getAttribute('data-id');

                const result = await Swal.fire({
                    title: '¿Desactivar enfermera?',
                    text: "Esta acción desactivará a la enfermera en el sistema.",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonText: 'Sí, desactivar',
                    cancelButtonText: 'Cancelar'
                });

                if (result.isConfirmed) {
                    try {
                        const res = await fetch(`/app/personal/enfermera/desactivar/${id}`, {
                            method: 'POST'
                        });

                        if (!res.ok) throw new Error('Error al desactivar');

                        await Swal.fire('Desactivada', 'La enfermera ha sido desactivada.', 'success');
                        cargarEnfermeras();
                    } catch (error) {
                        console.error(error);
                        Swal.fire('Error', 'No se pudo desactivar a la enfermera.', 'error');
                    }
                }
            });
        });

    } catch (error) {
        console.error(error);
        cuerpoTabla.innerHTML = `
            <tr><td colspan="6" class="text-center text-danger">Error al cargar enfermeras</td></tr>
        `;
    }
}

document.addEventListener('DOMContentLoaded', cargarEnfermeras);
