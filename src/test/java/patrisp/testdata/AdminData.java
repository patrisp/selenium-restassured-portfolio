package patrisp.testdata;

public class AdminData {
    public String lastName;
    public String firstName;
    public String middleName;
    public String employeeId;
    public String otherId;
    public String drivingLicenseNo;
    public String drivingLicenseExpiredDate;
    public int gender;
    public String maritalStatus;
    public String birthday;
    public int nationalityId;
    public String nickname;
    public boolean smoker;
    public String militaryService;

    public AdminData() {
        this.lastName = "User";
        this.firstName = "Admin";
        this.middleName = "Test";
        this.employeeId = "71635";
        this.otherId = "123";
        this.drivingLicenseNo = "DLup0045";
        this.drivingLicenseExpiredDate = "2024-08-15";
        this.gender = 1;
        this.maritalStatus = "Other";
        this.birthday = "2024-08-15";
        this.nationalityId = 1;
        this.nickname = "OrangeAdmin";
        this.smoker = false;
        this.militaryService = "Not a veteran";
    }
}
