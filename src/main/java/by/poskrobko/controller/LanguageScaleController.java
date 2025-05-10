package by.poskrobko.controller;

import by.poskrobko.dto.ScaleDTO;
import by.poskrobko.service.LanguageScaleService;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Pattern;

public class LanguageScaleController extends BaseController {

    private final LanguageScaleService languageScaleService = new LanguageScaleService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        handleRequest(exchange, () -> {
            LanguageScaleAction action = LanguageScaleAction.resolve(path, HttpMethod.valueOf(method));
            switch (action) {
                case GET_SCALE_BY_NAME -> {
                    System.out.println("Get language scale by name: " + action);
                    String name = path.substring("/scales/".length());
                    ScaleDTO dto = languageScaleService.findByName(name);
                    sendJson(exchange, 200, dto);
                }
                case GET_ALL_SCALES -> {
                    System.out.println("Get all language scales: " + action);
                    List<ScaleDTO> dtoList = languageScaleService.findAll();
                    sendJson(exchange, 200, dtoList);
                }
                case POST_ADD_NEW_SCALE -> {
                    System.out.println("Post add a new language scale: " + action);
                    ScaleDTO dto = jsonMapper.readValue(exchange.getRequestBody(), ScaleDTO.class);
                    ScaleDTO savedScale = languageScaleService.save(dto);
                    sendJson(exchange, 201, savedScale);
                }
                case PUT_UPDATE_SCALE -> {
                    System.out.println("Put update language scale: " + action);
                    ScaleDTO dto = jsonMapper.readValue(exchange.getRequestBody(), ScaleDTO.class);
                    String oldName = URLDecoder.decode(path.substring("/scales/".length()), StandardCharsets.UTF_8);
                    languageScaleService.update(oldName, dto);
                    sendNoContent(exchange);
                }
                case DELETE_SCALE -> {
                    System.out.println("Delete language scale: " + action);
                    String name = path.substring("/scales/".length());
                    languageScaleService.delete(name);
                    sendNoContent(exchange);
                }
            }
        });
    }

    private enum LanguageScaleAction {
        GET_SCALE_BY_NAME(Pattern.compile("/scales/[\\wа-яА-ЯёЁ-]+"), HttpMethod.GET),
        GET_ALL_SCALES(Pattern.compile("/scales"), HttpMethod.GET),
        POST_ADD_NEW_SCALE(Pattern.compile("/scales"), HttpMethod.POST),
        PUT_UPDATE_SCALE(Pattern.compile("/scales/[^/]+"), HttpMethod.PUT),
        DELETE_SCALE(Pattern.compile("/scales/[\\wа-яА-ЯёЁ-]+"), HttpMethod.DELETE),

        DEFAULT(Pattern.compile(""), HttpMethod.DEFAULT);

        private final Pattern path;
        private final HttpMethod method;

        LanguageScaleAction(Pattern path, HttpMethod method) {
            this.path = path;
            this.method = method;
        }

        public static LanguageScaleAction resolve(String pattern, HttpMethod method) {
            for (LanguageScaleAction action : LanguageScaleAction.values()) {
                if (action.path.matcher(pattern).matches() && action.method == method) {
                    return action;
                }
            }
            return DEFAULT;
        }
    }
}
