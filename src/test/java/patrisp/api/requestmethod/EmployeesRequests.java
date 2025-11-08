package patrisp.api.requestmethod;

import io.restassured.response.Response;
import patrisp.api.Specifications;
import patrisp.api.requestbody.EmployeePersonalDetails;
import patrisp.api.requestbody.employee.AddEmployeeRequestBody;

import static io.restassured.RestAssured.given;

public class EmployeesRequests extends Specifications {
    public void updateUserDetails(String userId, EmployeePersonalDetails employeeDetails) {
        Response response = given()
                .body(employeeDetails)
                .when()
                .put("/api/v2/pim/employees/" + userId + "/personal-details");
    }

    public boolean checkIfEmployeeIdIsValid(String id) {
        Response response = given()
                .when()
                .get("/api/v2/core/validation/unique?value=" + id + "&entityName=Employee&attributeName=employeeId")
                .then()
                .extract().response();

        return response.jsonPath().getBoolean("data.valid");
    }

    public int createNewEmployee(AddEmployeeRequestBody employeeDetails) {
        Response response = given()
                .body(employeeDetails)
                .when()
                .post("/api/v2/pim/employees")
                .then()
                .extract().response();
        return response.jsonPath().getInt("data.empNumber");
    }
}
