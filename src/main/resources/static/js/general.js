//COLOCA CLASE IS-FILLED A LOS INPUT QUE TIENEN VALORES
document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('input, select, textarea').forEach(el => {
        const checkFilled = () => {
            if ((el.tagName === 'SELECT' && el.value !== '') ||
                (el.tagName !== 'SELECT' && el.value.trim() !== '')) {
                el.classList.add('is-filled');
            } else {
                el.classList.remove('is-filled');
            }
        };

        // Inicial
        checkFilled();

        // Detectar cambios
        el.addEventListener('change', checkFilled);
        el.addEventListener('input', checkFilled);
    });
});

//ACTUALIZA HORA CADA SEGUNDO
function actualizarHora() {
    const ahora = new Date();
    const horas = String(ahora.getHours()).padStart(2, '0');
    const minutos = String(ahora.getMinutes()).padStart(2, '0');
    const segundos = String(ahora.getSeconds()).padStart(2, '0');

    document.getElementById('horaActual').textContent = `${horas}:${minutos}:${segundos}`;
}
setInterval(actualizarHora, 1000);


