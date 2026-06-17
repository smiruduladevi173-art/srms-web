package srms_web.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import srms_web.database.DBConnection;
import srms_web.model.Department;
import srms_web.model.Subject;

@Service
public class AdminService {


//=========================
// GET DEPARTMENTS
//=========================
public List<Department> getAllDepartments(){

List<Department> departments =
new ArrayList<>();

String sql=
"""
SELECT
d.id,
d.department_name,
COUNT(s.student_id)
AS student_count

FROM departments d

LEFT JOIN students s
ON d.id=s.department_id

GROUP BY
d.id,
d.department_name

ORDER BY
d.department_name
""";


try(

Connection con=
DBConnection.getConnection();

PreparedStatement ps=
con.prepareStatement(sql);

ResultSet rs=
ps.executeQuery()

){

while(rs.next()){

Department department=
new Department();

department.setId(
rs.getInt("id")
);

department.setDepartmentName(
rs.getString(
"department_name"
)
);

department.setStudentCount(
rs.getInt(
"student_count"
)
);

departments.add(
department
);

}

}
catch(Exception e){

e.printStackTrace();

}

return departments;

}



//=========================
// ADD
//=========================

public void addDepartment(
String departmentName
){

String sql=
"""
INSERT INTO departments(
department_name
)
VALUES(?)
""";

try(

Connection con=
DBConnection.getConnection();

PreparedStatement ps=
con.prepareStatement(sql)

){

ps.setString(
1,
departmentName
);

ps.executeUpdate();

System.out.println(
"Department Added"
);

}
catch(Exception e){

e.printStackTrace();

}

}



//=========================
// CHECK DELETE
//=========================

public boolean canDeleteDepartment(
int departmentId
){

String sql=
"""
SELECT COUNT(*)

FROM students

WHERE department_id=?
""";

try(

Connection con=
DBConnection.getConnection();

PreparedStatement ps=
con.prepareStatement(sql)

){

ps.setInt(
1,
departmentId
);

try(

ResultSet rs=
ps.executeQuery()

){

if(rs.next()){

return rs.getInt(1)
== 0;

}

}

}
catch(Exception e){

e.printStackTrace();

}

return false;

}



//=========================
// DELETE
//=========================

public void deleteDepartment(
int id
){

String sql=
"""
DELETE FROM departments
WHERE id=?
""";


try(

Connection con=
DBConnection.getConnection();

PreparedStatement ps=
con.prepareStatement(sql)

){

ps.setInt(
1,
id
);

ps.executeUpdate();

}
catch(Exception e){

e.printStackTrace();

}

}
//=====================
// GET SUBJECTS
//=====================

public List<Subject>
getSubjects(
int departmentId,
int semester
){

List<Subject> list=
new ArrayList<>();

String sql=
"""
SELECT
s.id,
s.subject_name

FROM subjects s

WHERE
department_id=?

AND semester=?

ORDER BY
subject_name
""";


try(

Connection con=
DBConnection.getConnection();

PreparedStatement ps=
con.prepareStatement(sql)

){

ps.setInt(
1,
departmentId
);

ps.setInt(
2,
semester
);

try(

ResultSet rs=
ps.executeQuery()

){

while(rs.next()){

Subject subject=
new Subject();

subject.setId(
rs.getInt(
"id"
)
);

subject.setSubjectName(
rs.getString(
"subject_name"
)
);

list.add(
subject
);

}

}

}
catch(Exception e){

e.printStackTrace();

}

return list;

}



//=====================
// ADD SUBJECT
//=====================

public void addSubject(

int departmentId,

int semester,

String subjectName

){

String sql=
"""
INSERT INTO subjects(

department_id,
semester,
subject_name

)

VALUES(

?,
?,
?

)
""";

try(

Connection con=
DBConnection.getConnection();

PreparedStatement ps=
con.prepareStatement(sql)

){

ps.setInt(
1,
departmentId
);

ps.setInt(
2,
semester
);

ps.setString(
3,
subjectName
);

ps.executeUpdate();

}
catch(Exception e){

e.printStackTrace();

}

}



//=====================
// DELETE SUBJECT
//=====================

public boolean deleteSubject(
int subjectId
){

try(

Connection con=
DBConnection.getConnection()

){

con.setAutoCommit(false);


// DELETE MARKS

PreparedStatement ps1=
con.prepareStatement(
"""
DELETE FROM marks
WHERE subject_id=?
"""
);

ps1.setInt(
1,
subjectId
);

ps1.executeUpdate();


// DELETE SUBJECT

PreparedStatement ps2=
con.prepareStatement(
"""
DELETE FROM subjects
WHERE id=?
"""
);

ps2.setInt(
1,
subjectId
);

int deleted=
ps2.executeUpdate();

con.commit();

return deleted>0;

}
catch(Exception e){

e.printStackTrace();

}

return false;

}
}