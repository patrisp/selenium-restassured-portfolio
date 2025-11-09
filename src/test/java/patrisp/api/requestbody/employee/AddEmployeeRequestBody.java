package patrisp.api.requestbody.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class AddEmployeeRequestBody {
    private String firstName;
    private String middleName;
    private String lastName;
    private String employeeId;
    private String empPicture;
}
