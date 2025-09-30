package patrisp.testdata;

public class VacancyData {
    public String vacancyName;
    public String jobTitle;
    public String description;
    public String hiringManager;
    public String numberOfPositions;
    public boolean isActive;
    public boolean isPublished;


    public VacancyData() {
        this.vacancyName = "";
        this.jobTitle = "Account Assistant";
        this.description = "";
        this.hiringManager = "";
        this.numberOfPositions = "10";
        this.isActive = true;
        this.isPublished = true;
    }

    public void setVacancyName(String vacancyName) {
        this.vacancyName = vacancyName;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHiringManager(String hiringManager) {
        this.hiringManager = hiringManager;
    }

    public void setNumberOfPositions(String numberOfPositions) {
        this.numberOfPositions = numberOfPositions;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public String getVacancyName() {
        return vacancyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getDescription() {
        return description;
    }

    public String getHiringManager() {
        return hiringManager;
    }

    public String getNumberOfPositions() {
        return numberOfPositions;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isPublished() {
        return isPublished;
    }
}
