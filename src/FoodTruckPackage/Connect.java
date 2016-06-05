package FoodTruckPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect{
//TODO: take out the SQL queries and put in DAO classes
    private Connection connect = null;
    
    final private String host = "localhost";
    final private String user = "root";
    final private String password = "MUtants13";
    final private String database = "FoodTruckTracker";

    public Connection connectToDB(){
        try {
                // This will load the MySQL driver, each DB has its own driver
                Class.forName("com.mysql.jdbc.Driver");

                // Setup the connection with the DB
                connect = DriverManager.getConnection("jdbc:mysql://" + host + "/"
                                + database + "?" + "user=" + user + "&password=" + password);

        } catch (SQLException e) {
        	e.printStackTrace();
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connect;
    }
}
