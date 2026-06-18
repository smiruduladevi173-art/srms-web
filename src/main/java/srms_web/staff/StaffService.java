package srms_web.staff;

import srms_web.database.DBConnection;
import srms_web.model.AnalyticsData;
import srms_web.model.AnalyticsSummary;
import srms_web.model.Department;
import srms_web.model.StudentInfo;
import srms_web.model.StudentMark;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import srms_web.model.Subject;




@Service
public class StaffService {


// =========================
// GET STUDENTS
// =========================

public List<StudentInfo> getAllStudents() {

    List<StudentInfo> students =
            new ArrayList<>();

    try {

        Connection con =
                DBConnection.getConnection();

        String sql =
                """
                SELECT

                s.student_id,

                s.roll_number,

                s.name,

                s.gender,

                s.dob,

                d.department_name

                FROM students s

                JOIN departments d

                ON s.department_id=d.id
                """;

        PreparedStatement pst =
                con.prepareStatement(
                        sql
                );

        ResultSet rs =
                pst.executeQuery();

              

        while (

                rs.next()

        ) {

            StudentInfo student =
                    new StudentInfo();

            student.setStudentId(

                    rs.getInt(
                            "student_id"
                    )

            );

            student.setRollNumber(

                    rs.getString(
                            "roll_number"
                    )

            );

            student.setName(

                    rs.getString(
                            "name"
                    )

            );

            student.setGender(

                    rs.getString(
                            "gender"
                    )

            );

            student.setDob(

                    rs.getString(
                            "dob"
                    )

            );

            student.setDepartment(

                    rs.getString(
                            "department_name"
                    )

            );

            students.add(
                    student
            );

        }

       
  {
    con.close();
}

    }

    

    catch (

            Exception e

    ) {

        e.printStackTrace();

    }
    
    

    

    return students;

    

}

// =========================
// SAVE ALL
// =========================

public void updateStudents(

List<String> rollNumbers,

List<String> names,

List<String> genders,

List<String> dobs

){

try{

Connection con =
DBConnection.getConnection();

String sql =
"""
UPDATE students

SET

name=?,
gender=?,
dob=?

WHERE roll_number=?

""";

PreparedStatement pst =
con.prepareStatement(
sql
);

for(

int i=0;

i<rollNumbers.size();

i++

){

pst.setString(
1,
names.get(i)
);

pst.setString(
2,
genders.get(i)
);

pst.setString(
3,
dobs.get(i)
);

pst.setString(
4,
rollNumbers.get(i)
);

pst.executeUpdate();

}

 {
    con.close();
}

}



catch(Exception e){

e.printStackTrace();

}

}

/*================================
STUDENT MARKS
================================== */
public List<StudentMark>
loadMarks(
int departmentId,
int subjectId
){

List<StudentMark> list =
new ArrayList<>();

try{

Connection con =
DBConnection.getConnection();

String sql =
"""
SELECT
s.student_id,
s.roll_number,
s.name,
m.marks

FROM students s

LEFT JOIN marks m
ON s.student_id = m.student_id
AND m.subject_id = ?

WHERE s.department_id = ?

ORDER BY s.roll_number
""";

PreparedStatement pst =
con.prepareStatement(sql);

pst.setInt(
1,
subjectId
);

pst.setInt(
2,
departmentId
);

ResultSet rs =
pst.executeQuery();

while(rs.next()){

StudentMark row =
new StudentMark();

row.setStudentId(
rs.getInt(
"student_id"
)
);

row.setRollNumber(
rs.getString(
"roll_number"
)
);

row.setName(
rs.getString(
"name"
)
);

row.setMarks(
(Integer)rs.getObject(
"marks"
)
);

list.add(row);

}

System.out.println(
"Students Loaded = "
+ list.size()
);
 {
    con.close();
}
}
catch(Exception e){

e.printStackTrace();

}

return list;

}

/*================================
subject
=================================== */
public List<Subject>
getSubjects(

int departmentId

){

List<Subject>
list=
new ArrayList<>();

try{

/* DEBUG */

System.out.println(
"Department = "
+
departmentId
);

Connection con=
DBConnection
.getConnection();

String sql=
"""
SELECT

id,

subject_name

FROM subjects

WHERE department_id=?
""";

PreparedStatement pst=
con.prepareStatement(
sql
);

pst.setInt(
1,
departmentId
);

ResultSet rs=
pst.executeQuery();

while(

rs.next()

){

/* DEBUG */

System.out.println(

"Subject = "

+

rs.getString(
"subject_name"
)

);

Subject s=
new Subject();

s.setId(

rs.getInt(
"id"
)

);

s.setSubjectName(

rs.getString(
"subject_name"
)

);

list.add(
s
);

}

System.out.println(

"Subjects Loaded = "

+

list.size()

);

 {
    con.close();
}

}


catch(

Exception e

){

e.printStackTrace();

}


return list;

}

//=========================
// DEPARTMENTS
//=========================
public List<Department> getDepartments(){

    List<Department> list =
    new ArrayList<>();

    try{

        Connection con =
        DBConnection.getConnection();

        String sql =
        """
        SELECT
        id,
        department_name
        FROM departments
        """;

        PreparedStatement pst =
        con.prepareStatement(sql);

        ResultSet rs =
        pst.executeQuery();

        while(rs.next()){

            Department d =
            new Department();

            d.setId(
                rs.getInt("id")
            );

            d.setDepartmentName(
                rs.getString(
                    "department_name"
                )
            );

            list.add(d);
             {
    con.close();
}

        }

    }catch(Exception e){

        e.printStackTrace();

    }

    return list;

}
//=========================
// SAVE MARKS
//=========================
public void saveMark(
int studentId,
int subjectId,
int marks
){

try(

Connection con =
DBConnection.getConnection()

){

String checkSql =
"""
SELECT mark_id
FROM marks
WHERE student_id=?
AND subject_id=?
""";

try(

PreparedStatement check =
con.prepareStatement(checkSql)

){

check.setInt(1,studentId);
check.setInt(2,subjectId);

try(

ResultSet rs =
check.executeQuery()

){

if(rs.next()){

String updateSql =
"""
UPDATE marks
SET marks=?
WHERE student_id=?
AND subject_id=?
""";

try(

PreparedStatement pst =
con.prepareStatement(updateSql)

){

pst.setInt(1,marks);
pst.setInt(2,studentId);
pst.setInt(3,subjectId);

pst.executeUpdate();

}

}else{

String insertSql =
"""
INSERT INTO marks(
student_id,
subject_id,
marks
)
VALUES(
?,
?,
?
)
""";

try(

PreparedStatement pst =
con.prepareStatement(insertSql)

){

pst.setInt(1,studentId);
pst.setInt(2,subjectId);
pst.setInt(3,marks);

pst.executeUpdate();

}

}

}

}
 {
    con.close();
}

}
catch(Exception e){

e.printStackTrace();

}

}

//=========================
//GET ANALYTICS SUMMARY
//=========================
public AnalyticsSummary getAnalyticsSummary(
        int departmentId
) {

    AnalyticsSummary summary =
            new AnalyticsSummary();

            

    try {

        Connection con =
                DBConnection.getConnection();

        String sql =
                """
                SELECT
                    COUNT(DISTINCT s.student_id) total_students,
                    AVG(m.marks) average_marks,
                    MAX(m.marks) highest_mark,
                    MIN(m.marks) lowest_mark,

                    (
                        SUM(
                            CASE
                                WHEN m.marks >= 50
                                THEN 1
                                ELSE 0
                            END
                        ) * 100.0
                    ) / COUNT(*) pass_percentage

                FROM students s

                JOIN marks m
                ON s.student_id = m.student_id

                WHERE s.department_id = ?
                """;

        PreparedStatement pst =
                con.prepareStatement(sql);

        pst.setInt(1, departmentId);

        ResultSet rs =
                pst.executeQuery();

        if(rs.next()) {

            summary.setTotalStudents(
                    rs.getInt("total_students")
            );

            summary.setAverageMarks(
                    rs.getDouble("average_marks")
            );

            summary.setHighestMark(
                    rs.getInt("highest_mark")
            );

            summary.setLowestMark(
                    rs.getInt("lowest_mark")
            );

            summary.setPassPercentage(
                    rs.getDouble("pass_percentage")
            );
        }
         {
    con.close();
}

    }
    catch(Exception e) {

        e.printStackTrace();

    }

    return summary;

  
}

//=========================
//TOP STUDENT BY DEPARTMENT
//=========================
public List<AnalyticsData>
getTopStudentsByDepartment(
        int departmentId
) {

    List<AnalyticsData> list =
            new ArrayList<>();

    try {

        Connection con =
                DBConnection.getConnection();

        String sql =
                """
                SELECT
                    s.name,
                    AVG(m.marks) average

                FROM students s

                JOIN marks m
                ON s.student_id = m.student_id

                WHERE s.department_id = ?

                GROUP BY
                    s.student_id,
                    s.name

                ORDER BY average DESC

                LIMIT 5
                """;

        PreparedStatement pst =
                con.prepareStatement(sql);

        pst.setInt(1, departmentId);

        ResultSet rs =
                pst.executeQuery();

        while(rs.next()) {

            AnalyticsData data =
                    new AnalyticsData();

            data.setStudentName(
                    rs.getString("name")
            );

            data.setAverage(
                    rs.getDouble("average")
            );

            list.add(data);
        }

    }
    catch(Exception e) {

        e.printStackTrace();

    }

    return list;
}






}