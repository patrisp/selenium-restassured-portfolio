package patrisp.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ViewVacanciesPage extends AbstractComponent{
    @FindBy(xpath = "//div[@class=\"orangehrm-header-container\"]/button")
    private WebElement addVacancyButton;

    public ViewVacanciesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void addVacancy() {
        addVacancyButton.click();
    }
}
