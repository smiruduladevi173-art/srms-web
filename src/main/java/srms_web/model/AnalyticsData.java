package srms_web.model;

public class AnalyticsData {

    private String studentName;

    private double average;

    public AnalyticsData() {
    }

    public AnalyticsData(
            String studentName,
            double average
    ) {
        this.studentName = studentName;
        this.average = average;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(
            String studentName
    ) {
        this.studentName = studentName;
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