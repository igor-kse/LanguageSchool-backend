package by.poskrobko.controller;

import by.poskrobko.SessionStorage;
import by.poskrobko.dto.UserDTO;
import by.poskrobko.model.Role;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import exception.ExistingEntityException;
import exception.NotExistingEntityException;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

public abstract class BaseController implements HttpHandler {

    protected final ObjectMapper jsonMapper = JsonMapper.builder()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                .build()
                .registerModule(new JavaTimeModule());

    private String cookie = "";


    public boolean isActionAllowed(HttpExchange exchange) {
        try {
            var cookies = exchange.getRequestHeaders().get("SESSIONID");
            cookie = cookies != null && !cookies.isEmpty() ? cookies.getFirst() : "";
            System.out.println("Request id: " + cookie);

            UserDTO user = SessionStorage.getUser(cookie);
            boolean isAdmin = user != null && user.roles().contains(Role.ADMIN);
            boolean isGet = "GET".equalsIgnoreCase(exchange.getRequestMethod());
            if (!isGet && !isAdmin) {
                exchange.setAttribute("forbidden", true);
                sendJson(exchange, 403, Collections.singletonMap("error", "Forbidden"));
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    protected void handleRequest(HttpExchange exchange, HttpExchangeProcessable processor) {
        if (Boolean.TRUE.equals(exchange.getAttribute("forbidden"))) {
            return;
        }
        try {
            processor.process();
        } catch (ExistingEntityException | NotExistingEntityException e) {
            e.printStackTrace();
            sendJson(exchange, 400, Collections.singletonMap("error", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            sendJson(exchange, 500, Collections.singletonMap("error", e.getMessage()));
        }
    }

    protected void sendJson(HttpExchange exchange, int status, Object data) {
        try {
            String json = jsonMapper.writeValueAsString(data);
            exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(status, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void sendNoContent(HttpExchange exchange) {
        try {
            exchange.sendResponseHeaders(204, -1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected enum HttpMethod {
        GET,
        POST,
        PUT,
        DELETE,
        PATCH,
        DEFAULT
    }

    protected interface HttpExchangeProcessable {
        void process() throws IOException;
    }

    protected String getCookie() {
        return cookie;
    }
}
