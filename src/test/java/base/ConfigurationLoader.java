package base;

import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

public class ConfigurationLoader {
    private static Properties properties;

    static { properties = new Properties();
        try (InputStream input = ConfigurationLoader.class.getClassLoader().getResourceAsStream("config/config.properties")) {
            if (input == null) {
                throw new RuntimeException("Sorry, unable to find config.properties");
            } properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

}
