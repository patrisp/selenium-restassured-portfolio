package patrisp.testdata;

public class EmployeeData {
    public String firstName;
    public String middleName;
    public String lastName;
    public String id;

    public EmployeeData() {
        this.firstName = "Mary";
        this.middleName = "Kate";
        this.lastName = "Hopkins";
        this.id = null;
    }

    public EmployeeData(String firstName, String middleName, String lastName, String id) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.id = id;
    }

    public String getId() {
        return id;
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

    public void setId(String id) {
        this.id = id;
    }
}
