function llenarFormularioFichaPaciente(objetoFicha) {
    const paciente = objetoFicha.cita.paciente;
    const tipoDoc = paciente.tipoDocIdentidad || {};
    const contratante = paciente.contratante || {};
    const aseguradora = contratante.aseguradora || {};
    const tipoPaciente = aseguradora.tipoPaciente || {};
    const tipoEntrada = objetoFicha.tipoEntrada || {};

    // Entrada
    document.getElementById('tipoEntradaFicha').value = tipoEntrada.id || "";

    // Documento
    document.getElementById('tipoDocIdentidadFicha').value = tipoDoc.id || "";
    document.getElementById('numeroDocumentoFicha').value = paciente.numDocIdentidad || "";

    // Apellidos y Nombres
    document.getElementById('apellidoPaternoFicha').value = paciente.apellidoP || "";
    document.getElementById('apellidoMaternoFicha').value = paciente.apellidoM || "";
    document.getElementById('nombresFicha').value = paciente.nombre || "";

    // Fecha nacimiento
    document.getElementById('fechaNacimientoFicha').value = paciente.fechaNacimiento || "";

    // Edad (si quieres calcularla automáticamente)
    calcularEdadDesdeNacimiento('fechaNacimientoFicha', 'edadFicha');

    // Sexo
    document.getElementById('sexoFicha').value = paciente.sexo || "";

    // Teléfono
    document.getElementById('telefonoFicha').value = paciente.numCelular || "";

    // Tipo Paciente
    document.getElementById('tipoPacienteFicha').value = tipoPaciente.id || "";

    // Aseguradora
    const selectAseguradora = document.getElementById('aseguradoraFicha');
    selectAseguradora.innerHTML = `<option value="${aseguradora.id}">${aseguradora.nombre}</option>`;
    selectAseguradora.value = aseguradora.id || "";

    // Contratante
    const selectContratante = document.getElementById('contratanteFicha');
    selectContratante.innerHTML = `<option value="${contratante.id}">${contratante.nombre}</option>`;
    selectContratante.value = contratante.id || "";
}

function calcularEdadDesdeNacimiento(idFechaNacimiento, idEdad) {
    const fechaInput = document.getElementById(idFechaNacimiento).value;
    if (!fechaInput) return;

    const fechaNacimiento = new Date(fechaInput);
    const hoy = new Date();
    let edad = hoy.getFullYear() - fechaNacimiento.getFullYear();
    const m = hoy.getMonth() - fechaNacimiento.getMonth();
    if (m < 0 || (m === 0 && hoy.getDate() < fechaNacimiento.getDate())) {
        edad--;
    }

    document.getElementById(idEdad).value = edad;
}
