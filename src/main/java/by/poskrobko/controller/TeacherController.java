package by.poskrobko.controller;

import by.poskrobko.dto.TeacherDTO;
import by.poskrobko.service.TeacherService;
import com.sun.net.httpserver.HttpExchange;

import java.util.List;
import java.util.regex.Pattern;

public class TeacherController extends BaseController{

    private final TeacherService teacherService = new TeacherService();

    @Override
    public void handle(HttpExchange exchange) {

        if (!isActionAllowed(exchange)) {
            return;
        }
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        handleRequest(exchange, () -> {
            TeacherAction action = TeacherAction.resolve(path, HttpMethod.valueOf(method));
            switch (action) {
                case GET_ALL_TEACHERS -> {
                    System.out.println("Get all users: " + action);
                    List<TeacherDTO> users = teacherService.getAllTeachers();
                    sendJson(exchange, 200, users);
                }
                case PATCH_UPDATE_TEACHER -> {
                    System.out.println("Put update user: " + action);
                    TeacherDTO teacherDTO = jsonMapper.readValue(exchange.getRequestBody(), TeacherDTO.class);
                    teacherService.update(teacherDTO);
                    sendNoContent(exchange);
                }
            }
        });
    }

    private enum TeacherAction {
        GET_TEACHER_BY_ID(Pattern.compile("/teachers/[\\w-]+"), HttpMethod.GET),
        GET_ALL_TEACHERS(Pattern.compile("/teachers"), HttpMethod.GET),
        POST_REGISTER_TEACHER(Pattern.compile("/teachers"), HttpMethod.POST),
        PATCH_UPDATE_TEACHER(Pattern.compile("/teachers"), HttpMethod.PATCH),
        DELETE_TEACHER(Pattern.compile("/teachers/[\\w-]+"), HttpMethod.DELETE),

        DEFAULT(Pattern.compile(""), HttpMethod.DEFAULT);

        private final Pattern path;
        private final HttpMethod method;

        TeacherAction(Pattern path, HttpMethod method) {
            this.path = path;
            this.method = method;
        }

        public static TeacherAction resolve(String pattern, HttpMethod method) {
            for (TeacherAction action : TeacherAction.values()) {
                if (action.path.matcher(pattern).matches() && action.method == method) {
                    return action;
                }
            }
            return DEFAULT;
        }
    }
}
