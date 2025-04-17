package by.poskrobko.util;

import by.poskrobko.dto.UserDTO;
import by.poskrobko.dto.UserToRegisterDTO;
import by.poskrobko.model.Role;
import by.poskrobko.model.User;
import by.poskrobko.service.UserService;
import exception.NotExistingEntityException;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DBManager {
    private static final Properties applicationProperties = new Properties();

    private static final String DEFAULT_URL = "jdbc:sqlite:language_school.db";
    private static final String DEFAULT_SCHEMA = "schema.sql";
    private static final String DEFAULT_DROP = "drop.sql";
    private static final String DEFAULT_DB_FILE = "language_school.db";

    private static String url;
    private static String schema;
    private static String drop;
    private static String dbFileName;

    private static boolean createAdmin;
    private static String adminFirstName;
    private static String adminLastName;
    private static String adminEmail;
    private static String adminPassword;

    private static final SQLExecutor sqlExecutor = new SQLExecutor();
    private static final UserService userService = new UserService();

    static {
        try (InputStream is = DBManager.class.getClassLoader().getResourceAsStream("application.properties")) {
            applicationProperties.load(is);
            url = applicationProperties.getProperty("db.url", DEFAULT_URL);
            schema = applicationProperties.getProperty("db.schema", DEFAULT_SCHEMA);
            drop = applicationProperties.getProperty("db.drop", DEFAULT_DROP);
            dbFileName = applicationProperties.getProperty("db.filename", DEFAULT_DB_FILE);

            createAdmin = Boolean.parseBoolean(applicationProperties.getProperty("users.init.admin.create", "false"));
            adminFirstName = applicationProperties.getProperty("users.init.admin.firstname", "Admin");
            adminLastName = applicationProperties.getProperty("users.init.admin.lastname", "Admin");
            adminEmail = applicationProperties.getProperty("users.init.admin.email", "admin@admin.com");
            adminPassword = applicationProperties.getProperty("users.init.admin.password", "admin");
        } catch (IOException e) {
            System.err.println("Could not load application.properties. " + DEFAULT_URL + " is used");
            url = DEFAULT_URL;
            schema = DEFAULT_SCHEMA;
            drop = DEFAULT_DROP;
        }
    }

    private DBManager() {
    }

    public static void initDatabase() {
        List<String> queries = readSqlScripts(schema);
        executeMultiple(queries);
        System.out.println("Database schema has been created");
    }

    public static void dropDatabase() {
        List<String> queries = readSqlScripts(drop);
        executeMultiple(queries);
        System.out.println("Database has been successfully dropped");
    }

    public static void initProductionDatabase() {
        File file = new File(dbFileName);
        if (!file.exists()) {
            initDatabase();
        }
        if (createAdmin) {
            try {
                userService.findByEmail(adminEmail);
            } catch (NotExistingEntityException e) {
                var admin = new UserToRegisterDTO(adminFirstName, adminLastName, adminEmail, adminPassword, Set.of("USER", "ADMIN"), null);
                UserDTO userDTO = userService.registerUser(admin);
                userService.addAdminRights(userDTO.id());
            }
        }
    }

    protected static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    private static void executeMultiple(List<String> queries) {
        sqlExecutor.executeVoid(connection -> {
            Statement statement = connection.createStatement();
            for (String query : queries) {
                statement.addBatch(query);
            }
            statement.executeBatch();
        });
    }

    private static List<String> readSqlScripts(String path) {
        List<String> queries = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(DBManager.class.getClassLoader().getResourceAsStream(path))
                ))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                sqlBuilder.append(line).append("\n");
                if (line.trim().endsWith(";")) {
                    queries.add(sqlBuilder.toString());
                    sqlBuilder = new StringBuilder();
                }
            }
        } catch (IOException e) {
            System.err.println("Cannot read SQL file: " + e.getMessage());
        }
        return queries;
    }
}
