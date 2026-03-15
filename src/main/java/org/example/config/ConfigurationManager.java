package org.example.config;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.time.Duration;
import java.util.Map;

/// Manages the application's configuration.
public enum ConfigurationManager {
    INSTANCE;

    private static final String CONFIG_FILE = "application-config.yml";
    private final AppConfig config;

    ConfigurationManager() {
        try (InputStream inputStream =
                     getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {

            if (inputStream == null) {
                throw new RuntimeException(String.format("%s not found in classpath", CONFIG_FILE));
            }

            LoaderOptions options = new LoaderOptions();
            Yaml yaml = new Yaml(new Constructor(AppConfig.class, options));
            this.config = yaml.load(inputStream);

            overrideWithSystemProperties();

        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    private void overrideWithSystemProperties() {
        if (config == null) return;

        String baseUrl = System.getProperty("baseUrl");
        if (baseUrl != null) {
            config.setBaseUrl(baseUrl);
        }

        String userEmail = System.getenv("email");
        if (userEmail != null) {
            config.setEmail(userEmail);
        }

        String userPassword = System.getenv("password");
        if (userPassword != null) {
            config.setPassword(userPassword);
        }

    }

    public AppConfig getConfig() {
        return config;
    }
}
