package patrisp.tests.recruitment;

import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import patrisp.api.Specifications;
import patrisp.api.requestbody.EmployeePersonalDetails;
import patrisp.api.requestmethod.EmployeesRequests;
import patrisp.pages.PageProvider;
import patrisp.testdata.AdminData;
import patrisp.testdata.VacancyData;
import patrisp.tests.core.BaseTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static patrisp.api.Specifications.API_URL;

public class AddVacancyTest extends BaseTest {
    private PageProvider pages;
    private VacancyData vacancy;

    @BeforeMethod
    public void testSetup() {
        pages = new PageProvider(driver);
        Specifications.installSpecification(Specifications.requestSpecification(API_URL, ContentType.JSON), Specifications.responseSpecification(200));
        AdminData admin = new AdminData();
        EmployeesRequests request = new EmployeesRequests();
        EmployeePersonalDetails requestBody = new EmployeePersonalDetails(admin);
        request.updateUserDetails("7", requestBody);
        vacancy = new VacancyData();
        vacancy.setVacancyName("Selenium vacancy" + System.currentTimeMillis());
        vacancy.setDescription("Description " + System.currentTimeMillis());
        vacancy.setHiringManager(admin.getAdminFullName());
    }

    @Test
    public void addNewVacancy() {
        pages.dashboard().goToModule("Recruitment");
        pages.viewCandidates().goToTab("Vacancies");
        pages.viewVacancies().addVacancy();
        pages.vacancy()
                .addVacancyName(vacancy.vacancyName)
                .selectJobTitle(vacancy.jobTitle)
                .addDescription(vacancy.description)
                .selectHiringManager(vacancy.hiringManager)
                .setNumberOfPositions(vacancy.numberOfPositions)
                .saveChanges();

        String currentUrl = driver.getCurrentUrl();
        Pattern pattern = Pattern.compile(".*/(\\d+)$");
        Matcher matcher = pattern.matcher(currentUrl);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(matcher.matches(), "URL should contain the vacancy ID");
        softAssert.assertEquals(pages.vacancy().getVacancyName(), vacancy.vacancyName);
        softAssert.assertEquals(pages.vacancy().getJobTitle(), vacancy.jobTitle);
        softAssert.assertEquals(pages.vacancy().getDescription(), vacancy.description);
        softAssert.assertEquals(pages.vacancy().getHiringManagerName(), vacancy.hiringManager);
        softAssert.assertEquals(pages.vacancy().getNumberOfPositions(), vacancy.numberOfPositions);
    }
}
