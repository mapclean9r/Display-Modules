package config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class ConfigLoader {
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static Config loadConfig(String resourcePath) {
        try (InputStream in = open(resourcePath)) {
            return MAPPER.readValue(in, Config.class);
        } catch (Exception e) {
            throw new RuntimeException("Could not load combined config: " + resourcePath, e);
        }
    }

    private static InputStream open(String resourcePath) {
        InputStream in = ConfigLoader.class.getResourceAsStream(resourcePath);
        if (in == null) {
            throw new IllegalArgumentException("Resource not found: " + resourcePath);
        }
        return in;
    }
}