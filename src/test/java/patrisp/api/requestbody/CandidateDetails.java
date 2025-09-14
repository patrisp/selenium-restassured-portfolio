package patrisp.api.requestbody;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CandidateDetails {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String[] keywords;
    private String comment;
    private String dateOfApplication;
    private Boolean consentToKeepData;
    private Integer vacancyId;

    public CandidateDetails(String firstName, String lastName) {
        this.comment = null;
        this.consentToKeepData = true;
        this.contactNumber = null;
        this.dateOfApplication = null;
        this.email = "example@example.com";
        this.firstName = firstName;
        this.keywords = null;
        this.lastName = lastName;
        this.middleName = null;
        this.vacancyId = 5;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDateOfApplication(String dateOfApplication) {
        this.dateOfApplication = dateOfApplication;
    }

    public void setConsentToKeepData(Boolean consentToKeepData) {
        this.consentToKeepData = consentToKeepData;
    }

    public void setVacancyId(Integer vacancyId) {
        this.vacancyId = vacancyId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public String getComment() {
        return comment;
    }

    public String getDateOfApplication() {
        return dateOfApplication;
    }

    public Boolean getConsentToKeepData() {
        return consentToKeepData;
    }

    public Integer getVacancyId() {
        return vacancyId;
    }

    @JsonIgnore
    public String getCandidateFullName() {
        String fullName;
        if (middleName == null) {
            fullName = firstName + " " + lastName;
        } else {
            fullName = firstName + " " + middleName + " " + lastName;
        }
        return fullName;
    }
}
