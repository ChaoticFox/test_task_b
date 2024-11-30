package pageobjects;

import base.BasePage;
import base.TranslationHelper;
import com.codeborne.selenide.Selectors;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class MainPage extends BasePage {

    public MainPage(TranslationHelper translationHelper){
        super(translationHelper);
    }
    public void clickMenuOption(String itemText){
        $(byText(itemText)).click();
    }
}
