document.getElementById("btnExportarExcel").addEventListener("click", () => {
    const btn = document.getElementById("btnExportarExcel");
    const icono = btn.querySelector(".icono-exportar");
    const spinner = btn.querySelector(".spinner-border");

    // Desactivar botón y mostrar spinner
    btn.disabled = true;
    icono.style.display = "none";
    spinner.classList.remove("d-none");

    const filasVisibles = Array.from(document.querySelectorAll("tbody tr"))
        .filter(tr => tr.offsetParent !== null && tr.hasAttribute("data-id-ficha") && tr.getAttribute("data-id-ficha") !== "");

    const idsFichas = filasVisibles.map(tr => tr.getAttribute("data-id-ficha"));

    fetch("/app/exportar-fichas/", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(idsFichas)
    })
    .then(response => {
        if (!response.ok) throw new Error("Error al exportar");
        return response.blob();
    })
    .then(blob => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement("a");
        a.href = url;
        a.download = "fichasFiltradas.xlsx";
        document.body.appendChild(a);
        a.click();
        a.remove();
        console.log("🟢 Exportación enviada correctamente.");
    })
    .catch(error => {
        console.error("❌ Error al exportar:", error);
        alert("Ocurrió un error al exportar las fichas.");
    })
    .finally(() => {
        // Restaurar botón y ocultar spinner
        btn.disabled = false;
        icono.style.display = "inline";
        spinner.classList.add("d-none");
    });

    console.log("ID Fichas a exportar:", idsFichas);
});
