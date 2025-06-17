async function cargarMedicos() {
    const cuerpoTabla = document.getElementById('tablaMedicosBody');

    try {
        const response = await fetch('/app/personal/medicos');
        if (!response.ok) throw new Error('Error al obtener médicos');

        const medicos = await response.json();
        cuerpoTabla.innerHTML = '';

        medicos.forEach((medico, index) => {
            const estadoHTML = medico.isActive
                ? `<span class="badge bg-success estado-toggle" data-id="${medico.idPersona}" style="cursor:pointer;">Activo</span>`
                : `<span class="badge bg-secondary">Inactivo</span>`;

            const fila = `
                <tr>
                    <td class="text-center">${index + 1}</td>
                    <td>${medico.nombre}</td>
                    <td>${medico.apellidoP}</td>
                    <td>${medico.apellidoM}</td>
                    <td>${medico.nombreCompleto}</td>
                    <td class="text-center">${estadoHTML}</td>
                </tr>
            `;
            cuerpoTabla.insertAdjacentHTML('beforeend', fila);
        });

        // Agregar eventos a spans activos
        cuerpoTabla.querySelectorAll('.estado-toggle').forEach(span => {
            span.addEventListener('click', async function () {
                const id = this.getAttribute('data-id');

                const result = await Swal.fire({
                    title: '¿Desactivar médico?',
                    text: "Esta acción desactivará al médico en el sistema.",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonText: 'Sí, desactivar',
                    cancelButtonText: 'Cancelar'
                });

                if (result.isConfirmed) {
                    try {
                        const res = await fetch(`/app/personal/medico/desactivar/${id}`, {
                            method: 'POST'
                        });

                        if (!res.ok) throw new Error('Error al desactivar');

                        await Swal.fire('Desactivado', 'El médico ha sido desactivado.', 'success');
                        cargarMedicos(); // Recargar tabla
                    } catch (error) {
                        console.error(error);
                        Swal.fire('Error', 'No se pudo desactivar al médico.', 'error');
                    }
                }
            });
        });

    } catch (error) {
        console.error(error);
        cuerpoTabla.innerHTML = `
            <tr><td colspan="6" class="text-center text-danger">Error al cargar médicos</td></tr>
        `;
    }
}

document.addEventListener('DOMContentLoaded', cargarMedicos);
