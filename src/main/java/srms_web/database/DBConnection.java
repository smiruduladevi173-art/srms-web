package srms_web.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

public static Connection getConnection(){

try{

return DriverManager.getConnection(
"jdbc:sqlite:databse/srms.db"
);


}

catch(Exception e){

throw new RuntimeException(
e
);

}

}

}
