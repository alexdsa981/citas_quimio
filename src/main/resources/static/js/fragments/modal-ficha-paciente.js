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

        // Forzamos el inicio del día para evitar problemas de desfase por zona horaria
        const fechaNac = new Date(this.value + 'T00:00:00');
        const hoy = new Date();
        const hoyFijo = new Date(hoy.getFullYear(), hoy.getMonth(), hoy.getDate()); // Sin horas

        let edad = hoyFijo.getFullYear() - fechaNac.getFullYear();

        const cumpleEsteAnio = new Date(hoyFijo.getFullYear(), fechaNac.getMonth(), fechaNac.getDate());

        if (hoyFijo < cumpleEsteAnio) {
            edad--;
        }

        edadInput.value = edad;
    });
});


/*
function generarOpcionesHora(selectElement) {
for (let h = 7; h < 20; h++) {
  for (let m = 0; m < 60; m += 15) {
    const hora = h.toString().padStart(2, '0');
    const minuto = m.toString().padStart(2, '0');
    const option = document.createElement('option');
    option.value = `${hora}:${minuto}`;
    option.textContent = `${hora}:${minuto}`;
    selectElement.appendChild(option);
  }
}

// Agregar exactamente 20:00
const option = document.createElement('option');
option.value = `20:00`;
option.textContent = `20:00`;
selectElement.appendChild(option);
}

document.querySelectorAll('.hora-select').forEach(generarOpcionesHora);
*/
