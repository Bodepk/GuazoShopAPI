package com.bode.guazo.service;

import com.bode.guazo.entity.Compra;
import com.bode.guazo.entity.CompraProductos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Envía notificaciones de WhatsApp usando CallMeBot (gratuito).
 *
 * Configuración inicial (una sola vez):
 *  1. Agrega el número +34 644 59 78 89 a tus contactos de WhatsApp como "CallMeBot"
 *  2. Envíale el mensaje: "I allow callmebot to send me messages"
 *  3. Recibirás tu API key por WhatsApp
 *  4. Configura las variables de entorno WA_PHONE y WA_APIKEY
 */
@Service
public class WhatsAppService {

    private static final Logger log = LoggerFactory.getLogger(WhatsAppService.class);
    private static final String CALLMEBOT_URL = "https://api.callmebot.com/whatsapp.php";

    @Value("${whatsapp.phone:}")
    private String phone;

    @Value("${whatsapp.apikey:}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public void notificarNuevaCompra(Compra compra) {
        if (phone.isBlank() || apiKey.isBlank()) {
            log.warn("WhatsApp no configurado. Agrega WA_PHONE y WA_APIKEY en application-dev.properties");
            return;
        }

        String mensaje = buildMensaje(compra);
        enviar(mensaje);
    }

    private String buildMensaje(Compra compra) {
        StringBuilder sb = new StringBuilder();
        sb.append("🛒 *NUEVO PEDIDO #").append(compra.getId()).append("*\n\n");
        sb.append("👤 *Cliente:* ").append(capitalizarNombre(compra.getNombreCliente())).append("\n");
        sb.append("📞 *Teléfono:* ").append(compra.getCliente().getNumero()).append("\n");
        sb.append("📍 *Dirección:* ").append(compra.getDireccion()).append("\n\n");
        sb.append("🛍️ *Productos:*\n");

        if (compra.getProductos() != null) {
            for (CompraProductos cp : compra.getProductos()) {
                sb.append("  • ").append(cp.getProducto().getNombre())
                  .append(" x").append(cp.getCantidad())
                  .append(" = $").append(String.format("%.2f", cp.getSubTotal())).append("\n");
            }
        }

        sb.append("\n💰 *Total: $").append(String.format("%.2f", compra.getTotal())).append("*\n");
        sb.append("📦 *Estado:* PENDIENTE\n\n");
        sb.append("_Gestiona el pedido desde la app de escritorio._");

        return sb.toString();
    }

    private void enviar(String mensaje) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(CALLMEBOT_URL)
                    .queryParam("phone", phone)
                    .queryParam("text", mensaje)
                    .queryParam("apikey", apiKey)
                    .toUriString();

            restTemplate.getForObject(url, String.class);
            log.info("Notificación WhatsApp enviada correctamente");
        } catch (Exception e) {
            // No lanzamos excepción — si WhatsApp falla, la compra igual se guarda
            log.error("Error enviando notificación WhatsApp: {}", e.getMessage());
        }
    }

    private String capitalizarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) return nombre;
        String[] palabras = nombre.trim().toLowerCase().split("\\s+");
        StringBuilder resultado = new StringBuilder();
        for (String palabra : palabras) {
            if (!resultado.isEmpty()) resultado.append(" ");
            resultado.append(Character.toUpperCase(palabra.charAt(0))).append(palabra.substring(1));
        }
        return resultado.toString();
    }
}
