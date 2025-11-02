package patrisp.tests.employees;

import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import patrisp.api.Specifications;
import patrisp.api.requestbody.EmployeePersonalDetails;
import patrisp.api.requestmethod.EmployeesRequests;
import patrisp.pages.PageProvider;
import patrisp.testdata.EmployeeData;
import patrisp.tests.core.BaseTest;

import static patrisp.api.Specifications.API_URL;
import static patrisp.utilities.RandomUtil.generateRandomString;

public class EditEmployeeTest extends BaseTest {
int USER_ID;

    @BeforeClass
    public void createTestData() {
        String employeeId;
        boolean isValid;
        EmployeePersonalDetails employeeInitialDetails = new EmployeePersonalDetails("Jake", "Adam", "Reynolds", (String)null, (String)null);

        Specifications.installSpecification(
                Specifications.requestSpecification(API_URL, ContentType.JSON),
                Specifications.responseSpecification(200)
        );
        EmployeesRequests employeesRequests = new EmployeesRequests();

        do {
            employeeId = generateRandomString(5);
            isValid = employeesRequests.checkIfEmployeeIdIsValid(employeeId);
            if (isValid) employeeInitialDetails.setEmployeeId(employeeId);
        } while (!isValid);

        USER_ID = employeesRequests.createNewEmployee(employeeInitialDetails);
    }

    @Test
    public void EditEmployee() {
        Specifications.installSpecification(
                Specifications.requestSpecification(API_URL, ContentType.JSON),
                Specifications.responseSpecification(200)
        );

        PageProvider pages = new PageProvider(driver);
        EmployeeData updatedEmployeeDetails = new EmployeeData("Aaron", "James", "Gibson", null);
        String employeeId = generateRandomString(5);

        // Open employee page
        driver.get("web/index.php/pim/viewPersonalDetails/empNumber/" + USER_ID);
        // Update first name, middle name, last name
        // Set other id, driver licence, license expiry
        // Set Nationality and marital status
        // Set date of birth and gender
        EmployeesRequests employeesRequests = new EmployeesRequests();
        employeesRequests.checkIfEmployeeIdIsValid(employeeId);
        // Save changes

    }
}
