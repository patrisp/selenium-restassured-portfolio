package patrisp.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class VacancyPage extends AbstractComponent{
    @FindBy(xpath = "//label[text()=\"Vacancy Name\"]/ancestor::div[@class=\"oxd-input-group oxd-input-field-bottom-space\"]//input")
    private WebElement vacancyNameField;
    @FindBy(css = ".oxd-select-text")
    private WebElement jobTitleField;
    @FindBy(xpath = "//div[@role=\"option\"]/span")
    private List<WebElement> jobOptions;
    @FindBy(xpath = "//textarea[@placeholder=\"Type description here\"]")
    private WebElement descriptionField;
    @FindBy(xpath = "//input[@placeholder=\"Type for hints...\"]")
    private WebElement hiringManagerField;
    @FindBy(xpath = "//div[@role=\"option\"]/span")
    private List<WebElement> hiringManagerOptions;
    @FindBy(xpath = "//label[text()=\"Number of Positions\"]/ancestor::div[@class=\"oxd-input-group oxd-input-field-bottom-space\"]//input")
    private WebElement numberOfPositionsField;
    @FindBy(xpath = "(//label/span)[1]")
    private WebElement statusSwitch;
    @FindBy(xpath = "(//label/span)[2]")
    private WebElement rssSwitch;
    @FindBy(xpath = "//button[@type=\"submit\"]")
    private WebElement saveButton;

    public VacancyPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public VacancyPage addVacancyName(String name) {
        vacancyNameField.sendKeys(name);
        return this;
    }

    public VacancyPage selectJobTitle(String jobTitle) {
        jobTitleField.click();
        jobOptions.stream()
                .filter(option -> option.getText().contains(jobTitle))
                .findFirst()
                .ifPresent(WebElement::click);
        return this;
    }

    public VacancyPage addDescription(String description) {
        descriptionField.sendKeys(description);
        return this;
    }

    public VacancyPage selectHiringManager(String hiringManagerName) {
        hiringManagerField.sendKeys(hiringManagerName);
        hiringManagerOptions.stream()
                .filter(option -> option.getText().contains(hiringManagerName))
                .findFirst()
                .ifPresent(WebElement::click);
        return this;
    }

    public VacancyPage setNumberOfPositions(String numberOfPositions) {
        numberOfPositionsField.sendKeys(numberOfPositions);
        return this;
    }

    public void saveChanges() {
        saveButton.click();
    }

    public String getVacancyName() {
        return vacancyNameField.getText();
    }

    public String getJobTitle() {
        return jobTitleField.getText();
    }

    public String getDescription() {
        return descriptionField.getText();
    }

    public String getHiringManagerName() {
        return hiringManagerField.getText();
    }

    public String getNumberOfPositions() {
        return numberOfPositionsField.getText();
    }
}
