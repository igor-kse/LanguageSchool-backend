package by.poskrobko.controller;

import by.poskrobko.dto.ScheduleDTO;
import by.poskrobko.dto.ScheduleToPost;
import by.poskrobko.service.ScheduleService;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class ScheduleController extends BaseController {

    private final ScheduleService scheduleService = new ScheduleService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        handleRequest(exchange, () -> {
            ScheduleAction action = ScheduleAction.resolve(path, HttpMethod.valueOf(method));
            switch (action) {
                case GET_SCHEDULE -> {
                    System.out.println("Get schedule: " + action);
                    List<ScheduleDTO> scheduleDTOs = scheduleService.findAll();
                    sendJson(exchange, 200, scheduleDTOs);
                }
                case POST_SCHEDULE -> {
                    System.out.println("Post schedule: " + action);
                    ScheduleToPost scheduleDTO = jsonMapper.readValue(exchange.getRequestBody(), ScheduleToPost.class);
                    System.out.println(scheduleDTO.toString());
                    ScheduleDTO postedSchedule = scheduleService.create(scheduleDTO);
                    sendJson(exchange, 201, postedSchedule);
                }
                case PUT_SCHEDULE -> {
                    System.out.println("Put update schedule: " + action);
                    ScheduleToPost scheduleDTO = jsonMapper.readValue(exchange.getRequestBody(), ScheduleToPost.class);
                    scheduleService.update(scheduleDTO);
                    sendNoContent(exchange);
                }
                case DELETE_SCHEDULE -> {
                    System.out.println("Delete schedule: " + action);
                    String id = path.substring("/schedule/".length());
                    scheduleService.delete(id);
                    sendNoContent(exchange);
                }
            }
        });
    }

    private enum ScheduleAction {
        GET_SCHEDULE(Pattern.compile("/schedule"), HttpMethod.GET),
        POST_SCHEDULE(Pattern.compile("/schedule"), HttpMethod.POST),
        PUT_SCHEDULE(Pattern.compile("/schedule"), HttpMethod.PUT),
        DELETE_SCHEDULE(Pattern.compile("/schedule/[\\w-]+"), HttpMethod.DELETE),

        DEFAULT(Pattern.compile(""), HttpMethod.DEFAULT);

        private final Pattern path;
        private final HttpMethod method;

        ScheduleAction(Pattern path, HttpMethod method) {
            this.path = path;
            this.method = method;
        }

        public static ScheduleAction resolve(String pattern, HttpMethod method) {
            for (ScheduleAction action : ScheduleAction.values()) {
                if (action.path.matcher(pattern).matches() && action.method == method) {
                    return action;
                }
            }
            return DEFAULT;
        }
    }
}
