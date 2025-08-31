package patrisp.tests.recruitment;

import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import patrisp.api.Specifications;
import patrisp.api.requestbody.CandidateDetails;
import patrisp.api.requestbody.VacancyDetails;
import patrisp.api.requestmethod.CandidateRequests;
import patrisp.api.requestmethod.VacancyRequests;
import patrisp.pages.PageProvider;
import patrisp.testdata.AdminData;
import patrisp.tests.core.BaseTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static patrisp.api.Specifications.API_URL;

public class RejectCandidateTest extends BaseTest {
//    TODO: Shortlisted status test
//    TODO: Interview Scheduled status test
//    TODO: Interview Failed status test
//    TODO: Interview Passed status test
//    TODO: Offer Job status test
private PageProvider pages;
private String CANDIDATE_PROFILE_URL = "https://opensource-demo.orangehrmlive.com/web/index.php/recruitment/addCandidate/";
private String ACTIVITY_DATE = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-dd-MM"));
private String VACANCY_NAME = "Junior Account Assistant";
private AdminData ADMIN;
private String ACTIVITY_LOG_CONTENT;
private String CANDIDATE_FULL_NAME;
private CandidateDetails APPLICATION_INITIATED_CANDIDATE;
private int CANDIDATE_ID;

    @BeforeMethod
    public void testSetup() {
        pages = new PageProvider(driver);
        Specifications.installSpecification(
                Specifications.requestSpecification(API_URL, ContentType.JSON),
                Specifications.responseSpecification(200)
        );

        // Admin setup
        ADMIN = new AdminData();
        // Vacancy setup
        VacancyRequests vacancyRequests = new VacancyRequests();
        vacancyRequests.updateVacancyDetails(new VacancyDetails(7), "5");
        // Candidate setup
        CandidateRequests candidateRequests = new CandidateRequests();
        APPLICATION_INITIATED_CANDIDATE = new CandidateDetails("Elena", "Varga");
        APPLICATION_INITIATED_CANDIDATE.setDateOfApplication("2025-08-24");
        CANDIDATE_ID = candidateRequests.createCandidate(APPLICATION_INITIATED_CANDIDATE);
        CANDIDATE_FULL_NAME = APPLICATION_INITIATED_CANDIDATE.getFirstName() + " " + APPLICATION_INITIATED_CANDIDATE.getLastName();
        ACTIVITY_LOG_CONTENT = ADMIN.getAdminFullName() + " rejected " + CANDIDATE_FULL_NAME + " from " + VACANCY_NAME;
    }

    @Test
    public void rejectCandidateAtApplicationInitiatedStatus() {
        driver.get(CANDIDATE_PROFILE_URL + CANDIDATE_ID);
        pages.candidate().rejectCandidate();

        SoftAssert softAssert = new SoftAssert();
        // Toast message validation
        softAssert.assertEquals(pages.candidate().getToastMessageTitle(), "Success");
        softAssert.assertEquals(pages.candidate().getToastMessageContent(), "Successfully Saved");
        // Activity page validation
        softAssert.assertEquals(pages.candidate().getActivityDate(0), ACTIVITY_DATE);
        softAssert.assertEquals(pages.candidate().getActivityContent(0), ACTIVITY_LOG_CONTENT);
    }

}
