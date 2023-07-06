package org.dcuciuc.util;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

@Component
public class PropertiesReader {

    private final String PROPERTIES_FILE = "application.properties";
    private final Properties properties;

    public PropertiesReader() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE));
        } catch (IOException exception) {
            exception.getStackTrace();
        }
    }

    public String readProperty(String keyName) {
        return properties.getProperty(keyName, "Key name not found");
    }
}
