package by.poskrobko.controller;

import by.poskrobko.SessionStorage;
import by.poskrobko.dto.CredentialsDTO;
import by.poskrobko.dto.UserDTO;
import by.poskrobko.service.UserService;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuthController extends BaseController {
    private final UserService userService = new UserService();

    private final String AUTH_LOGIN_PATH = "/auth/login";

    @Override
    public void handle(HttpExchange exchange) {
        super.handle(exchange);
        handleRequest(exchange, () -> {
            String path = exchange.getRequestURI().getPath();
            String method = exchange.getRequestMethod();

            if (!path.equals(AUTH_LOGIN_PATH) || (HttpMethod.valueOf(method) != HttpMethod.POST)) {
                sendJson(exchange, 404, Map.of("error", "Not found"));
                return;
            }

            CredentialsDTO credentials = jsonMapper.readValue(exchange.getRequestBody(), CredentialsDTO.class);
            boolean ok = userService.checkByCredentials(credentials.email(), credentials.password());
            if (!ok) {
                sendJson(exchange, 401, Map.of("error", "Invalid credentials"));
                return;
            }

            UserDTO user = userService.findByEmail(credentials.email());
            String sessionId = UUID.randomUUID().toString();
            SessionStorage.addSession(sessionId, user.id());

            exchange.getResponseHeaders().add("Set-Cookie", "SESSIONID=" + sessionId + "; HttpOnly; Path=/");

            var response = new HashMap<String, Object>();
            response.put("result", "ok");
            response.put("userId", user.id());
            response.put("firstname", user.firstName());
            response.put("lastname", user.lastName());
            response.put("email", user.email());
            response.put("roles", user.roles());
            response.put("avatarBase64", user.avatarBase64());

            sendJson(exchange, 200, response);
        });
    }
}
