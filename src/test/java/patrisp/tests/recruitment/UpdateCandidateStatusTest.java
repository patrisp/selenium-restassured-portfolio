package patrisp.tests.recruitment;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
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

public class UpdateCandidateStatusTest extends BaseTest {
    private PageProvider pages;
    private static final String CANDIDATE_PROFILE_URL = "https://opensource-demo.orangehrmlive.com/web/index.php/recruitment/addCandidate/";
    private static final String VACANCY_NAME = "Selenium" + System.currentTimeMillis();
    private static final String ACTIVITY_DATE = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    private static final String INTERVIEW_NAME = "Test interview";
    private static final String INTERVIEW_DATE = ACTIVITY_DATE;
    private AdminData admin;
    private final Map<CandidateDetails, Integer> candidates = new HashMap<>();
    private CandidateDetails applicationInitiatedCandidate;
    private CandidateDetails shortlistedCandidate;
    private CandidateDetails interviewScheduledCandidate;
    private CandidateDetails interviewPassedCandidate;
    private CandidateDetails offerJobCandidate;

    enum Status {
        SHORTLISTED,
        INTERVIEW_SCHEDULED,
        INTERVIEW_PASSED,
        OFFER_JOB,
        HIRE
    }

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
        interviewPassedCandidate = new CandidateDetails("Amelia", "Grant");
        offerJobCandidate = new CandidateDetails("Tobias", "Richter");

        candidates.put(applicationInitiatedCandidate, null);
        candidates.put(shortlistedCandidate, null);
        candidates.put(interviewScheduledCandidate, null);
        candidates.put(interviewPassedCandidate, null);
        candidates.put(offerJobCandidate, null);

        CandidateRequests candidateRequests = new CandidateRequests();
        for (CandidateDetails candidate : candidates.keySet()) {
            candidate.setDateOfApplication("2025-01-01");
            candidate.setVacancyId(vacancyId);
            int id = candidateRequests.createCandidate(candidate);
            candidates.put(candidate, id);
        }

        // Update candidate statuses
        Map<String, Object> interviewDetails = new HashMap<>();
        interviewDetails.put("interviewName", INTERVIEW_NAME);
        interviewDetails.put("interviewDate", "2025-01-01");
        interviewDetails.put("interviewTime", null);
        interviewDetails.put("note", null);
        interviewDetails.put("interviewerEmpNumbers", new Integer[]{interviewerId});

        List<Integer> candidatesToShortlist = List.of(
                candidates.get(shortlistedCandidate),
                candidates.get(interviewScheduledCandidate),
                candidates.get(interviewPassedCandidate),
                candidates.get(offerJobCandidate)
        );
        List<Integer> candidatesToInterview = List.of(
                candidates.get(interviewScheduledCandidate),
                candidates.get(interviewPassedCandidate),
                candidates.get(offerJobCandidate)
        );
        List<Integer> candidatesToPassInterview = List.of(
                candidates.get(interviewPassedCandidate),
                candidates.get(offerJobCandidate)
        );

        candidatesToShortlist.forEach(candidateId -> candidateRequests.shortlistCandidate(candidateId));
        candidatesToInterview.forEach(candidateId -> candidateRequests.scheduleInterview(candidateId, interviewDetails));
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

    private void proceedToTheNextStage(Status status) {
        if (status == Status.INTERVIEW_SCHEDULED) {
            pages.candidate()
                    .proceedToNextStatus()
                    .setInterviewName(INTERVIEW_NAME)
                    .selectInterviewer(admin.getAdminFullName())
                    .selectDate(INTERVIEW_DATE)
                    .saveChanges();
        } else {
            pages.candidate()
                    .proceedToNextStatus()
                    .saveChanges();
        }
    }

    private void verifyActivityLogContent(CandidateDetails candidate, Status status) {
        String adminName = admin.getAdminFullName();
        String activity = switch (status) {
            case SHORTLISTED -> String.format("Shortlisted for %s by %s", VACANCY_NAME, adminName);
            case INTERVIEW_SCHEDULED -> String.format("%s scheduled %s on %s with %s for %s",
                    adminName, INTERVIEW_NAME, INTERVIEW_DATE, adminName, VACANCY_NAME);
            case INTERVIEW_PASSED -> String.format("%s marked %s as passed for %s",
                    adminName, INTERVIEW_NAME, VACANCY_NAME);
            case OFFER_JOB -> String.format("%s offered the job for %s", adminName, VACANCY_NAME);
            case HIRE -> String.format("%s hired %s for %s", adminName, candidate.getCandidateFullName(), VACANCY_NAME);
        };

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
                activity
        );
        softAssert.assertAll();
    }

    @DataProvider(name = "candidateStatusData")
    public Object[][] candidateStatusData() {
        return new Object[][] {
                {applicationInitiatedCandidate, Status.SHORTLISTED},
                {shortlistedCandidate, Status.INTERVIEW_SCHEDULED},
                {interviewScheduledCandidate, Status.INTERVIEW_PASSED},
                {interviewPassedCandidate, Status.OFFER_JOB},
                {offerJobCandidate, Status.HIRE}
        };
    }

    @Test(dataProvider = "candidateStatusData")
    public void progressCandidateToTheNextStatus(CandidateDetails candidate, Status status) {
        openCandidatePage(candidates.get(candidate));
        proceedToTheNextStage(status);
        verifyActivityLogContent(candidate, status);
    }
}
