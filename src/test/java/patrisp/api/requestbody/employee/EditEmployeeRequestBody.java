package patrisp.api.requestbody.employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditEmployeeRequestBody  {
    private String firstName;
    private String lastName;
    private String middleName;
    private String employeeId;
    private String otherId;
    private String drivingLicenseNo;
    private String drivingLicenseExpiredDate;
    private Integer gender;
    private String maritalStatus;
    private String birthday;
    private Integer nationalityId;
}
