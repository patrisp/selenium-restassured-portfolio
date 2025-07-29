package patrisp.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends AbstractComponent{
    @FindBy(xpath = "//input[@name=\"username\"]")
    private WebElement usernameInput;

    @FindBy(xpath = "//input[@name=\"password\"]")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@type=\"submit\"]")
    private WebElement loginButton;

    @FindBy(xpath = "//div[@class=\"oxd-alert-content oxd-alert-content--error\"]/p")
    private WebElement alertContent;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public LoginPage logIn(String username, String password) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginButton.click();
        return this;
    }

    public String getErrorContent() {
        return alertContent.getText();
    }



}
