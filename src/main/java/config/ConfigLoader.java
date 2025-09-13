package config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ConfigLoader {

    public static ModuleConfig loadConfig(String path) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(path), ModuleConfig.class);
        } catch (IOException e) {
            throw new RuntimeException("Could not load config file: " + path, e);
        }
    }
}