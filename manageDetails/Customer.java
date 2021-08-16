package manageDetails;

import java.sql.Date;

public class Customer {
    //Variable
    private String name;
    private Date dofBirth;
    private String location;

    //Getter and Setter for name and Date
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Date getDofBirth() {
        return dofBirth;
    }
    public void setDofBirth(Date dofBirth) {
        this.dofBirth = dofBirth;
    }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

}
