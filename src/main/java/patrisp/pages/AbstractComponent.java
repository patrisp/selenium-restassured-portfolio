package patrisp.pages;

import org.openqa.selenium.WebDriver;

abstract public class AbstractComponent {
    protected WebDriver driver;

    public AbstractComponent(WebDriver driver) {
        this.driver = driver;
    }
}
