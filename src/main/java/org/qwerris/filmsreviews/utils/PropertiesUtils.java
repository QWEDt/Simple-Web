package org.qwerris.filmsreviews.utils;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.util.Properties;

@UtilityClass
public class PropertiesUtils {
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void loadProperties() {
        try {
            PROPERTIES.load(
                    PropertiesUtils.class.getClassLoader()
                            .getResourceAsStream("application.properties")
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
