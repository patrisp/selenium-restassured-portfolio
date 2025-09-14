package patrisp.tests.login;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import patrisp.pages.PageProvider;
import patrisp.readProperties.ConfigProvider;
import patrisp.tests.core.BaseTest;

public class LogInTest extends BaseTest {
    private PageProvider pages;
    @BeforeClass
    public void testSetUp() {
        pages = new PageProvider(driver);
    }

    @Test
    public void logInCorrectCredentials() {
        pages.login().logIn(ConfigProvider.LOGIN, ConfigProvider.PASSWORD);
        wait.until(ExpectedConditions.urlContains("dashboard/index"));
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard/index"),
                "Expected to be redirected to the dashboard");
        pages.dashboard().logout();
    }

    @Test
    public void logInWrongCredentials() {
        pages.login().logIn(ConfigProvider.LOGIN + "123", ConfigProvider.PASSWORD + "123");
        Assert.assertEquals(pages.login().getErrorContent(), "Invalid credentials", "Expected an 'Invalid credentials' error message");
    }
}
