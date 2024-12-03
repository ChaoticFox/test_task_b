package base;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class TranslationHelper {
    private final Properties properties;

    public TranslationHelper(String languageCode) {
        properties = new Properties();
        String fileName = "translations/strings_" + languageCode + ".properties";
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName);
             InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
            if (input==null) {
                throw new RuntimeException("Sorry, unable to find " + fileName);
            }
            properties.load(reader);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getTranslation(String key) {
        return properties.getProperty(key);
    }

}

