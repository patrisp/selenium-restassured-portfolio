package patrisp.api.requestbody;

import patrisp.testdata.AdminData;

public class EmployeePersonalDetails {
    private String lastName;
    private String firstName;
    private String middleName;
    private String employeeId;
    private String otherId;
    private String drivingLicenseNo;
    private String drivingLicenseExpiredDate;
    private Integer gender;
    private String maritalStatus;
    private String birthday;
    private Integer nationalityId;
    private Boolean smoker;
    private String militaryService;

    public EmployeePersonalDetails(AdminData admin) {
        this.lastName = admin.lastName;
        this.firstName = admin.firstName;
        this.middleName = admin.middleName;
        this.employeeId = admin.employeeId;
        this.otherId = admin.otherId;
        this.drivingLicenseNo = admin.drivingLicenseNo;
        this.drivingLicenseExpiredDate = admin.drivingLicenseExpiredDate;
        this.gender = admin.gender;
        this.maritalStatus = admin.maritalStatus;
        this.birthday = admin.birthday;
        this.nationalityId = admin.nationalityId;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId;
    }

    public void setDrivingLicenseNo(String drivingLicenseNo) {
        this.drivingLicenseNo = drivingLicenseNo;
    }

    public void setDrivingLicenseExpiredDate(String drivingLicenseExpiredDate) {
        this.drivingLicenseExpiredDate = drivingLicenseExpiredDate;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setNationalityId(Integer nationalityId) {
        this.nationalityId = nationalityId;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getOtherId() {
        return otherId;
    }

    public String getDrivingLicenseNo() {
        return drivingLicenseNo;
    }

    public String getDrivingLicenseExpiredDate() {
        return drivingLicenseExpiredDate;
    }

    public Integer getGender() {
        return gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public String getBirthday() {
        return birthday;
    }

    public Integer getNationalityId() {
        return nationalityId;
    }
}
