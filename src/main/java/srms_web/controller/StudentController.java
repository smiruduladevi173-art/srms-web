
package srms_web.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import srms_web.model.Mark;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;




@Controller
public class StudentController {

        // Return highest marks from list or 0 if empty
        private int highestMark(List<Mark> marksList) {
                int highest = 0;
                for (Mark m : marksList) {
                        if (m != null && m.getMarks() > highest) {
                                highest = m.getMarks();
                        }
                }
                return highest;
        }

    @GetMapping({
            "/student/student-dashboard",
            "/student/profile-profile"
    })
    public String dashboard(

        

            @RequestParam(
                    defaultValue = "profile"
            )
            String view,

            HttpSession session,

            Model model

    )


        
    
    {

        System.out.println("\n========== DASHBOARD ==========");

       Object userId =
        session.getAttribute(
                "userId"
        );

        System.out.println(
                "Session User ID = "
                + userId
        );

        if (userId == null) {

            System.out.println(
                    "ERROR → Session empty"
            );

            return "redirect:/";
        }

Integer studentId =
        getStudentId(
                userId
        );

boolean loaded =
        loadStudentData(
                userId,
                model
        );

loadMarksData(
        studentId,
        model
);

loadPerformanceData(
        studentId,
        model
);


        if (!loaded) {

            System.out.println(
                    "WARNING → DB fetch failed"
            );

            model.addAttribute(
                    "studentId",
                    userId
            );

            model.addAttribute(
                    "rollNumber",
                    "-"
            );

            model.addAttribute(
                    "name",
                    "DATA NOT FOUND"
            );

            model.addAttribute(
                    "dob",
                    "-"
            );

            model.addAttribute(
                    "gender",
                    "-"
            );

            model.addAttribute(
                    "department",
                    "-"
            );

            model.addAttribute(
                    "java",
                    "-"
            );

            model.addAttribute(
                    "dbms",
                    "-"
            );

            model.addAttribute(
                    "python",
                    "-"
            );

        }

        model.addAttribute(
                "view",
                resolveView(view)
        );

        model.addAttribute(
        "activePage",
        view
);


System.out.println(
    "View = " + resolveView(view)
);
        System.out.println(
                "Dashboard rendered"
        );

        System.out.println(
                "DASHBOARD HIT"
        );

        return "student-dashboard";

        

    }


private void loadPerformanceData(

        Object studentId,

        Model model

) {

    String db =
            "jdbc:sqlite:database/srms.db";

    try (

            Connection conn =
                    DriverManager.getConnection(db)

    ) {

        String sql = """

SELECT

AVG(m.marks) AS average_marks,

COUNT(m.mark_id) AS total_subjects,

SUM(
CASE
WHEN m.marks >= 50
THEN 1
ELSE 0
END
) AS passed_subjects

FROM marks m

WHERE m.student_id = ?

""";

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ps.setObject(
                1,
                studentId
        );

        ResultSet rs =
                ps.executeQuery();

        if (rs.next()) {

            double average =
                    rs.getDouble(
                            "average_marks"
                    );

            int totalSubjects =
                    rs.getInt(
                            "total_subjects"
                    );

            int passedSubjects =
                    rs.getInt(
                            "passed_subjects"
                    );

            double passPercentage =
                    totalSubjects > 0

                            ?

                            ((double)
                                    passedSubjects
                                    /
                                    totalSubjects)
                                    * 100

                            :

                            0;

            String performance;

            if (average >= 90) {

                performance =
                        "Excellent";

            }

            else if (average >= 75) {

                performance =
                        "Very Good";

            }

            else if (average >= 60) {

                performance =
                        "Good";

            }

            else if (average >= 50) {

                performance =
                        "Average";

            }

            else {

                performance =
                        "Needs Improvement";

            }

            model.addAttribute(
                    "averageMarks",
                    String.format(
                            "%.2f",
                            average
                    )
            );

            model.addAttribute(
                    "totalSubjects",
                    totalSubjects
            );

            model.addAttribute(
                    "passPercentage",
                    String.format(
                            "%.2f%%",
                            passPercentage
                    )
            );

            model.addAttribute(
                    "performance",
                    performance
            );

        }

    }

    catch (Exception e) {

        System.out.println(
                "\n===== PERFORMANCE ERROR ====="
        );

        e.printStackTrace();

    }

}



    private boolean loadStudentData(

            Object studentId,

            Model model

    ) {

        String db =
                "jdbc:sqlite:database/srms.db";

        System.out.println(
                "\nConnecting DB..."
        );

        try (

                Connection conn =
                        DriverManager.getConnection(db)

        ) {

            System.out.println(
                    "DB Connected"
            );

            String sql = """


SELECT
s.student_id,
s.roll_number,
s.name,
s.dob,
s.gender,
d.department_name

FROM students s

LEFT JOIN departments d
ON s.department_id = d.id

WHERE s.user_id = ?

""";

            System.out.println(
                    "SQL = " + sql
            );

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setObject(
                    1,
                    studentId
            );

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                System.out.println(
                        "Student Found"
                );

                model.addAttribute(
                        "studentId",
                        rs.getObject(
                                "student_id"
                        )
                );

                model.addAttribute(
                        "rollNumber",
                        rs.getString(
                                "roll_number"
                        )
                );

                model.addAttribute(
                        "name",
                        rs.getString(
                                "name"
                        )
                );

                model.addAttribute(
                        "dob",
                        rs.getString(
                                "dob"
                        )
                );

                model.addAttribute(
                        "gender",
                        rs.getString(
                                "gender"
                        )
                );

                model.addAttribute(
                        "department",
                        rs.getString(
                                "department_name"
                        )
                );

                model.addAttribute(
                        "java",
                        95
                );

                model.addAttribute(
                        "dbms",
                        88
                );

                model.addAttribute(
                        "python",
                        91
                );

                System.out.println(
                        "Data injected"
                );

                return true;

            }

            System.out.println(
                    "No matching student"
            );

        }

        catch (Exception e) {

            System.out.println(
                    "\n===== ERROR ====="
            );

            e.printStackTrace();

        }

        return false;

    }



private Integer getStudentId(
        Object userId
) {

    String db =
            "jdbc:sqlite:database/srms.db";

    try (

            Connection conn =
                    DriverManager.getConnection(db)

    ) {

        String sql = """

SELECT student_id
FROM students
WHERE user_id = ?

""";

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ps.setObject(
                1,
                userId
        );

        ResultSet rs =
                ps.executeQuery();

        if (rs.next()) {

            return rs.getInt(
                    "student_id"
            );

        }

    }

    catch (Exception e) {

        e.printStackTrace();

    }

    return null;

}


private void loadMarksData(

        Object studentId,

        Model model

)


{

    List<Mark> marksList =
            new ArrayList<>();

    String db =
            "jdbc:sqlite:database/srms.db";

    try (

            Connection conn =
                    DriverManager.getConnection(db)

    ) {

        String sql = """

SELECT

sub.subject_name,

sub.semester,

m.marks

FROM marks m

JOIN subjects sub

ON m.subject_id = sub.id

WHERE m.student_id = ?

ORDER BY sub.semester

""";

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ps.setObject(
                1,
                studentId
        );

        ResultSet rs =
                ps.executeQuery();

        while (rs.next()) {

            marksList.add(

                    new Mark(

                            rs.getString(
                                    "subject_name"
                            ),

                            rs.getInt(
                                    "semester"
                            ),

                            rs.getInt(
                                    "marks"
                            )

                    )

            );

        }

        model.addAttribute(
                "marksList",
                marksList
        );
model.addAttribute(
        "highestMark",
        highestMark(marksList)
);


        System.out.println(
                "Marks Loaded = "
                + marksList.size()
        );
        int highest = 0;

for(Mark mark : marksList)
{
    if(mark.getMarks() > highest)
    {
        highest = mark.getMarks();
    }
}



    }

    catch (Exception e) {

        System.out.println(
                "\n===== MARKS ERROR ====="
        );

        e.printStackTrace();

    }

}



    private String resolveView(
            String view
    ) {

        if (
                view.equals(
                        "marks"
                )
        ) {

            return
                    "fragments/marks-content";

        }

        if (
                view.equals(
                        "performance"
                )
        ) {

            return
                    "fragments/performance-content";

        }

        return
                "fragments/profile-content";

    }

}