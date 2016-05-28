package FoodTruckPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Connect{
//TODO: take out the SQL queries and put in DAO classes
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement prepState = null;
    private ResultSet resultSet = null;
    
    final private String host = "localhost";
    final private String user = "root";
    final private String password = "MUtants13";
    final private String database = "FoodTruckTracker";

    public Connection connectToDB() throws Exception {
        try {
                // This will load the MySQL driver, each DB has its own driver
                Class.forName("com.mysql.jdbc.Driver");

                // Setup the connection with the DB
                connect = DriverManager.getConnection("jdbc:mysql://" + host + "/"
                                + database + "?" + "user=" + user + "&password=" + password);
                
                return connect;

        } catch (Exception e) {
                throw e;
        }
    }
    
    //close the resultSet
    public void close() {
        try {
                if (resultSet != null) {
                	resultSet.close();
                }

                if (statement != null) {
                	statement.close();
                }
                
                if (prepState != null){
                	prepState.close();
                }

                if (connect != null) {
                	connect.close();
                }
        }catch (Exception e) {

        }
    }

}
