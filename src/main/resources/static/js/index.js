
function convertirFecha(fechaTexto) {
    if (/^\d{4}-\d{2}-\d{2}$/.test(fechaTexto)) {
        return fechaTexto;
    }

    const partes = fechaTexto.split(/[\/\-]/);

    if (partes.length === 3) {
        let dia, mes, anio;

        if (partes[0].length === 2) {
            [dia, mes, anio] = partes;
        } else {
            [anio, mes, dia] = partes;
        }

        return `${anio}-${mes.padStart(2, '0')}-${dia.padStart(2, '0')}`;
    }

    return '';
}


new Litepicker({
    element: document.getElementById('fechaPickerPrincipal'),
    format: 'DD/MM/YYYY',
    startDate: new Date() // Establece la fecha actual
});

