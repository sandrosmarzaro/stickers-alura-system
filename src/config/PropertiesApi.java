package config;

import exceptions.LoadPropertiesException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesApi {

    public Properties load() {
        final String fileName = "src/resources/apiKeys.properties";
        try (FileInputStream fileInputStream = new FileInputStream(fileName)){
            Properties properties = new Properties();
            properties.load(fileInputStream);
            return properties;
        } catch (IOException e) {
            throw new LoadPropertiesException("Error loading properties file");
        }
    }
}
