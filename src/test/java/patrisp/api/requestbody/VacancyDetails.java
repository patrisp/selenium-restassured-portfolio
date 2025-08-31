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
}
