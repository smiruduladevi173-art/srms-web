package srms_web.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

public static Connection getConnection(){
    System.out.println(
new java.io.File("srms.db").getAbsolutePath()
);

try{

return DriverManager.getConnection(
"jdbc:sqlite:srms.db"
);


}

catch(Exception e){

throw new RuntimeException(
e
);

}

}



}
