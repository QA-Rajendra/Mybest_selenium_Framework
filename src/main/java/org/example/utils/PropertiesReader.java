package org.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * A utility class to read .properties files from resources.
 * Supports fail-fast reading for mandatory keys.
 */
public class PropertiesReader {

    private static final Properties properties = new Properties();

    static {
        // Default file load at startup
        loadProperties("data.properties");
    }

    /**
     * Load a given .properties file from resources.
     * @param fileName Name of the properties file (must be in src/main/resources)
     */
    public static void loadProperties(String fileName) {
        try (InputStream inputStream =
                     PropertiesReader.class.getClassLoader().getResourceAsStream(fileName)) {

            if (inputStream == null) {
                throw new RuntimeException("❌ Properties file not found in resources: " + fileName);
            }

            properties.clear();
            properties.load(inputStream);

        } catch (IOException e) {
            throw new RuntimeException("❌ Failed to load properties file: " + fileName, e);
        }
    }

    /**
     * Read a key value from the loaded properties.
     * @param key property name
     * @return value or null if not found
     */
    public static String readKey(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            System.out.println("⚠️ Key not found: " + key);
        }
        return value;
    }

    /**
     * Read a key and fail if it's missing or empty.
     * @param key property name
     * @return value (never null or empty)
     */
    public static String readRequiredKey(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException("❌ Missing required property: " + key);
        }
        return value.trim();
    }
}
