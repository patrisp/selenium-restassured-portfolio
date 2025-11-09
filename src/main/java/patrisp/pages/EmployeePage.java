package patrisp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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
    @FindBy(xpath = "//label[contains(text(), 'Other Id')]/parent::div/following-sibling::div/input")
    private WebElement otherIdField;
    @FindBy(xpath = "//label[contains(text(), \"Driver's License Number\")]/parent::div/following-sibling::div/input")
    private WebElement driversLicenseNumberField;
    @FindBy(xpath = "//label[contains(text(), 'License Expiry Date')]/parent::div/following-sibling::div//input")
    private WebElement driversLicenseExpiryDateField;
    @FindBy(xpath = "//label[contains(text(), 'Nationality')]/parent::div/following-sibling::div//div[@class='oxd-select-text-input']")
    private WebElement nationalityDropdownField;
    @FindBy(xpath = "//label[contains(text(), 'Marital Status')]/parent::div/following-sibling::div//div[@class='oxd-select-text-input']")
    private WebElement maritalStatusDropdownField;
    @FindBy(xpath = "//div[@role=\"option\"]/span")
    private List<WebElement> dropdownOptions;
    @FindBy(xpath = "//label[contains(text(), 'Date of Birth')]/parent::div/following-sibling::div//input")
    private WebElement dateOfBirthField;
    @FindBy(xpath = "//input[@value=\"1\"]/parent::label")
    private WebElement maleGenderRadioButton;
    @FindBy(xpath = "//input[@value=\"2\"]/parent::label")
    private WebElement femaleGenderRadioButton;
    @FindBy(xpath = "//input[@type=\"file\"]")
    private WebElement addEmployeePhotoButton;
    @FindBy(className = "employee-image")
    private WebElement employeeProfilePicture;
    @FindBy(xpath = "//input[@type=\"checkbox\"]")
    private WebElement createLoginDetailsSwitch;
    @FindBy(xpath = "//button[@type=\"submit\"]")
    private WebElement addEmployeeButton;
    @FindBy(xpath = "//div[@class=\"orangehrm-horizontal-padding orangehrm-vertical-padding\"]//button[@type=\"submit\"]")
    private WebElement savePersonalDetailsChanges;
    @FindBy(xpath = "//div[@class=\"orangehrm-custom-fields\"]//button[@type=\"submit\"]")
    private WebElement saveCustomFieldsChanges;
    @FindBy(className = "orangehrm-edit-employee-name")
    private WebElement employeeNameHeader;

    public EmployeePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public EmployeePage setEmployeeFullName(String firstName, String middleName, String lastName) {
        firstNameField.sendKeys(Keys.CONTROL + "a");
        firstNameField.sendKeys(Keys.DELETE);
        firstNameField.sendKeys(firstName);
        middleNameField.sendKeys(Keys.CONTROL + "a");
        middleNameField.sendKeys(Keys.DELETE);
        middleNameField.sendKeys(middleName);
        lastNameField.sendKeys(Keys.CONTROL + "a");
        lastNameField.sendKeys(Keys.DELETE);
        lastNameField.sendKeys(lastName);
        return this;
    }

    public EmployeePage setEmployeeId(String id) {
        employeeIdField.clear();
        employeeIdField.sendKeys(id);
        return this;
    }

    public EmployeePage setOtherId(String otherId) {
        otherIdField.clear();
        otherIdField.sendKeys(otherId);
        return this;
    }

    public EmployeePage setDriversLicenceNumber(String number) {
        driversLicenseNumberField.clear();
        driversLicenseNumberField.sendKeys(number);
        return this;
    }

    public EmployeePage setDriversLicenceExpiryDate(String date) {
        selectDate(driversLicenseExpiryDateField, date);
        return this;
    }

    public EmployeePage selectNationality(String nationality) {
        nationalityDropdownField.click();
        dropdownOptions.stream()
                .filter(option -> option.getText().contains(nationality))
                .findFirst()
                .ifPresent(WebElement::click);
        return this;
    }

    public EmployeePage setDateOfBirth(String date) {
        selectDate(dateOfBirthField, date);
        return this;
    }

    public EmployeePage selectGender(String gender) {
        String genderId;
        if(gender.equalsIgnoreCase("male")) {
            maleGenderRadioButton.click();
        } else if(gender.equalsIgnoreCase("female")) {
            femaleGenderRadioButton.click();
        } else {
            throw new IllegalArgumentException("Incorrect method parameter");
        }
        return this;
    }

    public EmployeePage selectMaritalStatus(String status) {
        maritalStatusDropdownField.click();
        dropdownOptions.stream()
                .filter(option -> option.getText().contains(status))
                .findFirst()
                .ifPresent(WebElement::click);
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

    public void submitEmployee() {
        addEmployeeButton.click();
    }

    public void savePersonalDetailsChanges() {
        savePersonalDetailsChanges.click();
    }

    public void saveCustomFieldsChanges() {
        saveCustomFieldsChanges.click();
    }

    public String getEmployeeId() {
       return employeeIdField.getAttribute("value");
    }

    public String getOtherId() {
        return otherIdField.getAttribute("value");
    }

    public String getDriversLicenseNumber() {
        return driversLicenseNumberField.getAttribute("value");
    }

    public String getDriversLicenseExpiryDate()  {
        return driversLicenseExpiryDateField.getAttribute("value");
    }

    public String getNationality() {
        return nationalityDropdownField.getAttribute("value");
    }

    public String getMaritalStatus() {
        return maritalStatusDropdownField.getAttribute("value");
    }

    public String getGender() {
        String genderId = driver.findElement(By.cssSelector("input[type='radio']:checked")).getAttribute("value");
        String gender = null;
        if (genderId.equals("1")) {
            gender = "male";
        } else if (genderId.equals("2")) {
            gender = "female";
        }
        return gender;
    }

    public String getDateOfBirth() {
        return dateOfBirthField.getAttribute("value");
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
