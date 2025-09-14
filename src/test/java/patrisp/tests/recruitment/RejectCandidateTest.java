package patrisp.tests.recruitment;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import patrisp.api.Specifications;
import patrisp.api.requestbody.CandidateDetails;
import patrisp.api.requestbody.EmployeePersonalDetails;
import patrisp.api.requestbody.VacancyDetails;
import patrisp.api.requestmethod.CandidateRequests;
import patrisp.api.requestmethod.EmployeesRequests;
import patrisp.api.requestmethod.VacancyRequests;
import patrisp.pages.PageProvider;
import patrisp.testdata.AdminData;
import patrisp.tests.core.BaseTest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static patrisp.api.Specifications.API_URL;

public class RejectCandidateTest extends BaseTest {
    private PageProvider pages;
    private static final String CANDIDATE_PROFILE_URL = "https://opensource-demo.orangehrmlive.com/web/index.php/recruitment/addCandidate/";
    private static final String ACTIVITY_DATE = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-dd-MM"));
    private static final String VACANCY_NAME = "Selenium" + System.currentTimeMillis();
    private AdminData admin;
    private final Map<CandidateDetails, Integer> candidates = new HashMap<>();
    private CandidateDetails applicationInitiatedCandidate;
    private CandidateDetails shortlistedCandidate;
    private CandidateDetails interviewScheduledCandidate;
    private CandidateDetails interviewFailedCandidate;
    private CandidateDetails interviewPassedCandidate;
    private CandidateDetails offerJobCandidate;

    @BeforeClass
    public void testDataSetup() {
        pages = new PageProvider(driver);

        Specifications.installSpecification(
                Specifications.requestSpecification(API_URL, ContentType.JSON),
                Specifications.responseSpecification(200)
        );

        // Vacancy setup
        int interviewerId = 7;
        VacancyRequests vacancyRequests = new VacancyRequests();
        List<Integer> jobTitleIds = vacancyRequests.getJobTitles();
        VacancyDetails vacancyDetails = new VacancyDetails(interviewerId);
        vacancyDetails.setName(VACANCY_NAME);
        vacancyDetails.setJobTitleId(jobTitleIds.get(0));
        int vacancyId = vacancyRequests.createVacancy(vacancyDetails);

        // Candidate setup
        applicationInitiatedCandidate = new CandidateDetails("Elena", "Varga");
        shortlistedCandidate = new CandidateDetails("Marcus", "Fieldman");
        interviewScheduledCandidate = new CandidateDetails("Sofia", "Lindström");
        interviewFailedCandidate = new CandidateDetails("Darius", "Novak");
        interviewPassedCandidate = new CandidateDetails("Amelia", "Grant");
        offerJobCandidate = new CandidateDetails("Tobias", "Richter");

        candidates.put(applicationInitiatedCandidate, null);
        candidates.put(shortlistedCandidate, null);
        candidates.put(interviewScheduledCandidate, null);
        candidates.put(interviewFailedCandidate, null);
        candidates.put(interviewPassedCandidate, null);
        candidates.put(offerJobCandidate, null);

        CandidateRequests candidateRequests = new CandidateRequests();
        for (CandidateDetails candidate : candidates.keySet()) {
            candidate.setDateOfApplication("2025-08-24");
            candidate.setVacancyId(vacancyId);
            int id = candidateRequests.createCandidate(candidate);
            candidates.put(candidate, id);
        }

        // Update candidate statuses
        Map<String, Object> interviewDetails = new HashMap<>();
        interviewDetails.put("interviewName", "Test");
        interviewDetails.put("interviewDate", "2025-08-18");
        interviewDetails.put("interviewTime", null);
        interviewDetails.put("note", null);
        interviewDetails.put("interviewerEmpNumbers", new Integer[]{interviewerId});

        List<Integer> candidatesToShortlist = List.of(
                candidates.get(shortlistedCandidate),
                candidates.get(interviewScheduledCandidate),
                candidates.get(interviewFailedCandidate),
                candidates.get(interviewPassedCandidate),
                candidates.get(offerJobCandidate)
        );
        List<Integer> candidatesToInterview = List.of(
                candidates.get(interviewScheduledCandidate),
                candidates.get(interviewFailedCandidate),
                candidates.get(interviewPassedCandidate),
                candidates.get(offerJobCandidate)
        );
        List<Integer> candidatesToPassInterview = List.of(
                candidates.get(interviewPassedCandidate),
                candidates.get(offerJobCandidate)
        );

        candidatesToShortlist.forEach(candidateId -> candidateRequests.shortlistCandidate(candidateId));
        candidatesToInterview.forEach(candidateId -> candidateRequests.scheduleInterview(candidateId, interviewDetails));
        candidateRequests.failInterview(candidates.get(interviewFailedCandidate), interviewerId);
        candidatesToPassInterview.forEach(candidateId -> candidateRequests.passInterview(candidateId, interviewerId));
        candidateRequests.offerJob(candidates.get(offerJobCandidate));
        RestAssured.reset();
    }

    @BeforeMethod
    public void testSetup() {
        pages = new PageProvider(driver);
        Specifications.installSpecification(
                Specifications.requestSpecification(API_URL, ContentType.JSON),
                Specifications.responseSpecification(200)
        );

        // Admin setup
        admin = new AdminData();
        EmployeesRequests request = new EmployeesRequests();
        EmployeePersonalDetails requestBody = new EmployeePersonalDetails(admin);
        request.updateUserDetails("7", requestBody);
    }

    private void openCandidatePage(int candidateId) {
        driver.get(CANDIDATE_PROFILE_URL + candidateId);
    }

    private void rejectCandidate(CandidateDetails candidate) {
        openCandidatePage(candidates.get(candidate));
        pages.candidate().rejectCandidate();
    }

    private void verifyActivityLogContent(CandidateDetails candidate) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(
                pages.candidate().getToastMessageTitle(),
                "Success"
        );
        softAssert.assertEquals(
                pages.candidate().getToastMessageContent(),
                "Successfully Updated"
        );
        softAssert.assertEquals(
                pages.candidate().getActivityDate(0),
                ACTIVITY_DATE
        );
        softAssert.assertEquals(
                pages.candidate().getActivityContent(0),
                admin.getAdminFullName() + " rejected " + candidate.getCandidateFullName() + " from the " + VACANCY_NAME
        );
        softAssert.assertAll();
    }

    @DataProvider(name = "candidates")
    public Object[][] candidateProvider() {
        return new Object[][] {
                { applicationInitiatedCandidate },
                { shortlistedCandidate },
                { interviewScheduledCandidate },
                { interviewFailedCandidate },
                { interviewPassedCandidate },
                { offerJobCandidate }
        };
    }

    @Test(dataProvider = "candidates")
    public void rejectCandidateAtDifferentStatuses(CandidateDetails candidate) {
        rejectCandidate(candidate);
        verifyActivityLogContent(candidate);
    }
}
