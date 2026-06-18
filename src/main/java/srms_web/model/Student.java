package srms_web.model;

public class Student {

    private int studentId;

    private String rollNumber;

    private String name;

    private String dob;

    private String gender;

    private int departmentId;

    private int userId;

    private String username;

    private String password;

    public Student() {}

    public Student(
            int studentId,
            String rollNumber,
            String name,
            String dob,
            String gender,
            int departmentId,
            int userId,
            String username,
            String password
    ) {

        this.studentId =
                studentId;

        this.rollNumber =
                rollNumber;

        this.name =
                name;

        this.dob =
                dob;

        this.gender =
                gender;

        this.departmentId =
                departmentId;

        this.userId =
                userId;

        this.username =
                username;

        this.password =
                password;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(
            int studentId
    ) {
        this.studentId =
                studentId;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(
            String rollNumber
    ) {
        this.rollNumber =
                rollNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(
            String name
    ) {
        this.name =
                name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(
            String dob
    ) {
        this.dob =
                dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(
            String gender
    ) {
        this.gender =
                gender;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(
            int departmentId
    ) {
        this.departmentId =
                departmentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(
            int userId
    ) {
        this.userId =
                userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(
            String username
    ) {
        this.username =
                username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(
            String password
    ) {
        this.password =
                password;
    }

}