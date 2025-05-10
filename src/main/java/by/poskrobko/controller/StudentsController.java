package by.poskrobko.controller;

import by.poskrobko.dto.StudentDTO;
import by.poskrobko.service.StudentService;
import com.sun.net.httpserver.HttpExchange;

import java.util.List;
import java.util.regex.Pattern;

public class StudentsController extends BaseController {

    private final StudentService studentService = new StudentService();

    @Override
    public void handle(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        handleRequest(exchange, () -> {
            StudentsAction action = StudentsAction.resolve(path, HttpMethod.valueOf(method));
            System.out.println(action);
            switch (action) {
                case GET_ALL_STUDENTS -> {
                    String query = exchange.getRequestURI().getQuery();
                    List<StudentDTO> students;
                    if (query != null && query.startsWith("group=")) {
                        String group = query.substring("group=".length());
                        students = studentService.getAllStudents(group);
                    } else {
                        students = studentService.getAllStudents();
                    }
                    sendJson(exchange, 200, students);
                }
                case POST_ADD_STUDENT -> {
                    StudentDTO student = jsonMapper.readValue(exchange.getRequestBody(), StudentDTO.class);
                    StudentDTO saved = studentService.save(student);
                    sendJson(exchange, 201, saved);
                }
                case PATCH_UPDATE_STUDENT -> {
                    StudentDTO student = jsonMapper.readValue(exchange.getRequestBody(), StudentDTO.class);
                    studentService.update(student);
                    sendNoContent(exchange);
                }
                case DELETE_STUDENT -> {
                    String id = path.substring(path.lastIndexOf("/") + 1);
                    studentService.delete(id);
                    sendNoContent(exchange);
                }
                default -> sendJson(exchange, 404, "Not found");
            }
        });
    }

    private enum StudentsAction {
        GET_ALL_STUDENTS(Pattern.compile("/students"), HttpMethod.GET),
        GET_ALL_STUDENTS_OF_GROUP(Pattern.compile("/students?group=[\\w-]+"), HttpMethod.GET),
        POST_ADD_STUDENT(Pattern.compile("/students"), HttpMethod.POST),
        PATCH_UPDATE_STUDENT(Pattern.compile("/students"), HttpMethod.PUT),
        DELETE_STUDENT(Pattern.compile("/students/[\\w-]+"), HttpMethod.DELETE),
        DEFAULT(Pattern.compile(""), HttpMethod.DEFAULT);

        private final Pattern path;
        private final HttpMethod method;

        StudentsAction(Pattern path, HttpMethod method) {
            this.path = path;
            this.method = method;
        }

        public static StudentsAction resolve(String pattern, HttpMethod method) {
            for (StudentsAction action : StudentsAction.values()) {
                if (action.path.matcher(pattern).matches() && action.method == method) {
                    return action;
                }
            }
            return DEFAULT;
        }
    }
}
