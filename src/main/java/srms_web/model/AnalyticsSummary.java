package srms_web.model;

public class AnalyticsSummary {

    // STAFF ANALYTICS

    private int totalStudents;

    private double averageMarks;

    private int highestMark;

    private int lowestMark;

    private double passPercentage;

    // ADMIN ANALYTICS

    private int totalDepartments;

    private int totalSubjects;

    private int passedStudents;

    private int failedStudents;

    private double overallAverage;

    // =====================
    // TOTAL STUDENTS
    // =====================

    public int getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(
            int totalStudents
    ) {
        this.totalStudents = totalStudents;
    }

    // =====================
    // AVERAGE MARKS
    // =====================

    public double getAverageMarks() {
        return averageMarks;
    }

    public void setAverageMarks(
            double averageMarks
    ) {
        this.averageMarks = averageMarks;
    }

    // =====================
    // HIGHEST MARK
    // =====================

    public int getHighestMark() {
        return highestMark;
    }

    public void setHighestMark(
            int highestMark
    ) {
        this.highestMark = highestMark;
    }

    // =====================
    // LOWEST MARK
    // =====================

    public int getLowestMark() {
        return lowestMark;
    }

    public void setLowestMark(
            int lowestMark
    ) {
        this.lowestMark = lowestMark;
    }

    // =====================
    // PASS PERCENTAGE
    // =====================

    public double getPassPercentage() {
        return passPercentage;
    }

    public void setPassPercentage(
            double passPercentage
    ) {
        this.passPercentage = passPercentage;
    }

    // =====================
    // TOTAL DEPARTMENTS
    // =====================

    public int getTotalDepartments() {
        return totalDepartments;
    }

    public void setTotalDepartments(
            int totalDepartments
    ) {
        this.totalDepartments = totalDepartments;
    }

    // =====================
    // TOTAL SUBJECTS
    // =====================

    public int getTotalSubjects() {
        return totalSubjects;
    }

    public void setTotalSubjects(
            int totalSubjects
    ) {
        this.totalSubjects = totalSubjects;
    }

    // =====================
    // PASSED STUDENTS
    // =====================

    public int getPassedStudents() {
        return passedStudents;
    }

    public void setPassedStudents(
            int passedStudents
    ) {
        this.passedStudents = passedStudents;
    }

    // =====================
    // FAILED STUDENTS
    // =====================

    public int getFailedStudents() {
        return failedStudents;
    }

    public void setFailedStudents(
            int failedStudents
    ) {
        this.failedStudents = failedStudents;
    }

    // =====================
    // OVERALL AVERAGE
    // =====================

    public double getOverallAverage() {
        return overallAverage;
    }

    public void setOverallAverage(
            double overallAverage
    ) {
        this.overallAverage = overallAverage;
    }

}