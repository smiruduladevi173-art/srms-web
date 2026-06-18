package srms_web.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnection {

    public static Connection getConnection() {

        System.out.println(
            "Working Dir = " + System.getProperty("user.dir")
        );

        System.out.println(
            "DB Exists = " + new File("srms.db").exists()
        );

        System.out.println(
            new File("srms.db").getAbsolutePath()
        );

        try {

            Connection con = DriverManager.getConnection(
                "jdbc:sqlite:srms.db"
            );

            // FIX: Enable WAL mode so SQLite allows concurrent access
            //      instead of locking the entire file on every write.
            // FIX: busy_timeout tells SQLite to wait up to 5 seconds
            //      and retry if the DB is busy, instead of throwing
            //      "database is locked" immediately.
            try (Statement st = con.createStatement()) {
                st.execute("PRAGMA journal_mode=WAL;");
                st.execute("PRAGMA busy_timeout=5000;");
            }

            return con;

        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }

}