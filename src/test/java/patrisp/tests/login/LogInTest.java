package patrisp.tests.login;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import patrisp.pages.LoginPage;
import patrisp.readProperties.ConfigProvider;
import patrisp.tests.core.BaseTest;

public class LogInTest extends BaseTest {
    private LoginPage loginPage;
    @BeforeMethod
    public void testSetUp() {
        loginPage = new LoginPage(driver);
    }

    @Test
    public void logInCorrectCredentials() {
        loginPage.logIn(ConfigProvider.LOGIN, ConfigProvider.PASSWORD);
        wait.until(ExpectedConditions.urlContains("dashboard/index"));
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard/index"),
                "Expected to be redirected to the dashboard");
    }

    @Test
    public void logInWrongCredentials() {
        loginPage.logIn(ConfigProvider.LOGIN + "123", ConfigProvider.PASSWORD + "123");
        Assert.assertEquals(loginPage.getErrorContent(), "Invalid credentials", "Expected an 'Invalid credentials' error message");
    }
}
