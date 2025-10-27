package patrisp.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ViewEmployeesPage extends AbstractComponent<ViewEmployeesPage>{
    @FindBy(xpath = "//div[@class=\"orangehrm-header-container\"]/button")
    private WebElement addEmployeeButton;

    public ViewEmployeesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void openEmployeeCreationPage() {
        addEmployeeButton.click();
    }
}
