package patrisp.testdata;

public class CandidateData {
    public String firstName;
    public String middleName;
    public String lastName;
    public String vacancyName;
    public String hiringManager;
    public String email;
    public String contactNumber;
    public String resumeName;
    public String notes;
    public String[] keywords;
    public String applicationDate;
    public Integer vacancyId;

    public CandidateData() {
        this.firstName = "Sally";
        this.middleName = "Anne";
        this.lastName = "Kowalski";
        this.vacancyName = "Junior Account Assistant";
        this.hiringManager = "N/A";
        this.email = "example@example.com";
        this.contactNumber = "123456789";
        this.resumeName = "resume.pdf";
        this.notes = "Selenium portfolio";
        this.keywords = new String[]{"key1", "key2"};
        this.applicationDate = "2025-08-01";
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setVacancyId(int id) {
        this.vacancyId = id;
    }

    public void setApplicationDate(String date) {
        this.applicationDate = date;
    }

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
