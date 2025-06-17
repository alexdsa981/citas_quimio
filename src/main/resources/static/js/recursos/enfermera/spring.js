const buscador = document.getElementById('buscadorEmpleado');
const cuerpoTabla = document.getElementById('tablaEmpleadosBody');

buscador.addEventListener('input', async function () {
    const texto = buscador.value.trim();

    if (texto.length < 2) {
        cuerpoTabla.innerHTML = '';
        return;
    }

    try {
        const response = await fetch(`/app/empleado/buscar/${texto}`);
        if (!response.ok) throw new Error('Error al buscar empleados');

        const empleados = await response.json();
        cuerpoTabla.innerHTML = '';

        empleados.forEach((empleado, index) => {
            const fila = `
                <tr>
                    <td class="text-center">${index + 1}</td>
                    <td>${empleado.nombres}</td>
                    <td>${empleado.apellidoPaterno}</td>
                    <td>${empleado.apellidoMaterno}</td>
                    <td>${empleado.nombreCompleto}</td>
                    <td class="text-center">
                        <button class="btn btn-sm btn-success btn-agregar" title="Agregar"
                            data-id="${empleado.persona}"
                            data-nombres="${empleado.nombres}"
                            data-apellidoP="${empleado.apellidoPaterno}"
                            data-apellidoM="${empleado.apellidoMaterno}"
                            data-nombre-completo="${empleado.nombreCompleto}">
                            <i class="bi bi-plus-circle"></i>
                        </button>
                    </td>
                </tr>`;
            cuerpoTabla.insertAdjacentHTML('beforeend', fila);
        });

        cuerpoTabla.querySelectorAll('.btn-agregar').forEach(btn => {
            btn.addEventListener('click', async function () {
                const dto = {
                    id: btn.getAttribute('data-id'),
                    nombre: btn.getAttribute('data-nombres'),
                    apellidoP: btn.getAttribute('data-apellidoP'),
                    apellidoM: btn.getAttribute('data-apellidoM'),
                    nombreCompleto: btn.getAttribute('data-nombre-completo')
                };

                try {
                    const response = await fetch('/app/personal/enfermera/guardar', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(dto)
                    });

                    if (!response.ok) throw new Error('Error al guardar enfermera');

                    await cargarEnfermeras();

                    Swal.fire({
                        icon: 'success',
                        title: 'Enfermera agregada',
                        text: 'La enfermera fue agregado o actualizado correctamente.',
                        timer: 2000,
                        showConfirmButton: false
                    });

                } catch (error) {
                    console.error(error);
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: 'Hubo un problema al agregar la enfermera.',
                    });
                }
            });
        });

    } catch (error) {
        console.error(error);
        cuerpoTabla.innerHTML = `<tr><td colspan="6" class="text-center text-danger">Error al cargar empleados</td></tr>`;
    }
});
