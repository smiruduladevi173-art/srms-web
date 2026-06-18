package srms_web.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

public static Connection getConnection(){

    System.out.println("Working Dir = "
        + System.getProperty("user.dir"));

System.out.println("DB Exists = " +
        new java.io.File("srms.db").exists());

    System.out.println(
new java.io.File("srms.db").getAbsolutePath()
);

try{

return DriverManager.getConnection(
"jdbc:sqlite:srms.db"
);
public static void close(Connection con) {
    if (con != null) {
        try {
            con.close();
        } catch (Exception ignored) {}
    }
}

}

catch(Exception e){

throw new RuntimeException(
e
);

}

}



}
