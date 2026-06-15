package srms_web.model;

public class Mark {

    private String subject;

    private int semester;

    private int marks;

    public Mark(
            String subject,
            int semester,
            int marks
    ) {

        this.subject = subject;
        this.semester = semester;
        this.marks = marks;
    }

    public String getSubject() {
        return subject;
    }

    public int getSemester() {
        return semester;
    }

    public int getMarks() {
        return marks;
    }
}

