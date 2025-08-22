package patrisp.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ViewCandidatesPage extends AbstractComponent{
    @FindBy(xpath = "//div[@class=\"orangehrm-header-container\"]/button")
    private WebElement addCandidateButton;

    public ViewCandidatesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    public void addCandidate() {
        addCandidateButton.click();
    }
}
