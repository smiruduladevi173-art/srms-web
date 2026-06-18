package srms_web.model;

public class TopStudent {

    private String rollNumber;

    private String name;

    private String department;

    private double average;

    public TopStudent() {
    }

    public TopStudent(
            String rollNumber,
            String name,
            String department,
            double average
    ) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.department = department;
        this.average = average;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(
            String rollNumber
    ) {
        this.rollNumber = rollNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(
            String name
    ) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(
            String department
    ) {
        this.department = department;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(
            double average
    ) {
        this.average = average;
    }
}