package srms_web.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import srms_web.database.DBConnection;
import srms_web.model.Department;

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

}