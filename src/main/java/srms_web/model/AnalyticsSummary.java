package srms_web.model;

public class AnalyticsSummary {

    private int totalStudents;

    private double averageMarks;

    private int highestMark;

    private int lowestMark;

    private double passPercentage;

    public int getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(
            int totalStudents
    ) {
        this.totalStudents = totalStudents;
    }

    public double getAverageMarks() {
        return averageMarks;
    }

    public void setAverageMarks(
            double averageMarks
    ) {
        this.averageMarks = averageMarks;
    }

    public int getHighestMark() {
        return highestMark;
    }

    public void setHighestMark(
            int highestMark
    ) {
        this.highestMark = highestMark;
    }

    public int getLowestMark() {
        return lowestMark;
    }

    public void setLowestMark(
            int lowestMark
    ) {
        this.lowestMark = lowestMark;
    }

    public double getPassPercentage() {
        return passPercentage;
    }

    public void setPassPercentage(
            double passPercentage
    ) {
        this.passPercentage = passPercentage;
    }
}