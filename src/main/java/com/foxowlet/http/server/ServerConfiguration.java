package com.foxowlet.http.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServerConfiguration {
    private static final Properties DEFAULT_PROPERTIES = new Properties();
    private final Properties properties;

    static {
        DEFAULT_PROPERTIES.put("server.port", "80");
        DEFAULT_PROPERTIES.put("server.num-threads", "10");
    }

    public ServerConfiguration() {
        this(DEFAULT_PROPERTIES);
    }

    public ServerConfiguration(String configFile) {
        this(loadProperties(configFile));
    }

    public ServerConfiguration(Properties properties) {
        this.properties = properties;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    private static Properties loadProperties(String configFile) {
        try (InputStream resource = ServerConfiguration.class.getClassLoader().getResourceAsStream(configFile)) {
            Properties properties = new Properties(DEFAULT_PROPERTIES);
            properties.load(resource);
            return properties;
        } catch (IOException e) {
            throw new IllegalStateException("Can't read server properties", e);
        }
    }
}
