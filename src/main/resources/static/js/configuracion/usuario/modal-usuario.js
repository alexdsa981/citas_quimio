
document.getElementById("buscadorUsuario").addEventListener("input", async function () {
    const filtro = this.value.toLowerCase();
    const tabla = document.getElementById("tablaUsuariosBody");
    tabla.innerHTML = "";

    if (filtro.length < 2) return;

    try {
        const response = await fetch("/app/usuarios/lista");
        const usuarios = await response.json();

        let contador = 1;
        usuarios
            .filter(u => u.nombre.toLowerCase().includes(filtro) || u.usuario.toLowerCase().includes(filtro))
            .forEach(usuario => {
                const fila = document.createElement("tr");
                fila.innerHTML = `
                    <td class="text-center">${contador++}</td>
                    <td>${usuario.usuario}</td>
                    <td>${usuario.nombre}</td>
                    <td class="text-center">
                        <button class="btn btn-sm btn-primary" onclick="agregarUsuario('${usuario.usuario}', '${usuario.nombre}')">
                            <i class="bi bi-plus-circle"></i> Agregar
                        </button>
                    </td>
                `;
                tabla.appendChild(fila);
            });

        if (contador === 1) {
            tabla.innerHTML = `<tr><td colspan="4" class="text-center">No se encontraron resultados</td></tr>`;
        }

    } catch (error) {
        console.error("Error al cargar usuarios:", error);
    }
});

function agregarUsuario(usuario, nombre) {
    // Aquí haces lo que necesites con el usuario seleccionado
    console.log("Usuario seleccionado:", usuario, nombre);
    // Por ejemplo, podrías llenar un input oculto o cerrarlo automáticamente:
    // document.getElementById("usuarioSeleccionado").value = usuario;
    // bootstrap.Modal.getInstance(document.getElementById('nuevoUsuarioModal')).hide();
}
