package patrisp.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class EmployeePage extends AbstractComponent<EmployeePage>{
    @FindBy(xpath = "//input[@name=\"firstName\"]")
    private WebElement firstNameField;
    @FindBy(xpath = "//input[@name=\"middleName\"]")
    private WebElement middleNameField;
    @FindBy(xpath = "//input[@name=\"lastName\"]")
    private WebElement lastNameField;
    @FindBy(xpath = "//label[contains(text(), 'Employee Id')]/parent::div/following-sibling::div/input")
    private WebElement employeeIdField;
    @FindBy(xpath = "//input[@type=\"file\"]")
    private WebElement addEmployeePhotoButton;
    @FindBy(className = "employee-image")
    private WebElement employeeProfilePicture;
    @FindBy(xpath = "//input[@type=\"checkbox\"]")
    private WebElement createLoginDetailsSwitch;
    @FindBy(xpath = "//button[@type=\"submit\"]")
    private WebElement submitButton;
    @FindBy(className = "orangehrm-edit-employee-name")
    private WebElement employeeNameHeader;

    public EmployeePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public EmployeePage addEmployeeFullName(String firstName, String middleName, String lastName) {
        firstNameField.sendKeys(firstName);
        middleNameField.sendKeys(middleName);
        lastNameField.sendKeys(lastName);
        return this;
    }

    public EmployeePage addEmployeeId(String id) {
        employeeIdField.clear();
        employeeIdField.sendKeys(id);
        return this;
    }

    public EmployeePage addProfilePicture(String filepath) throws URISyntaxException {
        Path path = Paths.get(
                Objects.requireNonNull(
                        getClass().getClassLoader().getResource(filepath)
                ).toURI()
        );
        addEmployeePhotoButton.sendKeys(path.toString());
        return this;
    }

    public String getId() {
       return employeeIdField.getAttribute("value");
    }

    public void submitEmployee() {
        submitButton.click();
    }

    public String getEmployeeHeaderName() {
        return employeeNameHeader.getText();
    }

    public String getEmployeeFullName() {
        return firstNameField.getAttribute("value") + " " + middleNameField.getAttribute("value") + " " + lastNameField.getAttribute("value");
    }

    public int getProfilePictureWidth() {
        return employeeProfilePicture.getSize().getWidth();
    }

    public int getProfilePictureHeight() {
        return employeeProfilePicture.getSize().getHeight();
    }

}
