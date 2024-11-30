package base;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TranslationHelper {
    private Properties properties;

    public TranslationHelper(String languageCode){
        properties = new Properties();
        String fileName = "config/strings_"+languageCode+".properties";
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new RuntimeException("Sorry, unable to find " + fileName);
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getTranslation(String key){
        return properties.getProperty(key);
    }

}

