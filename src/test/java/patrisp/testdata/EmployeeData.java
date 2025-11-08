package patrisp.testdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeData {
    private String firstName;
    private String middleName;
    private String lastName;
    private String employeeId;
    private String otherId;
    private String driverLicenseNumber;
    private String driverLicenseExpiryDate;
    private String nationality;
    private String maritalStatus;
    private String dateOfBirth;
    private String gender;
}
