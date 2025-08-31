package patrisp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class CandidatePage extends AbstractComponent<CandidatePage>{
    @FindBy(xpath = "//input[@name=\"firstName\"]")
    private WebElement firstNameField;
    @FindBy(xpath = "//input[@name=\"middleName\"]")
    private WebElement middleNameField;
    @FindBy(xpath = "//input[@name=\"lastName\"]")
    private WebElement lastNameField;
    @FindBy(css = ".oxd-select-text")
    private WebElement vacancyField;
    @FindBy(css = ".oxd-select-text-input")
    private WebElement vacancyNameInput;
    @FindBy(xpath = "//div[@role=\"option\"]/span")
    private List<WebElement> vacancyOptions;
    @FindBy(xpath = "//label[normalize-space(text())=\"Email\"]/../following-sibling::*/input")
    private WebElement emailField;
    @FindBy(xpath = "//label[normalize-space(text())=\"Contact Number\"]/../following-sibling::*/input")
    private WebElement contactNumberField;
    @FindBy(xpath = "//label[normalize-space(text())=\"Keywords\"]/../following-sibling::*/input")
    private WebElement keywordsField;
    @FindBy(xpath = "//label[normalize-space(text())=\"Notes\"]/../following-sibling::*/textarea")
    private WebElement notesField;
    @FindBy(xpath = "//input[@type=\"file\"]")
    private WebElement addResumeInput;
    @FindBy(css = "span.oxd-checkbox-input")
    private WebElement dataConsentCheckbox;
    @FindBy(xpath = "//button[@type=\"submit\"]")
    private WebElement submitButton;
    @FindBy(xpath = "(//h6[text()='Application Stage']//following-sibling::div[1]//p)[1]")
    private WebElement candidateFullNameInfo;
    @FindBy(xpath = "(//h6[text()='Application Stage']//following-sibling::div[1]//p)[2]")
    private WebElement vacancyNameInfo;
    @FindBy(xpath = "(//h6[text()='Application Stage']//following-sibling::div[1]//p)[3]")
    private WebElement hiringManagerInfo;
    @FindBy(xpath = "//div[@class=\"orangehrm-recruitment-status\"]/p")
    private WebElement applicationStatusInfo;
    @FindBy(css = ".orangehrm-file-name")
    private WebElement resumeFileNameInfo;
    @FindBy(xpath = "(//div[@class=\"oxd-table-card\"])//div[@role=\"cell\"][1]/div")
    private List<WebElement> activityLogDateCell;
    @FindBy(xpath = "(//div[@class=\"oxd-table-card\"])//div[@role=\"cell\"][2]/div")
    private List<WebElement> activityLogActionCell;

    @FindBy(xpath = "//div[@class=\"orangehrm-recruitment-actions\"]/button[contains(@class, \"oxd-button--danger\")]")
    private WebElement rejectButton;

    public CandidatePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public CandidatePage addCandidateName(String firstName, String middleName, String lastName) {
        firstNameField.sendKeys(firstName);
        middleNameField.sendKeys(middleName);
        lastNameField.sendKeys(lastName);
        return this;
    }

    public CandidatePage selectVacancy(String vacancyName) {
        vacancyField.click();
        vacancyOptions.stream()
                .filter(option -> option.getText().contains(vacancyName))
                .findFirst()
                .ifPresent(WebElement::click);
        return this;
    }

    public CandidatePage addEmailAddress(String emailAddress) {
        emailField.sendKeys(emailAddress);
        return this;
    }

    public CandidatePage addContactNumber(String contactNumber) {
        contactNumberField.sendKeys(contactNumber);
        return this;
    }

    public CandidatePage addKeywords(String[] keywords) {
        for(String keyword : keywords) {
            keywordsField.sendKeys(keyword + ",");
        }
        return this;
    }

    public CandidatePage addNotes(String notes) {
        notesField.sendKeys(notes);
        return this;
    }

    public CandidatePage allowDataConsent() {
        dataConsentCheckbox.click();
        return this;
    }

    public CandidatePage uploadResume(String filePath) throws URISyntaxException {
        Path path = Paths.get(
                Objects.requireNonNull(
                        getClass().getClassLoader().getResource(filePath)
                ).toURI()
        );
        addResumeInput.sendKeys(path.toString());
        return this;
    }

    public CandidatePage submitCandidate() {
        submitButton.click();
        return this;
    }

    public String getCandidateFirstName() {
        return firstNameField.getAttribute("value");
    }

    public String getCandidateMiddleName() {
        return middleNameField.getAttribute("value");
    }

    public String getCandidateLastName() {
        return lastNameField.getAttribute("value");
    }

    public String getVacancyName() {
        return vacancyNameInput.getText();
    }

    public String getEmail() {
        return emailField.getAttribute("value");
    }

    public String getContactNumber() {
        return contactNumberField.getAttribute("value");
    }

    public String[] getKeywords() {
        return keywordsField.getAttribute("value").split(",\\s*");
    }

    public String getNotes() {
        return notesField.getAttribute("value");
    }

    public boolean isDataConsentGiven() {
        return !driver.findElements(By.cssSelector("input[type='checkbox']:checked")).isEmpty();
    }

    public String getCandidateFullName() {
        return candidateFullNameInfo.getText();
    }

    public String getVacancyDetails() {
        return vacancyNameInfo.getText();
    }

    public String getHiringManagerName() {
        return hiringManagerInfo.getText();
    }

    public String getApplicationStatus() {
        return applicationStatusInfo.getText();
    }

    public String getResumeName() {
        return resumeFileNameInfo.getText();
    }

    public String getActivityDate(int index) {
        return activityLogDateCell.get(index).getText();
    }

    public String getActivityContent(int index) {
        return activityLogActionCell.get(index).getText();
    }

    public void rejectCandidate() {
        rejectButton.click();
        submitButton.click();
    }
}
