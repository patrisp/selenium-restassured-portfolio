package patrisp.api.requestmethod;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import patrisp.api.Specifications;
import patrisp.api.requestbody.EmployeePersonalDetails;

import static io.restassured.RestAssured.given;

public class EmployeesRequests extends Specifications {
    public void updateUserDetails(String userId, EmployeePersonalDetails employeeDetails) {
        Specifications.installSpecification(Specifications.requestSpecification(API_URL, ContentType.JSON), Specifications.responseSpecification(200));
        Response response = given()
                .body(employeeDetails)
                .when()
                .put("/api/v2/pim/employees/" + userId + "/personal-details");
    }
}
