package patrisp.tests.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import patrisp.pages.PageProvider;
import patrisp.readProperties.ConfigProvider;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public abstract class BaseTest {
    protected static WebDriver driver;
    protected WebDriverWait wait;
    private PageProvider pages;


    @BeforeClass
    public void setDriver() {
        String browserName = ConfigProvider.BROWSER;

        if (browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }

        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.of(30, ChronoUnit.SECONDS));
        driver.manage().timeouts().implicitlyWait(Duration.of(10, ChronoUnit.SECONDS));

        driver.get(ConfigProvider.URL);
        if (this.getClass().getSimpleName().equals("LogInTest")) {
            return;
        }
        pages = new PageProvider(driver);
        pages.login().logIn(ConfigProvider.LOGIN, ConfigProvider.PASSWORD);
        wait.until(ExpectedConditions.urlContains("dashboard/index"));
        RestAssured.reset();
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit(); // quit once after all tests in the class
        }
    }


}
