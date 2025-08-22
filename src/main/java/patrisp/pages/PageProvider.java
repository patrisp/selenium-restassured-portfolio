package patrisp.pages;

import org.openqa.selenium.WebDriver;

public class PageProvider {
    private WebDriver driver;

    public PageProvider(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage login() {
        return new LoginPage(driver);
    }

    public DashboardPage dashboard() {
        return new DashboardPage(driver);
    }

    public ViewCandidatesPage viewCandidates() {
        return new ViewCandidatesPage(driver);
    }

    public CandidatePage candidate() {
        return new CandidatePage(driver);
    }

}
