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
        EmployeeData employee = EmployeeData.builder()
                .firstName("Sarah")
                .middleName("Caroline")
                .lastName("Lewis")
                .build();
        pages.dashboard().goToModule("PIM");
        pages.viewEmployees().openEmployeeCreationPage();
        employee.setEmployeeId(pages.employee().getEmployeeId());
        pages.employee()
                .setEmployeeFullName(employee.getFirstName(), employee.getMiddleName(), employee.getLastName())
                .addProfilePicture("test-data/employee-profile-picture.jpg")
                .submitEmployee();

        SoftAssert softAssert = new SoftAssert();
        // Toast message validation
        softAssert.assertEquals(pages.employee().getToastMessageTitle(), "Success");
        softAssert.assertEquals(pages.employee().getToastMessageContent(), "Successfully Saved");
        // Employee name validation (header)
        softAssert.assertEquals(pages.employee().getEmployeeHeaderName(), employee.getFirstName() + " " + employee.getLastName());
        // Employee name validation (input fields)
        softAssert.assertEquals(pages.employee().getEmployeeFullName(), employee.getFirstName() + " " + employee.getMiddleName() + " " + employee.getLastName());
        // Employee ID validation
        softAssert.assertEquals(pages.employee().getEmployeeId(), employee.getEmployeeId());
        // Employee profile picture validation
        softAssert.assertEquals(pages.employee().getProfilePictureWidth(), 124);
        softAssert.assertEquals(pages.employee().getProfilePictureHeight(), 104);
    }
}
