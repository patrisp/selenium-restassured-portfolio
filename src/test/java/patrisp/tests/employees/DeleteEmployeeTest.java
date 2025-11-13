package patrisp.tests.employees;

import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import patrisp.api.Specifications;
import patrisp.api.requestbody.employee.AddEmployeeRequestBody;
import patrisp.api.requestmethod.EmployeesRequests;
import patrisp.pages.PageProvider;
import patrisp.tests.core.BaseTest;

import static patrisp.api.Specifications.API_URL;
import static patrisp.utilities.RandomUtil.generateRandomString;

public class DeleteEmployeeTest extends BaseTest {

    String EMPLOYEE_ID;

    @BeforeClass
    public void createTestData() {
        AddEmployeeRequestBody employee = AddEmployeeRequestBody.builder()
                .firstName("Fiona")
                .middleName("Sarah")
                .lastName("Murphy")
                .build();

        Specifications.installSpecification(
                Specifications.requestSpecification(API_URL, ContentType.JSON),
                Specifications.responseSpecification(200)
        );

        EmployeesRequests employeesRequests = new EmployeesRequests();
        boolean idIsUnique;
        do {
            EMPLOYEE_ID = generateRandomString(5);
            idIsUnique = employeesRequests.checkIfEmployeeIdIsValid(EMPLOYEE_ID);
            if (idIsUnique) employee.setEmployeeId(EMPLOYEE_ID);
        } while (!idIsUnique);

        employeesRequests.createNewEmployee(employee);
    }

    @Test
    public void deleteEmployee() {
        PageProvider pages = new PageProvider(driver);

        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/pim/viewEmployeeList");

        pages.viewEmployees()
                .typeEmployeeId(EMPLOYEE_ID)
                .findEmployee()
                .deleteEmployee(EMPLOYEE_ID);

        SoftAssert softAssert = new SoftAssert();
        pages.viewEmployees()
                .typeEmployeeId(EMPLOYEE_ID)
                .findEmployee();

        softAssert.assertEquals(pages.viewEmployees().getToastMessageTitle(), "Info");
        softAssert.assertEquals(pages.viewEmployees().getToastMessageContent(), "No Records Found");
        softAssert.assertEquals(pages.viewEmployees().getNumberOfEmployeeRows(), 0);
    }
}
