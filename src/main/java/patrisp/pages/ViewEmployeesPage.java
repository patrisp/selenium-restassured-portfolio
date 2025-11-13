package patrisp.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.security.Key;
import java.util.List;

public class ViewEmployeesPage extends AbstractComponent<ViewEmployeesPage>{
    @FindBy(xpath = "//label[contains(text(), 'Employee Id')]/parent::div/following-sibling::div/input")
    private WebElement employeeIdSearchInput;
    @FindBy(xpath = "//div[@class=\"oxd-form-actions\"]/button[@type=\"submit\"]")
    private WebElement searchButton;
    @FindBy(xpath = "//div[@class=\"orangehrm-header-container\"]/button")
    private WebElement addEmployeeButton;
    @FindBy(css = "button.oxd-button--label-danger")
    private WebElement deleteEmployeeConfirmationButton;
    @FindBy(className = "oxd-table-card")
    private List<WebElement> employeeRow;
    public ViewEmployeesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void openEmployeeCreationPage() {
        addEmployeeButton.click();
    }

    public ViewEmployeesPage typeEmployeeId(String employeeId) {
        employeeIdSearchInput.sendKeys(Keys.CONTROL, "a");
        employeeIdSearchInput.sendKeys(Keys.DELETE);
        employeeIdSearchInput.sendKeys(employeeId);
        return this;
    }

    public ViewEmployeesPage findEmployee() {
        searchButton.click();
        return this;
    }

    public void deleteEmployee(String employeeId) {
        WebElement targetRow = employeeRow.stream()
                .filter(row -> row.getText().contains(employeeId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No employee row found containing this employee ID: " + employeeId));

        WebElement deleteButton = targetRow.findElement(By.xpath(".//button[i[@class='oxd-icon bi-trash']]"));
        deleteButton.click();

        deleteEmployeeConfirmationButton.click();
    }

    public long getNumberOfEmployeeRows() {
        return employeeRow.stream()
                .filter(WebElement::isDisplayed)
                .count();
    }
}
