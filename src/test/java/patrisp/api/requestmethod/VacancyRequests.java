package patrisp.api.requestmethod;
import io.restassured.response.Response;
import patrisp.api.Specifications;
import patrisp.api.requestbody.VacancyDetails;
import static io.restassured.RestAssured.given;

public class VacancyRequests extends Specifications {
    public void updateVacancyDetails(VacancyDetails vacancyDetails, String vacancyId) {
        Response response = given()
                .body(vacancyDetails)
                .when()
                .put("/api/v2/recruitment/vacancies/" + vacancyId);

    }
}
