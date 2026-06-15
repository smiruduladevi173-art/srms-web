package srms_web.staff;

import srms_web.database.DBConnection;
import srms_web.model.StudentInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

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

}

catch(Exception e){

e.printStackTrace();

}

}
}