package srms_web.auth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Authentication {

    public static Integer login(

            String username,

            String password

    ) {

        String db =
                "jdbc:sqlite:database/srms.db";

        try (

                Connection conn =
                        DriverManager.getConnection(db)

        ) {

            username = username.trim();

            password = password.trim();

            String sql = """

                    SELECT *
                    FROM users
                    WHERE username = ?

                    """;

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(
                    1,
                    username
            );

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                String dbPassword =
                        rs.getString(
                                "password"
                        );

                String role =
                        rs.getString(
                                "role"
                        );

                int userId =
                        rs.getInt(
                                "id"
                        );

                boolean valid = false;

                if (

                        dbPassword != null

                        &&

                        dbPassword.startsWith(
                                "$argon2"
                        )

                ) {

                    valid =
                            PasswordUtil.verifyPassword(

                                    dbPassword,

                                    password

                            );

                }

                else {

                    valid =
                            password.equals(
                                    dbPassword
                            );

                }

                if (

                        valid

                        &&

                        role.equals(
                                "STUDENT"
                        )

                ) {

                    String studentSql = """

                            SELECT student_id
                            FROM students
                            WHERE user_id = ?

                            """;

                    PreparedStatement ps2 =
                            conn.prepareStatement(
                                    studentSql
                            );

                    ps2.setInt(
                            1,
                            userId
                    );

                    ResultSet rs2 =
                            ps2.executeQuery();

                    if (rs2.next()) {

                        return rs2.getInt(
                                "student_id"
                        );

                    }
                }
            }

        }

        catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }
}