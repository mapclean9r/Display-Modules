package config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class ConfigLoader {

    public static ModuleConfig loadConfig(String resourcePath) {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream in = ConfigLoader.class.getResourceAsStream(resourcePath)){
            return mapper.readValue(in, ModuleConfig.class);
        } catch (IOException e) {
            throw new RuntimeException("Could not load config file: " + resourcePath, e);
        }
    }

    public static LayoutConfig loadLayout(String resourcePath) {
        try (InputStream in = ConfigLoader.class.getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new IllegalArgumentException("Resource not found: " + resourcePath);
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(in, LayoutConfig.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load layout config", e);
        }
    }
}