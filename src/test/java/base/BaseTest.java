package base;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public abstract class BaseTest {

    protected WebDriver driver;
    protected TranslationHelper translationHelper;

    @BeforeEach
    public void setUp() {
        Configuration.timeout = 12000;
        Configuration.baseUrl = "https://www.bta.lv";
        Configuration.browser = System.getenv("BROWSER") != null ? System.getenv("BROWSER") : "chrome"; //set BROWSER=chrome
        Configuration.browserSize = "1920x1080";
        ChromeOptions options = new ChromeOptions();
        //driver = createWebDriver(Configuration.browser, options);
        System.setProperty("webdriver.chrome.driver", "src/test/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        WebDriverRunner.setWebDriver(driver);
        WebDriverRunner.getWebDriver().manage().window().maximize();
        String languageCode = System.getenv("LANG_CODE"); //set LANG_CODE=lv in shell
        if (languageCode == null || languageCode.isEmpty()) {
            languageCode = "lv"; // default to Latvian if the environment variable is not set }
        }
        translationHelper = new TranslationHelper(languageCode);
        String siteUrl = getSiteUrl(languageCode);
        Selenide.open(siteUrl);

        //dismiss cookies
        if($(".cookies-notification").isDisplayed()){
            $$(".cookies-notification button").last().click();
        }

    }

    @AfterEach
    public void finalTearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private static WebDriver createWebDriver(String browser, ChromeOptions options) {
        switch (browser) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
                return new ChromeDriver(options);
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
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

    public boolean isPageOpened(String url) {
        String currentUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
        return currentUrl.equals(url);
    }
/*
    @BeforeMethod
    public void setUpTest(ITestResult result) {
        //ExtentReportListener.createTest(result.getName());
    }

    @AfterMethod
    public void tearDown() {
        //ExtentReportListener.flush();
    }*/
}
