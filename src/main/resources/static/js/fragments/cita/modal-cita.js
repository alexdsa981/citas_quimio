//FECHA PRINCIPAL SELECCIONADA SE COPIA AL MODAL
document.getElementById('btnAgregarFecha').addEventListener('click', function() {
    const fechaTexto = document.getElementById('fechaPickerPrincipal').value;
    const fechaFormateada = convertirFecha(fechaTexto);
    document.getElementById('fechaCitaSeleccionada').value = fechaFormateada;
});


//MUESTRA AL PRESIONAR CUALQUIER PARTE DEL INPUT
const inputHora = document.getElementById('horaProgramada');
inputHora.addEventListener('click', function () {
    this.showPicker?.();
});

//TIPO PACIENTE --> ASEGURADORA --> CONTRATANTE
document.addEventListener("DOMContentLoaded", function () {
    const tipoPacienteSelect = document.getElementById("tipoPaciente");
    const aseguradoraSelect = document.getElementById("aseguradora");
    const contratanteSelect = document.getElementById("contratante");

    function cargarAseguradoras(idTipoPaciente, callback) {
        fetch(`/app/clasificadores/tipopaciente/lista-aseguradoras/${idTipoPaciente}`)
            .then(res => res.json())
            .then(data => {
                aseguradoraSelect.innerHTML = "";
                data.forEach((aseg, index) => {
                    const option = document.createElement("option");
                    option.value = aseg.id;
                    option.text = aseg.nombre;
                    aseguradoraSelect.appendChild(option);
                    if (index === 0) option.selected = true;
                });
                if (data.length > 0) {
                    callback(data[0].id); // cargar contratantes del primero
                }
            });
    }

    function cargarContratantes(idAseguradora) {
        fetch(`/app/clasificadores/aseguradora/lista-contratantes/${idAseguradora}`)
            .then(res => res.json())
            .then(data => {
                contratanteSelect.innerHTML = "";
                data.forEach((contratante, index) => {
                    const option = document.createElement("option");
                    option.value = contratante.id;
                    option.text = contratante.nombre;
                    contratanteSelect.appendChild(option);
                    if (index === 0) option.selected = true;
                });
            });
    }

    // Eventos
    tipoPacienteSelect.addEventListener("change", function () {
        const idTipoPaciente = this.value;
        cargarAseguradoras(idTipoPaciente, cargarContratantes);
    });

    aseguradoraSelect.addEventListener("change", function () {
        const idAseguradora = this.value;
        cargarContratantes(idAseguradora);
    });

    // Inicialización al cargar la página
    const idInicial = tipoPacienteSelect.value;
    cargarAseguradoras(idInicial, cargarContratantes);
});


//EDAD AL PONER FECHA
document.addEventListener('DOMContentLoaded', function () {
    const fechaNacimientoInput = document.getElementById('fechaNacimiento');
    const edadInput = document.getElementById('edad');

    fechaNacimientoInput.addEventListener('change', function () {
        if (!this.value) {
            edadInput.value = '';
            return;
        }

        // Forzar el inicio del día para evitar desfase
        const fechaNac = new Date(this.value + 'T00:00:00');
        const hoy = new Date();
        const hoyFijo = new Date(hoy.getFullYear(), hoy.getMonth(), hoy.getDate());

        let edad = hoyFijo.getFullYear() - fechaNac.getFullYear();
        const cumpleEsteAnio = new Date(hoyFijo.getFullYear(), fechaNac.getMonth(), fechaNac.getDate());

        if (hoyFijo < cumpleEsteAnio) {
            edad--;
        }

        if (edad < 0) {
            edadInput.value = '';
            Swal.fire({
                icon: 'error',
                title: 'Fecha inválida',
                text: 'La fecha de nacimiento no puede ser en el futuro.'
            });
            return;
        }

        edadInput.value = edad;
    });
});

