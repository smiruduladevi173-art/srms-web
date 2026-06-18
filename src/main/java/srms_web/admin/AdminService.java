package srms_web.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import srms_web.database.DBConnection;
import srms_web.model.AnalyticsSummary;
import srms_web.model.Department;
import srms_web.model.Subject;
import srms_web.model.TopStudent;
import srms_web.auth.PasswordUtil;
import srms_web.model.Student;
import srms_web.model.StudentInfo;


@Service
public class AdminService {

    //=========================
    // GET DEPARTMENTS
    //=========================
    public List<Department> getAllDepartments() {

        List<Department> departments = new ArrayList<>();

        String sql =
                """
                SELECT
                    d.id,
                    d.department_name,
                    COUNT(s.student_id) AS student_count
                FROM departments d
                LEFT JOIN students s ON d.id = s.department_id
                GROUP BY d.id, d.department_name
                ORDER BY d.department_name
                """;

        // FIX: con, ps, rs all declared in try-with-resources — auto-closed in order.
        //      Removed the erroneous con.close() that was inside the while-loop.
        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Department department = new Department();
                department.setId(rs.getInt("id"));
                department.setDepartmentName(rs.getString("department_name"));
                department.setStudentCount(rs.getInt("student_count"));
                departments.add(department);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return departments;
    }


    //=========================
    // ADD DEPARTMENT
    //=========================
    public void addDepartment(String departmentName) {

        String sql =
                """
                INSERT INTO departments(department_name)
                VALUES(?)
                """;

        // FIX: Removed the erroneous con.close() block inside try-with-resources.
        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, departmentName);
            ps.executeUpdate();
            System.out.println("Department Added");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //=========================
    // CHECK DELETE
    //=========================
    public boolean canDeleteDepartment(int departmentId) {

        String sql =
                """
                SELECT COUNT(*)
                FROM students
                WHERE department_id = ?
                """;

        // FIX: Inner ResultSet moved into nested try-with-resources for proper closure.
        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, departmentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    //=========================
    // DELETE DEPARTMENT
    //=========================
    public void deleteDepartment(int id) {

        String sql =
                """
                DELETE FROM departments
                WHERE id = ?
                """;

        // FIX: Removed erroneous con.close() block inside try-with-resources.
        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //=====================
    // GET SUBJECTS
    //=====================
    public List<Subject> getSubjects(int departmentId, int semester) {

        List<Subject> list = new ArrayList<>();

        String sql =
                """
                SELECT s.id, s.subject_name
                FROM subjects s
                WHERE department_id = ?
                  AND semester = ?
                ORDER BY subject_name
                """;

        // FIX: Inner ResultSet moved into nested try-with-resources.
        //      Removed erroneous con.close() block inside the inner try.
        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, departmentId);
            ps.setInt(2, semester);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Subject subject = new Subject();
                    subject.setId(rs.getInt("id"));
                    subject.setSubjectName(rs.getString("subject_name"));
                    list.add(subject);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    //=====================
    // ADD SUBJECT
    //=====================
    public void addSubject(int departmentId, int semester, String subjectName) {

        String sql =
                """
                INSERT INTO subjects(department_id, semester, subject_name)
                VALUES(?, ?, ?)
                """;

        // FIX: Removed erroneous con.close() block inside try-with-resources.
        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, departmentId);
            ps.setInt(2, semester);
            ps.setString(3, subjectName);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //=====================
    // DELETE SUBJECT
    //=====================
    public boolean deleteSubject(int subjectId) {

        // FIX: CRITICAL — original code called con.close() after ps1 executed but
        //      BEFORE ps2 ran, so ps2 always operated on a closed connection.
        //      Removed all manual con.close() calls; try-with-resources handles it.
        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            // DELETE MARKS
            try (PreparedStatement ps1 = con.prepareStatement(
                    "DELETE FROM marks WHERE subject_id = ?")) {
                ps1.setInt(1, subjectId);
                ps1.executeUpdate();
            }

            // DELETE SUBJECT
            int deleted;
            try (PreparedStatement ps2 = con.prepareStatement(
                    "DELETE FROM subjects WHERE id = ?")) {
                ps2.setInt(1, subjectId);
                deleted = ps2.executeUpdate();
            }

            con.commit();
            return deleted > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    //=====================
    // GET STUDENTS
    //=====================
    public List<StudentInfo> getStudents(String search) {

        List<StudentInfo> students = new ArrayList<>();

        if (search == null) {
            search = "";
        }

        String sql =
                """
                SELECT
                    s.student_id,
                    s.roll_number,
                    s.name,
                    s.dob,
                    s.gender,
                    d.department_name
                FROM students s
                JOIN departments d ON s.department_id = d.id
                WHERE LOWER(s.roll_number) LIKE LOWER(?)
                   OR LOWER(s.name)        LIKE LOWER(?)
                ORDER BY s.roll_number
                """;

        // FIX: Inner ResultSet moved into nested try-with-resources.
        //      Removed erroneous con.close() inside the inner try block.
        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            String keyword = "%" + search + "%";
            ps.setString(1, keyword);
            ps.setString(2, keyword);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StudentInfo student = new StudentInfo();
                    student.setStudentId(rs.getInt("student_id"));
                    student.setRollNumber(rs.getString("roll_number"));
                    student.setName(rs.getString("name"));
                    student.setDob(rs.getString("dob"));
                    student.setGender(rs.getString("gender"));
                    student.setDepartment(rs.getString("department_name"));
                    students.add(student);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }


    //=====================
    // ROLL EXISTS
    //=====================
    public boolean rollExists(String rollNo) {

        String sql =
                """
                SELECT COUNT(*)
                FROM students
                WHERE roll_number = ?
                """;

        // FIX: ResultSet was not in a try-with-resources — added nested try-with-resources.
        //      Removed misplaced con.close() after the if-block.
        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, rollNo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    //=====================
    // USER EXISTS
    //=====================
    public boolean usernameExists(String username) {

        String sql =
                """
                SELECT COUNT(*)
                FROM users
                WHERE username = ?
                """;

        // FIX: Same as rollExists — ResultSet wrapped in nested try-with-resources.
        //      Removed misplaced con.close() after the if-block.
        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    //=====================
    // ADD STUDENT
    //=====================
    public String addStudent(Student student) {

        if (rollExists(student.getRollNumber()) && usernameExists(student.getUsername())) {
            return "Both Roll Number and Username already exist";
        }
        if (rollExists(student.getRollNumber())) {
            return "Roll Number already exists";
        }
        if (usernameExists(student.getUsername())) {
            return "Username already exists";
        }

        // FIX: CRITICAL — original code called con.close() right after getting
        //      generated keys from ps1, before ps2 ran. That closed the connection
        //      and made ps2.executeUpdate() throw on a closed connection.
        //      All manual con.close() calls removed; try-with-resources handles it.
        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            String hash = PasswordUtil.hashPassword(student.getPassword());

            // INSERT USER
            int userId;
            try (PreparedStatement ps1 = con.prepareStatement(
                    """
                    INSERT INTO users(username, password, role, department_id)
                    VALUES(?, ?, 'STUDENT', ?)
                    """,
                    PreparedStatement.RETURN_GENERATED_KEYS)) {

                ps1.setString(1, student.getUsername());
                ps1.setString(2, hash);
                ps1.setInt(3, student.getDepartmentId());
                ps1.executeUpdate();

                try (ResultSet userKey = ps1.getGeneratedKeys()) {
                    userId = userKey.next() ? userKey.getInt(1) : 0;
                }
            }

            // INSERT STUDENT
            try (PreparedStatement ps2 = con.prepareStatement(
                    """
                    INSERT INTO students(roll_number, name, dob, gender, department_id, user_id)
                    VALUES(?, ?, ?, ?, ?, ?)
                    """)) {

                ps2.setString(1, student.getRollNumber());
                ps2.setString(2, student.getName());
                ps2.setString(3, student.getDob());
                ps2.setString(4, student.getGender());
                ps2.setInt(5, student.getDepartmentId());
                ps2.setInt(6, userId);
                ps2.executeUpdate();
            }

            con.commit();

            return student.getRollNumber() + " - " + student.getName() + " added successfully";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Failed";
    }


    //=====================
    // DELETE STUDENT
    //=====================
    public boolean deleteStudent(int studentId) {

        // FIX: CRITICAL — original code called con.close() right after reading
        //      the user_id ResultSet, before the three DELETE statements ran.
        //      That closed the connection immediately, so p1/p2/p3 always failed.
        //      All manual con.close() calls removed; try-with-resources handles it.
        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            // GET USER ID
            int userId = 0;
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT user_id FROM students WHERE student_id = ?")) {
                ps.setInt(1, studentId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        userId = rs.getInt("user_id");
                    }
                }
            }

            // DELETE MARKS
            try (PreparedStatement p1 = con.prepareStatement(
                    "DELETE FROM marks WHERE student_id = ?")) {
                p1.setInt(1, studentId);
                p1.executeUpdate();
            }

            // DELETE STUDENT
            try (PreparedStatement p2 = con.prepareStatement(
                    "DELETE FROM students WHERE student_id = ?")) {
                p2.setInt(1, studentId);
                p2.executeUpdate();
            }

            // DELETE USER
            try (PreparedStatement p3 = con.prepareStatement(
                    "DELETE FROM users WHERE id = ?")) {
                p3.setInt(1, userId);
                p3.executeUpdate();
            }

            con.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    //==========================
    // GET ANALYTICS SUMMARY
    //==========================
    public AnalyticsSummary getAnalyticsSummary() {

        AnalyticsSummary summary = new AnalyticsSummary();

        // FIX: Each ResultSet is now wrapped in its own try-with-resources.
        //      Removed the misplaced con.close() at the end of the try block
        //      (try-with-resources already handles it).
        try (Connection con = DBConnection.getConnection()) {

            // TOTAL STUDENTS
            try (PreparedStatement ps1 = con.prepareStatement("SELECT COUNT(*) FROM students");
                 ResultSet rs1 = ps1.executeQuery()) {
                if (rs1.next()) {
                    summary.setTotalStudents(rs1.getInt(1));
                }
            }

            // TOTAL DEPARTMENTS
            try (PreparedStatement ps2 = con.prepareStatement("SELECT COUNT(*) FROM departments");
                 ResultSet rs2 = ps2.executeQuery()) {
                if (rs2.next()) {
                    summary.setTotalDepartments(rs2.getInt(1));
                }
            }

            // TOTAL SUBJECTS
            try (PreparedStatement ps3 = con.prepareStatement("SELECT COUNT(*) FROM subjects");
                 ResultSet rs3 = ps3.executeQuery()) {
                if (rs3.next()) {
                    summary.setTotalSubjects(rs3.getInt(1));
                }
            }

            // OVERALL AVERAGE
            try (PreparedStatement ps4 = con.prepareStatement("SELECT AVG(marks) FROM marks");
                 ResultSet rs4 = ps4.executeQuery()) {
                if (rs4.next()) {
                    summary.setOverallAverage(rs4.getDouble(1));
                }
            }

            // STUDENTS CLEARED (all subjects >= 35)
            try (PreparedStatement ps5 = con.prepareStatement(
                    """
                    SELECT COUNT(*)
                    FROM (
                        SELECT student_id
                        FROM marks
                        GROUP BY student_id
                        HAVING MIN(marks) >= 35
                    )
                    """);
                 ResultSet rs5 = ps5.executeQuery()) {
                if (rs5.next()) {
                    summary.setPassedStudents(rs5.getInt(1));
                }
            }

            // STUDENTS WITH ARREARS
            summary.setFailedStudents(
                    summary.getTotalStudents() - summary.getPassedStudents()
            );

            System.out.println("SUMMARY GENERATED");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return summary;
    }


    //=========================
    // GET TOP STUDENTS
    //=========================
    public List<TopStudent> getTopStudents() {

        List<TopStudent> students = new ArrayList<>();

        String sql =
                """
                SELECT
                    s.roll_number,
                    s.name,
                    d.department_name,
                    AVG(m.marks) AS average_marks
                FROM students s
                JOIN marks m       ON s.student_id  = m.student_id
                JOIN departments d ON s.department_id = d.id
                GROUP BY s.student_id
                ORDER BY average_marks DESC
                LIMIT 10
                """;

        // FIX: Removed erroneous con.close() that appeared after the while-loop
        //      inside try-with-resources. Already auto-closed.
        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                TopStudent student = new TopStudent();
                student.setRollNumber(rs.getString("roll_number"));
                student.setName(rs.getString("name"));
                student.setDepartment(rs.getString("department_name"));
                student.setAverage(rs.getDouble("average_marks"));
                students.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }
}