package base;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public abstract class BasePage {
    protected TranslationHelper translationHelper;
    public BasePage(TranslationHelper translationHelper){
        this.translationHelper = translationHelper;
    }

    public static void runJS(String script, Object... args) {
        executeJavaScript(script, args);
    }

}


