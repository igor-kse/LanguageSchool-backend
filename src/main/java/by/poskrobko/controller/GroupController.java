package by.poskrobko.controller;

import by.poskrobko.dto.GroupDTO;
import by.poskrobko.dto.StudentDTO;
import by.poskrobko.service.GroupService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.sun.net.httpserver.HttpExchange;

import java.util.List;
import java.util.regex.Pattern;

public class GroupController extends BaseController {


    private final GroupService groupService = new GroupService();

    @Override
    public void handle(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        handleRequest(exchange, () -> {
            GroupAction action = GroupAction.resolve(path, BaseController.HttpMethod.valueOf(method));
            switch (action) {
                case GET_GROUP_BY_ID -> {
                    System.out.println("Get group by id: " + action);
                    String id = path.substring("/groups/".length());
                    GroupDTO groupDTO = groupService.findById(id);

                    sendJson(exchange, 200, groupDTO);
                }
                case GET_ALL_GROUPS -> {
                    System.out.println("Get all groups: " + action);
                    List<GroupDTO> groups = groupService.findAll();
                    sendJson(exchange, 200, groups);
                }
                case POST_REGISTER_GROUP -> {
                    System.out.println("Post register group: " + action);
                    GroupDTO groupToRegister = jsonMapper.readValue(exchange.getRequestBody(), GroupDTO.class);
                    GroupDTO registeredGroup = groupService.create(groupToRegister);
                    sendJson(exchange, 201, registeredGroup);
                }
                case PUT_UPDATE_GROUP -> {
                    System.out.println("Put update group: " + action);
                    GroupDTO groupToUpdate = jsonMapper.readValue(exchange.getRequestBody(), GroupDTO.class);
                    groupService.update(groupToUpdate);
                    sendNoContent(exchange);
                }
                case PUT_ADD_STUDENTS -> {
                    System.out.println("Put ad students: " + action);
                    String groupId = path.substring("/groups/".length(), path.length() - "/students".length());
                    List<String> students = jsonMapper.readValue(exchange.getRequestBody(), new TypeReference<>() {});
                    groupService.updateStudents(groupId, students);
                }
                case DELETE_GROUP -> {
                    System.out.println("Delete group: " + action);
                    String id = path.substring("/groups/".length());
                    groupService.delete(id);
                    sendNoContent(exchange);
                }
            }
        });
    }

    private enum GroupAction {
        GET_GROUP_BY_ID(Pattern.compile("/groups/[\\w-]+"), BaseController.HttpMethod.GET),
        GET_ALL_GROUPS(Pattern.compile("/groups"), BaseController.HttpMethod.GET),
        POST_REGISTER_GROUP(Pattern.compile("/groups"), BaseController.HttpMethod.POST),
        PUT_ADD_STUDENTS(Pattern.compile("/groups/[\\w-]+/students"), BaseController.HttpMethod.PUT),
        PUT_UPDATE_GROUP(Pattern.compile("/groups"), BaseController.HttpMethod.PUT),
        DELETE_GROUP(Pattern.compile("/groups/[\\w-]+"), BaseController.HttpMethod.DELETE),

        DEFAULT(Pattern.compile(""), BaseController.HttpMethod.DEFAULT);

        private final Pattern path;
        private final BaseController.HttpMethod method;

        GroupAction(Pattern path, BaseController.HttpMethod method) {
            this.path = path;
            this.method = method;
        }

        public static GroupAction resolve(String pattern, BaseController.HttpMethod method) {
            for (GroupAction action : GroupAction.values()) {
                if (action.path.matcher(pattern).matches() && action.method == method) {
                    return action;
                }
            }
            return DEFAULT;
        }
    }
}
