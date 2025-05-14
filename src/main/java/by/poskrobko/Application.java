package by.poskrobko;

import by.poskrobko.controller.*;
import by.poskrobko.util.DBManager;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Application {

    private static final int PORT = ApplicationConfig.getInt("server.port");
    private static final int THREAD_POOL = ApplicationConfig.getInt("server.thread.pool");

    public static void main(String[] args) throws IOException {

        DBManager.initProductionDatabase();

        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        server.createContext("/users", new UserController());
        server.createContext("/languages", new LanguageController());
        server.createContext("/teachers", new TeacherController());
        server.createContext("/students", new StudentsController());
        server.createContext("/schedule", new ScheduleController());
        server.createContext("/payments", new PaymentController());
        server.createContext("/scales", new LanguageScaleController());
        server.createContext("/groups", new GroupController());
        server.createContext("/auth/login", new AuthController());

        server.setExecutor(Executors.newFixedThreadPool(THREAD_POOL));

        server.start();
        System.out.println("Server started on port: " + PORT);
    }
}
