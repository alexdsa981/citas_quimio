

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
        aplicarFiltrosOtros();
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
    aplicarFiltrosOtros();
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

    const filasNormales = [];
    const filasCanceladas = [];

    fichas.forEach((dto, index) => {
        const estado = dto.cita_estado || "";

        let claseEstado = "bg-secondary text-white";
        let claseFila = "fila-ficha";
        let bloqueaClick = false;

        if (estado === "PENDIENTE") {
            claseEstado = "bg-warning text-dark";
        } else if (estado === "EN_PROCESO") {
            claseEstado = "bg-info text-dark";
        } else if (estado === "ATENDIDO") {
            claseEstado = "bg-success text-white";
        } else if (estado === "NO_ASIGNADO") {
            claseEstado = "bg-light text-muted";
        } else if (estado === "CANCELADO") {
            claseEstado = "bg-dark text-white";
            claseFila += " bg-light text-muted no-click";
            bloqueaClick = true;
        } else if (estado === "EN_CONFLICTO") {
            claseEstado = "bg-danger text-white";
        } else {
            claseEstado = "bg-light text-muted border";
        }

        const esSeleccionada = dto.ficha_id == idFichaSeleccionada ? 'seleccionada' : '';
        claseFila += esSeleccionada ? ' seleccionada' : '';
        const claseFila2 = dto.ficha_id < 0 ? "ficha_antigua" : "";

        const fila = `
    <tr class="${claseFila} ${claseFila2}" data-id-ficha="${dto.ficha_id}" data-fecha-cita="${dto.cita_fecha}">
                <td class="text-center text-muted">${index + 1}</td>
                ${tdConColor(dto.cita_usuarioCreacion || "")}
                ${tdConColor(dto.cita_aseguradora || "")}
                ${tdConColor(`<b>${(dto.paciente_tipoDocumentoNombre === "D.N.I./Cédula/L.E.") ? "DNI" : (dto.paciente_tipoDocumentoNombre || "")}</b>: ${dto.paciente_numDocIdentidad || ""}`)}
                ${tdConColor(`${dto.paciente_nombreCompleto || ""}`)}
                ${tdConColor(dto.cita_fecha || "")}
                ${tdConColor(calcularRangoHora(dto.cita_horaProgramada, dto.cita_duracionMinutosProtocolo))}
                ${tdConColor(dto.atencion_cubiculo || "")}
                ${tdConColor(formatearHora(dto.atencion_horaInicio) || "")}
                ${tdConColor(formatearHora(dto.atencion_horaFin) || "")}
                ${tdConColor(calcularDuracionReal(dto.atencion_horaInicio, dto.atencion_horaFin))}
                <td><span class="badge ${claseEstado}">${estado}</span></td>
            </tr>
        `;

        if (estado === "CANCELADO") {
            filasCanceladas.push(fila);
        } else {
            filasNormales.push(fila);
        }
    });

    // Insertar normales primero, luego los cancelados
    filasNormales.forEach(f => tbody.insertAdjacentHTML("beforeend", f));
    filasCanceladas.forEach(f => tbody.insertAdjacentHTML("beforeend", f));

    // RESTRICCIÓN DE FECHA
    const fechaActual = document.getElementById("fechaActual").value;
    const hoy = new Date(fechaActual);


document.querySelectorAll("tr[data-fecha-cita]").forEach(tr => {
    const fechaCita = tr.getAttribute("data-fecha-cita");
    const fechaFila = new Date(fechaCita);

    if (fechaCita !== fechaActual) {
        tr.classList.add("tr-fecha-distinta");
    }

    tr.addEventListener("click", () => {

        //Asignar primero el id y la fecha seleccionada
        idFichaSeleccionada = parseInt(tr.dataset.idFicha, 10);
        fecha = fechaFila; // si tienes fecha como global también

        //Quitar bloqueo de todos los botones
        document.querySelectorAll(".bloqueable-fecha-pasada, .btn-duplicar")
            .forEach(btn => btn.classList.remove("bloqueado"));

        //Caso 1: Ficha antigua (ID negativo) → bloquear todo
        if (idFichaSeleccionada < 0) {
            document.querySelectorAll(".bloqueable-fecha-pasada, .btn-duplicar")
                .forEach(btn => btn.classList.add("bloqueado"));
            return;
        }

        // Caso 2: Usuario no administrador
        if (idRolUsuario !== 3) {
            if (fecha < hoy) {
                // Bloquear edición, pero permitir duplicar
                document.querySelectorAll(".bloqueable-fecha-pasada")
                    .forEach(btn => btn.classList.add("bloqueado"));
            } else if (fecha > hoy) {
                // Bloquear solo los que no tengan habilitado-futuro
                document.querySelectorAll(".bloqueable-fecha-pasada")
                    .forEach(btn => {
                        if (!btn.classList.contains("habilitado-futuro")) {
                            btn.classList.add("bloqueado");
                        }
                    });
            }
        }
    });
});



    aplicarFiltrosOtros();
}


function calcularDuracionReal(horaInicio, horaFin) {
    if (!horaInicio) {
        return "Pendiente";
    }

    if (!horaFin) {
        return "En curso";
    }

    const inicio = new Date(`2000-01-01T${horaInicio}`);
    const fin = new Date(`2000-01-01T${horaFin}`);

    const diferenciaMs = fin - inicio;
    const diferenciaMin = Math.floor(diferenciaMs / 60000);

    const horas = Math.floor(diferenciaMin / 60);
    const minutos = diferenciaMin % 60;

    if (horas > 0 && minutos > 0) return `${horas} h ${minutos} min`;
    if (horas > 0) return `${horas} h`;
    return `${minutos} min`;
}



function tdConColor(valor) {
    const limpio = valor?.toString().trim() || "";
    const esVacio = limpio === "" || limpio === "0 h 0 min";
    const clase = esVacio ? 'bg-light-danger' : '';
    return `<td class="${clase}">${limpio}</td>`;
}


function calcularRangoHora(horaInicio, duracionMinutos) {

    if (!horaInicio) return "";

    const [h, m] = horaInicio.split(":").map(Number);
    const inicio = new Date(0, 0, 0, h, m);

    const pad = (num) => String(num).padStart(2, '0');
    const inicioStr = `${pad(inicio.getHours())}:${pad(inicio.getMinutes())}`;

    const totalMin = duracionMinutos || 0;

    // Si no hay duración, mostrar solo la hora programada
    if (totalMin === 0) {
        return inicioStr;
    }

    const fin = new Date(inicio);
    fin.setMinutes(fin.getMinutes() + totalMin);
    const finStr = `${pad(fin.getHours())}:${pad(fin.getMinutes())}`;

    return `${inicioStr} - ${finStr}`;
}





function formatearHora(horaStr) {
    if (!horaStr) return "";

    try {
        const [hora, minuto] = horaStr.split(":");
        return `${hora}:${minuto}`;
    } catch (e) {
        return "";
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
    aplicarFiltrosOtros();
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
    const ficha = fichasGlobal.find(dto => dto.ficha_id == idFicha); // usa == por si el id es string
    if (ficha) {
        const estado = ficha.cita_estado || "NO_ASIGNADO";
        const fecha = ficha.cita_fecha || "";
        const hora = formatearHora(ficha.cita_horaProgramada) || "";

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

                document.getElementById('datosModificablesFuncionesVitalesFicha').classList.add('form-disabled');
                document.getElementById('datosModificablesDetalleQuimioterapiaFicha').classList.add('form-disabled');

                document.getElementById('btnGuardarDetalleQuimioterapiaFicha').disabled = true;
                document.getElementById('btnModificarDetalleQuimioterapiaFicha').disabled = false;

                document.getElementById('btnGuardarSignosVitalesFicha').disabled = true;
                document.getElementById('btnModificarSignosVitalesFicha').disabled = false;


                llenarVisualFichaPaciente(data);
                llenarFormularioFichaAtencionQuimioterapia(data);
                llenarFormularioFichaFuncionesVitales(data)
                llenarFormularioDetalleQuimioterapiaFicha(data);
                llenarDetalleCieDesdeFicha(data);
            })
            .catch(err => {
                console.error('Error al cargar ficha:', err);
                Swal.fire('Error', 'No se pudo cargar la ficha del paciente', 'error');
            });
    });
});


