package by.poskrobko;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

public class ApplicationConfig {
    private static final Properties props = new Properties();

    static {
        try (InputStream in = Application.class.getResourceAsStream("/application.properties")) {
            props.load(new InputStreamReader(Objects.requireNonNull(in), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить config.properties", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}
