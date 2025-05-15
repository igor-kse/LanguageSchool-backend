package by.poskrobko.controller;

import by.poskrobko.dto.LanguageDTO;
import by.poskrobko.dto.LanguageEntryDTO;
import by.poskrobko.service.LanguageService;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class LanguageController extends BaseController {

    private final LanguageService languageService = new LanguageService();

    @Override
    public void handle(HttpExchange exchange) {
        super.handle(exchange);
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        handleRequest(exchange, () -> {
            LanguageAction action = LanguageAction.resolve(path, HttpMethod.valueOf(method));
            switch (action) {
                case GET_LANGUAGE_BY_NAME -> {
                    System.out.println("Get language by name: " + action);
                    String name = path.substring("/languages/".length());
                    LanguageDTO languageDTO = languageService.findByName(name);
                    sendJson(exchange, 200, languageDTO);
                }
                case GET_ALL_LANGUAGES -> {
                    System.out.println("Get all languages " + action);
                    List<LanguageEntryDTO> languageDTOs = languageService.findAll();
                    sendJson(exchange, 200, languageDTOs);
                }
                case GET_ALL_LANGUAGES_WITH_SCALES -> {
                    System.out.println("Get all languages with scales " + action);
                    List<LanguageDTO> languageDTOs = languageService.findAllWithScales();
                    sendJson(exchange, 200, languageDTOs);
                }
                case POST_ADD_LANGUAGE -> {
                    System.out.println("Post add a new language " + action);
                    LanguageEntryDTO dto = jsonMapper.readValue(exchange.getRequestBody(), LanguageEntryDTO.class);
                    LanguageEntryDTO saved = languageService.save(dto);
                    sendJson(exchange, 201, saved);
                }
                case PUT_UPDATE_LANGUAGE -> {
                    System.out.println("Put update language " + action);
                    LanguageEntryDTO dto = jsonMapper.readValue(exchange.getRequestBody(), LanguageEntryDTO.class);
                    languageService.update(dto);
                    sendNoContent(exchange);
                }
                case DELETE_LANGUAGE -> {
                    System.out.println("Delete language " + action);
                    String name = path.substring("/languages/".length());
                    languageService.delete(name);
                    sendNoContent(exchange);
                }
            }
        });
    }

    private enum LanguageAction {
        GET_LANGUAGE_BY_NAME(Pattern.compile("/languages/[\\wа-яА-ЯёЁ-]+"), HttpMethod.GET),
        GET_ALL_LANGUAGES(Pattern.compile("/languages"), HttpMethod.GET),
        GET_ALL_LANGUAGES_WITH_SCALES(Pattern.compile("/languages-scales"), HttpMethod.GET),
        POST_ADD_LANGUAGE(Pattern.compile("/languages"), HttpMethod.POST),
        PUT_UPDATE_LANGUAGE(Pattern.compile("/languages"), HttpMethod.PUT),
        DELETE_LANGUAGE(Pattern.compile("/languages/[\\wа-яА-ЯёЁ-]+"), HttpMethod.DELETE),

        DEFAULT(Pattern.compile(""), HttpMethod.GET);

        private final Pattern path;
        private final HttpMethod method;

        LanguageAction(Pattern path, HttpMethod method) {
            this.method = method;
            this.path = path;
        }

        public static LanguageAction resolve(String pattern, HttpMethod method) {
            for (LanguageAction action : LanguageAction.values()) {
                if (action.path.matcher(pattern).matches() && action.method == method) {
                    return action;
                }
            }
            return DEFAULT;
        }
    }
}
