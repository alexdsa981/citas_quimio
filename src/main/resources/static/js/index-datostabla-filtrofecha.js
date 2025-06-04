document.addEventListener("DOMContentLoaded", function () {
    cargarFichasHoy();

    const picker = new Litepicker({
        element: document.getElementById('filtroFecha'),
        singleMode: false,
        format: 'DD/MM/YYYY',
        autoApply: true,
    });

    picker.on('selected', (date1, date2) => {
        if (date1 && date2) {
            const desde = date1.format('YYYY-MM-DD');
            const hasta = date2.format('YYYY-MM-DD');
            console.log('Fechas seleccionadas (evento selected):', { desde, hasta });
            cargarFichasEntreFechas(desde, hasta);
        }
    });
});



function cargarFichasHoy() {
    fetch("/app/ficha-paciente/fichas/hoy")
        .then(res => res.json())
        .then(data => llenarTablaFichas(data))
        .catch(err => console.error("Error al obtener fichas del día:", err));
}

function cargarFichasEntreFechas(fechaInicio, fechaFin) {
    const body = {
        desde: fechaInicio,
        hasta: fechaFin
    };

    fetch("/app/ficha-paciente/fichas/entre-fechas", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body)
    })
        .then(res => res.json())
        .then(data => llenarTablaFichas(data))
        .catch(err => console.error("Error al obtener fichas entre fechas:", err));
}


let fichasGlobal = [];
function llenarTablaFichas(fichas) {
    fichasGlobal = fichas;
    const tbody = document.querySelector("table tbody");
    tbody.innerHTML = "";
    if (fichas.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="11" class="text-center text-muted">⚠️ No se encontraron fichas para el rango de fechas seleccionado.</td>
            </tr>
        `;
        return;
    }

    fichas.forEach(ficha => {
        const cita = ficha.cita || {};
        const paciente = cita.paciente || {};
        const contratante = paciente.contratante || {};
        const aseguradora = contratante.aseguradora || {};
        const cubiculo = ficha.atencionQuimioterapiaList?.[0]?.cubiculo || {};
        const atencion = ficha.atencionQuimioterapiaList?.[0] || {};
        const estado = cita.estado || "";

        let claseEstado = "bg-secondary";
            if (estado === "PENDIENTE") {
                claseEstado = "bg-warning text-dark";
            } else if (estado === "EN_PROCESO") {
                claseEstado = "bg-info text-dark";
            } else if (estado === "ATENDIDO") {
                claseEstado = "bg-success";
            } else if (estado === "NO_ASIGNADO") {
                claseEstado = "bg-secondary text-white";
            }

        const fila = `
            <tr class="fila-ficha" data-id-ficha="${ficha.id}">
                <td><b>${paciente.tipoDocIdentidad.nombre || ""}</b> : ${paciente.numDocIdentidad || ""}</td>
                <td>${paciente.apellidoP || ""} ${paciente.apellidoM || ""}, ${paciente.nombre || ""}</td>
                <td>${cita.fecha || "n/d"}</td>
                <td>${cubiculo.codigo || ""}</td>
                <td>${formatearHora(cita.horaProgramada) || "n/d"}</td>
                <td>${formatearHora(atencion.horaInicio) || "n/d"}</td>
                <td>${formatearHora(atencion.horaFin) || "n/d"}</td>
                <td>${atencion.horasProtocolo ?? 0} h ${atencion.minutosRestantesProtocolo ?? 0} min</td>
                <td><span class="badge ${claseEstado}">${estado}</span></td>
                <td>${aseguradora.tipoPaciente?.nombre || ""}</td>
            </tr>
        `;

        tbody.insertAdjacentHTML("beforeend", fila);
    });

}




function formatearHora(horaStr) {
    if (!horaStr) return "n/d";

    try {
        const [hora, minuto] = horaStr.split(":");
        return `${hora}:${minuto}`;
    } catch (e) {
        return "n/d";
    }
}


function refrescarTablaSegunFiltro() {
    const pickerValue = document.getElementById('filtroFecha').value;

    const convertirFechaISO = (fechaStr) => {
        const partes = fechaStr.split('/');
        return `${partes[2]}-${partes[1].padStart(2, '0')}-${partes[0].padStart(2, '0')}`;
    };

    if (!pickerValue) {
        cargarFichasHoy();
    } else {
        const rango = pickerValue.split(" - ");
        if (rango.length === 2) {
            const [desde, hasta] = rango.map(convertirFechaISO);
            cargarFichasEntreFechas(desde, hasta);
        } else if (rango.length === 1 && rango[0]) {
            const fecha = convertirFechaISO(rango[0]);
            cargarFichasEntreFechas(fecha, fecha);
        } else {
            cargarFichasHoy();
        }
    }
}


document.addEventListener('DOMContentLoaded', () => {
    const tbody = document.querySelector('table tbody');
    const modalFichaPaciente = new bootstrap.Modal(document.getElementById('modalFichaPaciente'));

    tbody.addEventListener('dblclick', (e) => {
        // Encontrar la fila (tr) más cercana al click
        const fila = e.target.closest('tr.fila-ficha');
        if (!fila) return; // Si no se hizo dblclick en una fila válida, salir

        // Obtener el id de la ficha
        const idFicha = fila.dataset.idFicha;
        console.log('Ficha seleccionada:', idFicha);

    // Obtener datos de la variable global
    const ficha = fichasGlobal.find(f => f.id == idFicha); // usa == por si el id es string
    if (ficha) {
        const cita = ficha.cita || {};
        const estado = cita.estado || "NO_ASIGNADO";
        const fecha = cita.fecha || "n/d";
        const hora = formatearHora(cita.horaProgramada) || "n/d";

        let claseEstado = "bg-secondary text-white";
        if (estado === "PENDIENTE") claseEstado = "bg-warning text-dark";
        else if (estado === "EN_PROCESO") claseEstado = "bg-info text-dark";
        else if (estado === "ATENDIDO") claseEstado = "bg-success";
        else if (estado === "NO_ASIGNADO") claseEstado = "bg-secondary text-white";

        document.getElementById("info-cita").innerHTML = `
            <div><strong>Fecha:</strong> ${fecha} | <strong>Hora:</strong> ${hora}</div>
            <div><span class="badge ${claseEstado}">${estado}</span></div>
        `;
    }



        // Abrir el modal
        modalFichaPaciente.show();

        // Fetch para obtener la ficha por id
        fetch(`/app/ficha-paciente/${idFicha}`)
            .then(res => {
                if (!res.ok) throw new Error(`Error HTTP ${res.status}`);
                return res.json();
            })
            .then(data => {
                console.log('Datos ficha:', data);

                document.getElementById('datosModificablesPacienteFicha').classList.add('form-disabled');
                document.getElementById('datosModificablesAtencionQuimioterapiaFicha').classList.add('form-disabled');

                document.getElementById('btnGuardarPacienteFicha').disabled = true;
                document.getElementById('btnModificarPacienteFicha').disabled = false;

                document.getElementById('btnGuardarAtencionQuimioterapiaFicha').disabled = true;
                document.getElementById('btnModificarAtencionQuimioterapiaFicha').disabled = false;

                llenarFormularioFichaPaciente(data);
                llenarFormularioFichaAtencionQuimioterapia(data);
                llenarFormularioFichaFuncionesVitales(data)
            })
            .catch(err => {
                console.error('Error al cargar ficha:', err);
                Swal.fire('Error', 'No se pudo cargar la ficha del paciente', 'error');
            });
    });
});


