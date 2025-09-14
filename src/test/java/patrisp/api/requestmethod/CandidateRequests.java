package patrisp.api.requestmethod;

import io.restassured.response.Response;
import patrisp.api.Specifications;
import patrisp.api.requestbody.CandidateDetails;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CandidateRequests extends Specifications {
    public int createCandidate(CandidateDetails candidateDetails) {
        Response response = given()
                .body(candidateDetails)
                .when()
                .post("/api/v2/recruitment/candidates");
        return response.path("data.id");
    }

    public void shortlistCandidate(int candidateId) {
        Response response = given()
                .when()
                .put("/api/v2/recruitment/candidates/" + candidateId + "/shortlist");
    }

    public void scheduleInterview(int candidateId, Map body) {
        Response response = given()
                .body(body)
                .when()
                .post("/api/v2/recruitment/candidates/" + candidateId + "/shedule-interview");
    }

    public void failInterview(int candidateId, int interviewerId) {
        Response response = given()
                .when()
                .put("/api/v2/recruitment/candidates/" + candidateId + "/interviews/" + interviewerId + "/fail");
    }


    public void passInterview(int candidateId, int interviewerId ) {
        Response response = given()
                .when()
                .put("/api/v2/recruitment/candidates/" + candidateId + "/interviews/" + interviewerId + "/pass");
    }

    public void offerJob(int candidateId) {
        Response response = given()
                .when()
                .put("/api/v2/recruitment/candidates/" + candidateId + "/job/offer");
    }
}
