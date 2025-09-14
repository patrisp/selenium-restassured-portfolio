package patrisp.api.requestmethod;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import patrisp.api.Specifications;
import patrisp.api.requestbody.VacancyDetails;

import java.util.List;

import static io.restassured.RestAssured.given;

public class VacancyRequests extends Specifications {
    public List<Integer> getJobTitles() {
        List<Integer> ids;
        Response response = given()
                .when()
                .get("/api/v2/admin/job-titles?limit=0")
                .then()
                .extract().response();
        JsonPath jsonPath = new JsonPath(response.asString());
        return ids = jsonPath.getList("data.id");
    }

    public int createVacancy(VacancyDetails vacancyDetails) {
        int vacancyId;
        Response response = given()
                .body(vacancyDetails)
                .when()
                .post("/api/v2/recruitment/vacancies")
                .then()
                .extract().response();
        JsonPath jsonPath = new JsonPath(response.asString());
        return vacancyId = jsonPath.getInt("data.id");
    }
}
