package patrisp.api.requestmethod;

import io.restassured.response.Response;
import patrisp.api.Specifications;
import patrisp.api.requestbody.CandidateDetails;

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
        response.then().log().all();

    }

    public void scheduleInterview(int candidateId) {
        Response response = given()
                .body("{interviewName\":\"Interview\",\"interviewDate\":\"2025-08-18\",\"interviewTime\":null,\"note\":null,\"interviewerEmpNumbers\":[7]}\n")
                .when()
                .post("/api/v2/recruitment/candidates/" + candidateId + "/shedule-interview");
    }

    public void passInterview(int candidateId, int interviewerId ) {
        Response response = given()
                .body("{\"note\":null}")
                .when()
                .put("/api/v2/recruitment/candidates/" + candidateId + "/interviews/" + interviewerId + "/pass");
    }

    public void offerJob(int candidateId) {
        Response response = given()
                .body("{\"note\":null}")
                .when()
                .put("/api/v2/recruitment/candidates/" + candidateId + "/job/offer");
    }
}
