package by.poskrobko.controller;

import by.poskrobko.dto.UserDTO;
import by.poskrobko.dto.UserToRegisterDTO;
import by.poskrobko.dto.UserToUpdateDTO;
import by.poskrobko.service.UserService;
import com.sun.net.httpserver.HttpExchange;

import java.util.List;
import java.util.regex.Pattern;

public class UserController extends BaseController {

    private final UserService userService = new UserService();

    @Override
    public void handle(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        handleRequest(exchange, () -> {
            UserAction action = UserAction.resolve(path, HttpMethod.valueOf(method));
            switch (action) {
                case GET_USER_BY_ID -> {
                    System.out.println("Get user by id: " + action);
                    String id = path.substring("/users/".length());
                    UserDTO userDTO = userService.findById(id);

                    sendJson(exchange, 200, userDTO);
                }
                case GET_ALL_USERS -> {
                    System.out.println("Get all users: " + action);
                    List<UserDTO> users = userService.getAll();
                    sendJson(exchange, 200, users);
                }
                case POST_REGISTER_USER -> {
                    System.out.println("Post register user: " + action);
                    UserToRegisterDTO userToRegisterDTO = jsonMapper.readValue(exchange.getRequestBody(), UserToRegisterDTO.class);
                    UserDTO registeredUser = userService.registerUser(userToRegisterDTO);
                    sendJson(exchange, 201, registeredUser);
                }
                case PUT_UPDATE_USER -> {
                    System.out.println("Put update user: " + action);
                    UserToUpdateDTO userToUpdateDTO = jsonMapper.readValue(exchange.getRequestBody(), UserToUpdateDTO.class);
                    userService.update(userToUpdateDTO);
                    sendNoContent(exchange);
                }
                case DELETE_USER -> {
                    System.out.println("Delete user: " + action);
                    String id = path.substring("/users/".length());
                    userService.delete(id);
                    sendNoContent(exchange);
                }
            }
        });
    }

    private enum UserAction {
        GET_USER_BY_ID(Pattern.compile("/users/[\\w-]+"), HttpMethod.GET),
        GET_ALL_USERS(Pattern.compile("/users"), HttpMethod.GET),
        POST_REGISTER_USER(Pattern.compile("/users"), HttpMethod.POST),
        PUT_UPDATE_USER(Pattern.compile("/users"), HttpMethod.PUT),
        DELETE_USER(Pattern.compile("/users/[\\w-]+"), HttpMethod.DELETE),

        DEFAULT(Pattern.compile(""), HttpMethod.DEFAULT);

        private final Pattern path;
        private final HttpMethod method;

        UserAction(Pattern path, HttpMethod method) {
            this.path = path;
            this.method = method;
        }

        public static UserAction resolve(String pattern, HttpMethod method) {
            for (UserAction action : UserAction.values()) {
                if (action.path.matcher(pattern).matches() && action.method == method) {
                    return action;
                }
            }
            return DEFAULT;
        }
    }
}
