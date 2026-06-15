package srms_web.model;

public class StudentInfo {

    private int studentId;
    private String rollNumber;
    private String name;
    private String gender;
    private String dob;
    private String department;

    public StudentInfo() {}

    public StudentInfo(
            int studentId,
            String rollNumber,
            String name,
            String gender,
            String dob,
            String department) {

        this.studentId = studentId;
        this.rollNumber = rollNumber;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.department = department;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}