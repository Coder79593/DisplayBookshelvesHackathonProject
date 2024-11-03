package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

   
    static {
        try {
            FileInputStream input = new FileInputStream("ApplicationProperty/Configure.properties");
            properties = new Properties();
            properties.load(input);
        } catch (IOException e) {
            System.out.println("Error loading config file: " + e.getMessage());
        }
    }

   
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}