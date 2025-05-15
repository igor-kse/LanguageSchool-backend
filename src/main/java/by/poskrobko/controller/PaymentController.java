package by.poskrobko.controller;

import by.poskrobko.dto.PaymentDTO;
import by.poskrobko.dto.UserDTO;
import by.poskrobko.dto.UserToRegisterDTO;
import by.poskrobko.service.PaymentService;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class PaymentController extends BaseController {

    private final PaymentService paymentService = new PaymentService();

    @Override
    public void handle(HttpExchange exchange) {
        super.handle(exchange);
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        handleRequest(exchange, () -> {
            PaymentAction action = PaymentAction.resolve(path, HttpMethod.valueOf(method));
            switch (action) {
                case GET_ALL_PAYMENTS -> {
                    System.out.println("Get all payments: " + action);
                    List<PaymentDTO> payments = paymentService.getAll();
                    sendJson(exchange, 200, payments);
                }
                case POST_REGISTER_PAYMENT -> {
                    System.out.println("Post register payment: " + action);
                    PaymentDTO dto = jsonMapper.readValue(exchange.getRequestBody(), PaymentDTO.class);
                    String id = paymentService.add(dto.user(), dto.amount(), dto.date(), dto.description());
                    sendJson(exchange, 201, paymentService.getById(id));
                }
                case PUT_UPDATE_PAYMENT -> {
                    System.out.println("Put update payment: " + action);
                    PaymentDTO dto = jsonMapper.readValue(exchange.getRequestBody(), PaymentDTO.class);
                    paymentService.update(dto);
                    sendNoContent(exchange);
                }
                case DELETE_PAYMENT -> {
                    System.out.println("Delete payment: " + action);
                    String id = path.substring("/payments/".length());
                    paymentService.delete(id);
                    sendNoContent(exchange);
                }
            }
        });
    }

    private enum PaymentAction {
        GET_ALL_PAYMENTS(Pattern.compile("/payments"), HttpMethod.GET),
        POST_REGISTER_PAYMENT(Pattern.compile("/payments"), HttpMethod.POST),
        PUT_UPDATE_PAYMENT(Pattern.compile("/payments"), HttpMethod.PUT),
        DELETE_PAYMENT(Pattern.compile("/payments/[\\w-]+"), HttpMethod.DELETE),

        DEFAULT(Pattern.compile(""), HttpMethod.DEFAULT);

        private final Pattern path;
        private final HttpMethod method;

        PaymentAction(Pattern path, HttpMethod method) {
            this.path = path;
            this.method = method;
        }

        public static PaymentAction resolve(String pattern, HttpMethod method) {
            for (PaymentAction action : PaymentAction.values()) {
                if (action.path.matcher(pattern).matches() && action.method == method) {
                    return action;
                }
            }
            return DEFAULT;
        }
    }
}
