document.addEventListener('DOMContentLoaded', () => {
    const tipoDocSelect = document.getElementById('tipoDocIdentidadCita');
    const numeroDocInput = document.getElementById('numeroDocumentoCita');
    const btnBuscar = document.getElementById('btnBuscarPaciente');

document.getElementById('btnModificarPacienteModalCita').addEventListener('click', () => {
    desbloquearCamposPaciente();
});

    function bloquearCamposPaciente() {
        const campos = document.querySelectorAll(".bloqueable");
        campos.forEach(campo => {
            campo.readOnly = true;
            campo.disabled = true;
            campo.style.backgroundColor = "#e9ecef";
            campo.style.color = "#6c757d";
            campo.style.cursor = "not-allowed";
            campo.setAttribute("tabindex", "-1");
            campo.onmousedown = (e) => e.preventDefault();
        });
    }

    function desbloquearCamposPaciente() {
        const campos = document.querySelectorAll(".bloqueable");
        campos.forEach(campo => {
            campo.readOnly = false;
            campo.disabled = false;
            campo.style.backgroundColor = "#fff";
            campo.style.color = "#000";
            campo.style.cursor = "auto";
            campo.removeAttribute("tabindex");
            campo.onmousedown = null;
        });
    }


    function toggleNumeroDocumentoInput() {
        if (tipoDocSelect.value === "") {
            numeroDocInput.readOnly = true;
            numeroDocInput.value = "";
            numeroDocInput.style.backgroundColor = "#e9ecef";
            numeroDocInput.style.color = "#6c757d";
            numeroDocInput.style.cursor = "not-allowed";
            numeroDocInput.setAttribute("tabindex", "-1");
            numeroDocInput.onmousedown = (e) => e.preventDefault();
        } else {
            numeroDocInput.readOnly = false;
            numeroDocInput.style.backgroundColor = "#fff";
            numeroDocInput.style.color = "#000";
            numeroDocInput.style.cursor = "auto";
            numeroDocInput.removeAttribute("tabindex");
            numeroDocInput.onmousedown = null;
        }
    }

    // Bloqueo inicial
    bloquearCamposPaciente();
    toggleNumeroDocumentoInput();
    tipoDocSelect.addEventListener('change', toggleNumeroDocumentoInput);

    // Evento del botón de búsqueda
    btnBuscar.addEventListener('click', () => {
        const idTipoDocIdentidad = tipoDocSelect.value;
        const numeroDocumento = numeroDocInput.value.trim();

        if (!idTipoDocIdentidad || numeroDocumento.length < 4) {
            Swal.fire({
                icon: 'warning',
                title: 'Datos incompletos',
                text: 'Seleccione tipo de documento y escriba un número válido.',
            });
            return;
        }

        fetch('/app/paciente/buscar-por-documento', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                idTipoDocIdentidad,
                numeroDocumento
            })
        })
        .then(res => res.json())
        .then(data => {
            if (!data.message) {
                Swal.fire({
                    icon: 'success',
                    title: 'Paciente encontrado',
                    text: `${data.nombre} ${data.apellidoP}`,
                    timer: 1500,
                    showConfirmButton: false
                });
                llenarDatosPaciente(data);

                //Bloquear select, input y botón de búsqueda
                bloquearCamposPaciente();


                tipoDocSelect.disabled = true;
                //INPUT NUMERO DOCUMENTO
                numeroDocInput.readOnly = true;
                numeroDocInput.style.backgroundColor = "#e9ecef";
                numeroDocInput.style.color = "#6c757d";
                numeroDocInput.style.cursor = "not-allowed";
                numeroDocInput.setAttribute("tabindex", "-1");
                numeroDocInput.onmousedown = (e) => e.preventDefault();

                btnBuscar.disabled = true;

                document.getElementById('btnLimpiarModalCita').disabled = false;
                document.getElementById('btnModificarPacienteModalCita').disabled = false;
                document.getElementById('btnGuardarCita').disabled = false;
            } else {
                Swal.fire({
                    icon: 'info',
                    title: 'No se encontró el paciente',
                    text: 'Puede ingresar los datos manualmente.',
                    timer: 1500
                });

                tipoDocSelect.disabled = true;

                //INPUT NUMERO DOCUMENTO
                numeroDocInput.readOnly = true;
                numeroDocInput.style.backgroundColor = "#e9ecef";
                numeroDocInput.style.color = "#6c757d";
                numeroDocInput.style.cursor = "not-allowed";
                numeroDocInput.setAttribute("tabindex", "-1");
                numeroDocInput.onmousedown = (e) => e.preventDefault();

                document.getElementById('btnLimpiarModalCita').disabled = false;
                document.getElementById('btnModificarPacienteModalCita').disabled = true;
                document.getElementById('btnGuardarCita').disabled = false;
                desbloquearCamposPaciente();
            }
        })
        .catch(error => {
            console.error("Error en la búsqueda:", error);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'No se pudo conectar con el servidor.',
            });
        });

    });
    document.getElementById('btnLimpiarModalCita').addEventListener('click', () => {
        // Limpiar todos los inputs dentro de #modalAgendarCita
        const inputs = document.querySelectorAll('#contenedorPacienteModalCita input');

        inputs.forEach(input => {
            if (input.type !== 'hidden' && input.type !== 'button' && input.type !== 'checkbox') {
                input.value = '';
            }
        });

        // Bloquear campos de paciente nuevamente
        bloquearCamposPaciente();

        // Desbloquear tipo documento y número documento
        tipoDocSelect.disabled = false;
        numeroDocInput.readOnly = false;
        btnBuscar.disabled = false;

        // Restaurar estilo del número documento
        numeroDocInput.style.backgroundColor = "#fff";
        numeroDocInput.style.color = "#000";
        numeroDocInput.style.cursor = "auto";
        numeroDocInput.removeAttribute("tabindex");
        numeroDocInput.onmousedown = null;

        // Desactivar nuevamente el botón "Limpiar", "Guardar" y "Modificar"
        document.getElementById('btnGuardarCita').disabled = true;
        document.getElementById('btnModificarPacienteModalCita').disabled = true;
        document.getElementById('btnLimpiarModalCita').disabled = true;
    });
});

function llenarDatosPaciente(data) {
    document.getElementById('apellidoPaternoCita').value = data.apellidoP || '';
    document.getElementById('apellidoMaternoCita').value = data.apellidoM || '';
    document.getElementById('nombresCita').value = data.nombre || '';
    document.getElementById('fechaNacimientoCita').value = data.fechaNacimiento || '';
    document.getElementById('fechaNacimientoCita').dispatchEvent(new Event('change'));


    // Calcular edad
    if (data.fechaNacimiento) {
        console.log("Fecha de nacimiento recibida:", data.fechaNacimiento);

        const fechaNac = new Date(data.fechaNacimiento + 'T00:00:00');
        console.log("Fecha nac. como objeto Date:", fechaNac);

        const hoy = new Date();
        const hoyFijo = new Date(hoy.getFullYear(), hoy.getMonth(), hoy.getDate());
        console.log("Fecha actual fija (hoyFijo):", hoyFijo);

        let edad = hoyFijo.getFullYear() - fechaNac.getFullYear();
        const cumpleEsteAnio = new Date(hoyFijo.getFullYear(), fechaNac.getMonth(), fechaNac.getDate());
        console.log("Cumpleaños este año:", cumpleEsteAnio);

        if (hoyFijo < cumpleEsteAnio) {
            edad--;
            console.log("Aún no cumple años este año, edad ajustada a:", edad);
        } else {
            console.log("Ya cumplió años este año o es hoy, edad:", edad);
        }

        document.getElementById('edadCita').value = edad;
        console.log("Edad colocada en el input:", edad);
    } else {
        console.log("No hay fecha de nacimiento en los datos.");
        document.getElementById('edadCita').value = '';
    }


    // Sexo
    if (data.sexo) {
        document.getElementById('sexoCita').value = data.sexo.toLowerCase();
    }

    // Teléfono
    document.getElementById('telefonoCita').value = data.numCelular || '';

    // Tipo Paciente
    if(data.contratante && data.contratante.aseguradora && data.contratante.aseguradora.tipoPaciente){
        document.getElementById('tipoPacienteCita').value = data.contratante.aseguradora.tipoPaciente.id;
    }

    // Aseguradora
    if(data.contratante && data.contratante.aseguradora){
        const aseguradoraSelect = document.getElementById('aseguradoraCita');
        aseguradoraSelect.innerHTML = '';
        const option = document.createElement('option');
        option.value = data.contratante.aseguradora.id;
        option.text = data.contratante.aseguradora.nombre;
        option.selected = true;
        aseguradoraSelect.appendChild(option);
    }

    // Contratante
    if(data.contratante){
        const contratanteSelect = document.getElementById('contratanteCita');
        contratanteSelect.innerHTML = '';
        const option = document.createElement('option');
        option.value = data.contratante.id;
        option.text = data.contratante.nombre;
        option.selected = true;
        contratanteSelect.appendChild(option);
    }

}

function bloquearCampoVisual(campo) {
    campo.readOnly = true;
    campo.disabled = true;
    campo.style.backgroundColor = "#e9ecef";
    campo.style.color = "#6c757d";
    campo.style.cursor = "not-allowed";
    campo.setAttribute("tabindex", "-1");
    campo.onmousedown = (e) => e.preventDefault();
}

function desbloquearCampoVisual(campo) {
    campo.readOnly = false;
    campo.disabled = false;
    campo.style.backgroundColor = "#fff";
    campo.style.color = "#000";
    campo.style.cursor = "auto";
    campo.removeAttribute("tabindex");
    campo.onmousedown = null;
}

function bloquearCamposDocumento(tipoDocSelect, numeroDocInput, btnBuscar) {
    tipoDocSelect.disabled = true;
    bloquearCampoVisual(numeroDocInput);
    btnBuscar.disabled = true;
}

function desbloquearCamposDocumento(tipoDocSelect, numeroDocInput, btnBuscar) {
    tipoDocSelect.disabled = false;
    desbloquearCampoVisual(numeroDocInput);
    btnBuscar.disabled = false;
}




