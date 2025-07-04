function abrirModalBuscarPaciente() {
    const modalElement = document.getElementById('modalBuscarPaciente');
    const modal = new bootstrap.Modal(modalElement);
    modal.show();
    modalElement.addEventListener('shown.bs.modal', function () {
        document.getElementById('inputBuscarNombrePaciente').focus();
    }, { once: true });
}

function cambiarModoBusquedaPaciente() {
    const tipo = document.getElementById("tipoBusquedaPaciente").value;

    document.getElementById("campoBusquedaNombre").classList.add("d-none");
    document.getElementById("campoTipoDocumento").classList.add("d-none");
    document.getElementById("campoNumeroDocumento").classList.add("d-none");

    if (tipo === "nombre") {
        document.getElementById("campoBusquedaNombre").classList.remove("d-none");
    } else if (tipo === "documento") {
        document.getElementById("campoTipoDocumento").classList.remove("d-none");
        document.getElementById("campoNumeroDocumento").classList.remove("d-none");
    }

    document.getElementById("resultadoPacientes").innerHTML = `<div class="text-muted">Ingrese un término para buscar.</div>`;
}


 const inputHora = document.getElementById("horaProgramadaCita");

 inputHora.addEventListener("click", function () {
     this.showPicker?.();
 });

 const inputFecha = document.getElementById("fechaProgramadaCita");

 inputFecha.addEventListener("click", function () {
     this.showPicker?.();
 });

