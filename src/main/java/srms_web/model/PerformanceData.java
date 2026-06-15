package srms_web.model;

public class PerformanceData {

    private double averageMarks;
    private int totalSubjects;
    private double passPercentage;
    private String performance;

    public PerformanceData(
            double averageMarks,
            int totalSubjects,
            double passPercentage,
            String performance
    ) {

        this.averageMarks = averageMarks;
        this.totalSubjects = totalSubjects;
        this.passPercentage = passPercentage;
        this.performance = performance;
    }

    public double getAverageMarks() {
        return averageMarks;
    }

    public int getTotalSubjects() {
        return totalSubjects;
    }

    public double getPassPercentage() {
        return passPercentage;
    }

    public String getPerformance() {
        return performance;
    }
}