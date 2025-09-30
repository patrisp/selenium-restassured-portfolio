package patrisp.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

abstract public class AbstractComponent<T extends AbstractComponent<T>> {
    protected WebDriver driver;
    protected WebDriverWait wait;
    // Side menu
    @FindBy(className = "oxd-main-menu-item")
    private List<WebElement> sideMenuOptions;
    // User menu dropdown
    @FindBy(className = "oxd-userdropdown")
    private WebElement userDropdownButton;
    @FindBy(xpath = "//a[@href=\"/web/index.php/auth/logout\"]")
    private WebElement logOutButton;
    // Top navigation link
    @FindBy(xpath = "//nav[@class=\"oxd-topbar-body-nav\"]/ul/li")
    private List<WebElement> topNavigationLinks;
    // Calendar modal
    @FindBy(className = "oxd-date-input")
    private WebElement calendarField;
    @FindBy(className = "oxd-date-input-calendar")
    private WebElement calendarPopup;
    @FindBy(xpath = "//div[@class=\"oxd-calendar-header\"]/button[1]")
    private WebElement calendarPreviousMonthButton;
    @FindBy(xpath = "//div[@class=\"oxd-calendar-header\"]/button[1]")
    private WebElement calendarNextMonthButton;
    @FindBy(className = "oxd-calendar-day")
    private List<WebElement> calendarWeekdayCell;
    @FindBy(xpath = "//div[@class=\"oxd-calendar-selector-month-selected\"]/p")
    private WebElement calendarCurrentMonth;
    @FindBy(xpath = "//div[@class=\"oxd-calendar-selector-year-selected\"]/p")
    private WebElement calendarCurrentYear;
    @FindBy(xpath = "//ul[@class=\"oxd-calendar-dropdown\"]/li")
    private List<WebElement> dropdownList;
    @FindBy(xpath = "//div[normalize-space(text())=\"Today\"]")
    private WebElement calendarTodayButton;
    @FindBy(xpath = "//div[normalize-space(text())=\"Clear\"]")
    private WebElement calendarClearButton;
    @FindBy(xpath = "//div[normalize-space(text())=\"Close\"]")
    private WebElement calendarCloseButton;
    @FindBy(css = "input[placeholder*='yyyy']")
    private WebElement selectedDateField;
    @FindBy(className = "oxd-calendar-date")
    private List<WebElement> calendarDayCell;
    @FindBy(css = ".oxd-calendar-date.--today")
    private WebElement calendarTodayCell;
    @FindBy(css = ".oxd-calendar-date.--selected")
    private WebElement calendarSelectedCell;
//    Alert
    @FindBy(css = ".oxd-toast-content .oxd-text--toast-title")
    private WebElement toastMessageTitle;
    @FindBy(css = ".oxd-toast-content .oxd-text--toast-message")
    private WebElement toastMessageContent;

    public AbstractComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        PageFactory.initElements(driver, this);
    }

    public T selectDate(String isoDate) {
        LocalDate parsed = LocalDate.parse(isoDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String year = String.valueOf(parsed.getYear());
        String month = parsed.format(DateTimeFormatter.ofPattern("MMMM", Locale.ENGLISH));
        String day = String.valueOf(parsed.getDayOfMonth());

        calendarField.click();
        calendarCurrentYear.click();
        dropdownList.stream()
                .filter(option -> option.getText().contains(year))
                .findFirst().orElseThrow().click();
        calendarCurrentMonth.click();
        dropdownList.stream()
                .filter(option -> option.getText().contains(month))
                .findFirst().orElseThrow().click();
        calendarDayCell.stream()
                .filter(WebElement::isDisplayed)
                .filter(cell -> cell.getText().contains(day))
                .findFirst().orElseThrow().click();
        return (T) this;
    }

    public T goToModule(String moduleName) {
        sideMenuOptions.stream()
                .filter(option -> option.getText().equals(moduleName))
                .findFirst()
                .ifPresent(WebElement::click);
        return (T) this;
    }


    public T goToTab(String tabName) {
        topNavigationLinks.stream()
                .filter(button -> button.getText().equals(tabName))
                .findFirst()
                .ifPresent(WebElement::click);
        return (T) this;
    }

    public String getToastMessageTitle() {
        return wait.until(d -> {
            String text = toastMessageTitle.getText();
            return text != null && !text.trim().isEmpty() ? text : null;
        });
    }

    public String getToastMessageContent() {
        return wait.until(d -> {
            String text = toastMessageContent.getText();
            return text != null && !text.trim().isEmpty() ? text : null;
        });
    }

    public String getDateOfApplication() {
        return selectedDateField.getAttribute("value");
    }

    public void logout() {
        userDropdownButton.click();
        logOutButton.click();
    }
}
