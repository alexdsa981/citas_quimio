let stompClient = null;
let connected = false;
let wasDisconnected = false;

function conectarWS() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.debug = null; // Silenciar logs

    stompClient.connect({}, function (frame) {
        if (wasDisconnected) {
            console.log('🟢 Reconectado después de desconexión. Recargando página...');
            location.reload(); // ✅ Recarga solo si se había desconectado antes
        } else {
            console.log('🟢 Conectado por primera vez:', frame);
        }

        connected = true;
        wasDisconnected = false;

        // ✅ Suscribirse al tópico y actualizar tabla al recibir mensaje
        stompClient.subscribe('/topic/actualizar-tabla', function (mensaje) {
            console.log("📥 Mensaje recibido:", mensaje.body);
            refrescarTablaSegunFiltro(); // ✅ Refresca tabla sin recargar
        });

    }, function (error) {
        connected = false;
        wasDisconnected = true;
        console.warn("🔴 Desconectado de WebSocket. Reintentando en 5 segundos...", error);

        setTimeout(() => {
            conectarWS();
        }, 5000);
    });
}

// Iniciar al cargar la página
document.addEventListener("DOMContentLoaded", function () {
    conectarWS();
});
