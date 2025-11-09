package patrisp.tests.employees;

import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import patrisp.api.Specifications;
import patrisp.api.requestbody.employee.AddEmployeeRequestBody;
import patrisp.api.requestmethod.EmployeesRequests;
import patrisp.pages.PageProvider;
import patrisp.testdata.EmployeeData;
import patrisp.tests.core.BaseTest;
import patrisp.utilities.DateTimeUtils;

import static patrisp.api.Specifications.API_URL;
import static patrisp.utilities.RandomUtil.generateRandomString;

public class EditEmployeeTest extends BaseTest {
int USER_ID;
boolean IS_VALID;
private static final String DATE_FORMAT = "yyyy-dd-MM";
    @BeforeClass
    public void createTestData() {
        AddEmployeeRequestBody employeeInitialDetails = AddEmployeeRequestBody.builder()
                .firstName("Jake")
                .middleName("Adam")
                .lastName("Reynolds")
                .build();

        Specifications.installSpecification(
                Specifications.requestSpecification(API_URL, ContentType.JSON),
                Specifications.responseSpecification(200)
        );

        EmployeesRequests employeesRequests = new EmployeesRequests();

        do {
            String employeeId = generateRandomString(5);
            IS_VALID = employeesRequests.checkIfEmployeeIdIsValid(employeeId);
            if (IS_VALID) employeeInitialDetails.setEmployeeId(employeeId);
        } while (!IS_VALID);

        USER_ID = employeesRequests.createNewEmployee(employeeInitialDetails);
    }

    @Test
    public void EditEmployee() {
        PageProvider pages = new PageProvider(driver);
        EmployeeData newEmployeeDetails = EmployeeData.builder()
                .firstName("Aaron")
                .middleName("James")
                .lastName("Gibson")
                .employeeId(null)
                .otherId("1234ABC")
                .driverLicenseNumber("ZXC456")
                .driverLicenseExpiryDate(DateTimeUtils.formatDate("2025-04-05", DATE_FORMAT))
                .nationality("Albanian")
                .maritalStatus("Single")
                .dateOfBirth(DateTimeUtils.formatDate("1998-03-10", DATE_FORMAT))
                .gender("male")
                .build();

        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/pim/viewPersonalDetails/empNumber/" + USER_ID);

        EmployeesRequests employeesRequests = new EmployeesRequests();

        do {
            String employeeId = generateRandomString(5);
            IS_VALID = employeesRequests.checkIfEmployeeIdIsValid(employeeId);
            if (IS_VALID) newEmployeeDetails.setEmployeeId(employeeId);
        } while (!IS_VALID);

        pages.employee()
                .setEmployeeFullName(newEmployeeDetails.getFirstName(), newEmployeeDetails.getMiddleName(), newEmployeeDetails.getLastName())
                .setEmployeeId(newEmployeeDetails.getEmployeeId())
                .setDriversLicenceNumber(newEmployeeDetails.getDriverLicenseNumber())
                .setDriversLicenceExpiryDate(newEmployeeDetails.getDriverLicenseExpiryDate())
                .selectNationality(newEmployeeDetails.getNationality())
                .selectMaritalStatus(newEmployeeDetails.getMaritalStatus())
                .selectGender(newEmployeeDetails.getGender())
                .setDateOfBirth(newEmployeeDetails.getDateOfBirth())
                .savePersonalDetailsChanges();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(pages.employee().getToastMessageTitle(), "Success");
        softAssert.assertEquals(pages.employee().getToastMessageContent(), "Successfully Saved");

        driver.navigate().refresh();

        softAssert.assertEquals(pages.employee().getEmployeeHeaderName(), newEmployeeDetails.getFirstName() + " " + newEmployeeDetails.getLastName());
        softAssert.assertEquals(pages.employee().getEmployeeFullName(), newEmployeeDetails.getFirstName() + " " + newEmployeeDetails.getMiddleName() + " " + newEmployeeDetails.getLastName());
        softAssert.assertEquals(pages.employee().getEmployeeId(), newEmployeeDetails.getEmployeeId());
        softAssert.assertEquals(pages.employee().getOtherId(), newEmployeeDetails.getOtherId());
        softAssert.assertEquals(pages.employee().getDriversLicenseNumber(), newEmployeeDetails.getDriverLicenseNumber());
        softAssert.assertEquals(pages.employee().getDriversLicenseNumber(), newEmployeeDetails.getDriverLicenseExpiryDate());
        softAssert.assertEquals(pages.employee().getNationality(), newEmployeeDetails.getNationality());
        softAssert.assertEquals(pages.employee().getMaritalStatus(), newEmployeeDetails.getMaritalStatus());
        softAssert.assertEquals(pages.employee().getGender(), newEmployeeDetails.getGender());
        softAssert.assertEquals(pages.employee().getDateOfBirth(), newEmployeeDetails.getDateOfBirth());
    }
}
