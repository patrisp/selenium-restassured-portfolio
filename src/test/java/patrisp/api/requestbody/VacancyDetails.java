package patrisp.api.requestbody;

public class VacancyDetails {
    private String name;
    private Integer jobTitleId;
    private Integer employeeId;
    private Integer numOfPositions;
    private String description;
    private Boolean status;
    private Boolean isPublished;

    public VacancyDetails(Integer employeeId) {
        this.name = "Junior Account Assistant";
        this.jobTitleId = 22;
        this.employeeId = employeeId;
        this.numOfPositions = 10;
        this.description = "";
        this.status = true;
        this.isPublished = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getJobTitleId() {
        return jobTitleId;
    }

    public void setJobTitleId(Integer jobTitleId) {
        this.jobTitleId = jobTitleId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getNumOfPositions() {
        return numOfPositions;
    }

    public void setNumOfPositions(Integer numOfPositions) {
        this.numOfPositions = numOfPositions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Boolean published) {
        this.isPublished = published;
    }
}
