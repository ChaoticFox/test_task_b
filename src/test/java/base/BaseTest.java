package base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Selenide.*;

public abstract class BaseTest {

    protected WebDriver driver;
    protected TranslationHelper translationHelper;

    private static WebDriver createWebDriver(String browser) {
        switch (browser) {
            case "chrome":
                ChromeOptions options = new ChromeOptions();
                System.setProperty("webdriver.chrome.driver", "src/test/drivers/chromedriver.exe");
                return new ChromeDriver(options);
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    @BeforeEach
    public void setUp() {
        String languageCode = System.getenv("LANG_CODE"); //set LANG_CODE=lv in shell
        if (languageCode==null || languageCode.isEmpty()) {
            languageCode = "lv"; // default to Latvian if the environment variable is not set
        }
        Configuration.timeout = 12000;
        Configuration.baseUrl = getSiteUrl(languageCode);
        Configuration.browser = System.getenv("BROWSER")!=null ? System.getenv("BROWSER"):"chrome"; //set BROWSER=chrome
        Configuration.browserSize = "1920x1080";
        driver = createWebDriver(Configuration.browser);
        WebDriverRunner.setWebDriver(driver);
        WebDriverRunner.getWebDriver().manage().window().maximize();
        translationHelper = new TranslationHelper(languageCode);
        open(Configuration.baseUrl);
        //dismiss cookies
        if ($(".cookies-notification").isDisplayed()) {
            $$(".cookies-notification button").last().click();
        }

    }

    @AfterEach
    public void finalTearDown() {
        if (driver!=null) {
            driver.quit();
        }
    }

    private String getSiteUrl(String languageCode) {
        switch (languageCode) {// Latvian site version
            case "en":
                return "https://www.bta.lv/en";
            case "lv":
            default:
                return "https://www.bta.lv";
        }
    }
}
