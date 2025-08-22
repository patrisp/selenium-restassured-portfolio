package patrisp.api.requestbody;

import patrisp.testdata.AdminData;

public class EmployeePersonalDetails {
    public String lastName;
    public String firstName;
    public String middleName;
    public String employeeId;
    public String otherId;
    public String drivingLicenseNo;
    public String drivingLicenseExpiredDate;
    public Integer gender;
    public String maritalStatus;
    public String birthday;
    public Integer nationalityId;
    public String nickname;
    public Boolean smoker;
    public String militaryService;

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
        this.nickname = admin.nickname;
        this.smoker = admin.smoker;
        this.militaryService = admin.militaryService;
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

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setSmoker(Boolean smoker) {
        this.smoker = smoker;
    }

    public void setMilitaryService(String militaryService) {
        this.militaryService = militaryService;
    }
}
