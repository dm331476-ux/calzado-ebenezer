package demodos.util;

import java.io.IOException;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Envía respuestas JSON uniformes a los formularios del navegador.
 */
public final class ApiResponse {
    private ApiResponse() {
    }

    /**
     * Escribe una respuesta JSON con su estado HTTP y, si aplica, una ruta de destino.
     */
    public static void send(HttpServletResponse response, int status, boolean success,
            String message, String redirect) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-store");
        response.getWriter().write("{\"success\":" + success
                + ",\"message\":\"" + escape(message) + "\""
                + (redirect == null ? "" : ",\"redirect\":\"" + escape(redirect) + "\"")
                + "}");
    }

    /**
     * Escapa caracteres especiales para incluir texto seguro dentro de una cadena JSON.
     */
    private static String escape(String value) {
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
