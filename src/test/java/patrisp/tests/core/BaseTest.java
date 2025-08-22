package patrisp.tests.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import patrisp.api.requestmethod.LoginRequests;
import patrisp.readProperties.ConfigProvider;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public abstract class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    public static String authCookieValue;

    @BeforeMethod
    public void setup(Method method) {
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
        if (method.getDeclaringClass().getSimpleName().equals("LogInTest")) {
            return;
        }
        if (authCookieValue == null) {
            LoginRequests loginRequest = new LoginRequests();
            authCookieValue = loginRequest.getAuthCookie(
                    ConfigProvider.LOGIN,
                    ConfigProvider.PASSWORD
            );
        }
        driver.manage().deleteCookieNamed("orangehrm");
        Cookie authenticatedSession = new Cookie.Builder("orangehrm", authCookieValue)
                .domain("opensource-demo.orangehrmlive.com")
                .path("/")
                .isHttpOnly(true)
                .build();
        driver.manage().addCookie(authenticatedSession);
        if (driver.manage().getCookieNamed("orangehrm") == null) {
            throw new IllegalStateException("Auth cookie was not added to the browser");
        }
        driver.navigate().refresh();
        wait.until(ExpectedConditions.urlContains("dashboard/index"));
    }

    @AfterMethod
    public void teardown() {
        driver.close();
        driver.quit();
    }

}
