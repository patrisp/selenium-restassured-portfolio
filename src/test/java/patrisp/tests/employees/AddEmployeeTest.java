package patrisp.tests.employees;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import patrisp.pages.PageProvider;
import patrisp.testdata.EmployeeData;
import patrisp.tests.core.BaseTest;

import java.net.URISyntaxException;

public class AddEmployeeTest extends BaseTest {
    @Test
    public void AddNewEmployee() throws URISyntaxException {
        PageProvider pages = new PageProvider(driver);
        EmployeeData employee = new EmployeeData();
        pages.dashboard().goToModule("PIM");
        pages.viewEmployees().openEmployeeCreationPage();
        employee.setId(pages.employee().getId());
        pages.employee()
                .addEmployeeFullName(employee.firstName, employee.middleName, employee.lastName)
                .addProfilePicture("test-data/employee-profile-picture.jpg")
                .submitEmployee();

        SoftAssert softAssert = new SoftAssert();
        // Toast message validation
        softAssert.assertEquals(pages.employee().getToastMessageTitle(), "Success");
        softAssert.assertEquals(pages.employee().getToastMessageContent(), "Successfully Saved");
        // Employee name validation (header)
        softAssert.assertEquals(pages.employee().getEmployeeHeaderName(), employee.firstName + " " + employee.lastName);
        // Employee name validation (input fields)
        softAssert.assertEquals(pages.employee().getEmployeeFullName(), employee.firstName + " " + employee.middleName + " " + employee.lastName);
        // Employee ID validation
        softAssert.assertEquals(pages.employee().getId(), employee.id);
        // Employee profile picture validation
        softAssert.assertEquals(pages.employee().getProfilePictureWidth(), 124);
        softAssert.assertEquals(pages.employee().getProfilePictureHeight(), 104);
    }
}
