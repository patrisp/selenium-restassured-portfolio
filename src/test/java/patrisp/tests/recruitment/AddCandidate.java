package patrisp.tests.recruitment;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import patrisp.api.requestbody.EmployeePersonalDetails;
import patrisp.api.requestmethod.EmployeesRequests;
import patrisp.pages.PageProvider;
import patrisp.testdata.AdminData;
import patrisp.testdata.CandidateData;
import patrisp.tests.core.BaseTest;
import patrisp.utilities.DateTimeUtils;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddCandidate extends BaseTest {
    private PageProvider pages;
    private CandidateData candidate;
    private AdminData admin;
    private String candidateFullName;
    private String adminFullName;
    private String activityDate;
    private static final String DATE_FORMAT = "yyyy-dd-MM";

    @BeforeMethod
    public void testSetUp() {
        pages = new PageProvider(driver);

        // Admin setup
        admin = new AdminData();
        adminFullName = String.join(" ", admin.firstName, admin.middleName, admin.lastName);
        EmployeesRequests request = new EmployeesRequests();
        EmployeePersonalDetails requestBody = new EmployeePersonalDetails(admin);
        request.updateUserDetails("7", requestBody);

        // Candidate setup
        candidate = new CandidateData();
        candidateFullName = String.join(" ", candidate.firstName, candidate.middleName, candidate.lastName);
        activityDate = DateTimeUtils.formatDate(String.valueOf(LocalDate.now()), DATE_FORMAT);
    }

    @Test
    public void addNewCandidate() throws URISyntaxException {
        CandidateData candidate = new CandidateData();
        String candidateFullName = candidate.firstName + " " + candidate.middleName + " " + candidate.lastName;
        String adminFullName = admin.firstName + " " + admin.middleName + " " + admin.lastName;
        String activityDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-dd-MM"));

        pages.dashboard().goToModule("Recruitment");
        pages.viewCandidates().addCandidate();
        wait.until(ExpectedConditions.urlContains("recruitment/addCandidate"));
        pages.candidate()
                .addCandidateName(candidate.firstName, candidate.middleName, candidate.lastName)
                .selectVacancy(candidate.vacancyName)
                .addEmailAddress(candidate.email)
                .addContactNumber(candidate.contactNumber)
                .addKeywords(candidate.keywords)
                .uploadResume("test-data/" + candidate.resumeName)
                .addNotes(candidate.notes)
                .selectDate(candidate.applicationDate)
                .allowDataConsent()
                .submitCandidate();

        SoftAssert softAssert = new SoftAssert();
        // Toast message validation
        softAssert.assertEquals(pages.candidate().getToastMessageTitle(), "Success");
        softAssert.assertEquals(pages.candidate().getToastMessageContent(), "Successfully Saved");
        // Candidate card validation
        softAssert.assertEquals(pages.candidate().getCandidateFullName(), candidateFullName);
        softAssert.assertEquals(pages.candidate().getVacancyDetails(), candidate.vacancyName);
        softAssert.assertEquals(pages.candidate().getHiringManagerName(), candidate.hiringManager);
        softAssert.assertEquals(pages.candidate().getApplicationStatus(), "Status: Application Initiated");
        // Candidate details validation
        softAssert.assertEquals(pages.candidate().getCandidateFirstName(), candidate.firstName);
        softAssert.assertEquals(pages.candidate().getCandidateMiddleName(), candidate.middleName);
        softAssert.assertEquals(pages.candidate().getCandidateLastName(), candidate.lastName);
        softAssert.assertEquals(pages.candidate().getVacancyName(), candidate.vacancyName);
        softAssert.assertEquals(pages.candidate().getEmail(), candidate.email);
        softAssert.assertEquals(pages.candidate().getKeywords(), candidate.keywords);
        softAssert.assertEquals(pages.candidate().getNotes(), candidate.notes);
        softAssert.assertEquals(pages.candidate().getResumeName(), candidate.resumeName);
        softAssert.assertEquals(pages.candidate().getDateOfApplication(),  DateTimeUtils.formatDate(candidate.applicationDate, DATE_FORMAT));
        softAssert.assertTrue(pages.candidate().isDataConsentGiven());
        // Activity page validation
        softAssert.assertEquals(pages.candidate().getActivityDate(0), activityDate);
        softAssert.assertEquals(pages.candidate().getActivityDate(1), activityDate);
        softAssert.assertTrue(pages.candidate().getActivityContent(0).contains("assigned the job vacancy " + candidate.vacancyName));
        softAssert.assertTrue(pages.candidate().getActivityContent(1).contains(adminFullName + " added " + candidateFullName));

        softAssert.assertAll();
    }
}
