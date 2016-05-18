package FoodTruckPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Connect{

        private Connection connect = null;
        private Statement statement = null;
        private PreparedStatement preparedStatement = null;
        private ResultSet resultSet = null;

        final private String host = "localhost";
        final private String user = "root";
        final private String password = "MUtants13";
        final private String database = "FoodTruckTracker";

        public void connectToDB() throws Exception {
                try {
                        // This will load the MySQL driver, each DB has its own driver
                        Class.forName("com.mysql.jdbc.Driver");

                        // Setup the connection with the DB
                        connect = DriverManager.getConnection("jdbc:mysql://" + host + "/"
                                        + database + "?" + "user=" + user + "&password=" + password);

                } catch (Exception e) {
                        throw e;
                }
        }

        public void readStudents() throws Exception {
                try {
                        statement = connect.createStatement();
                        resultSet = statement.executeQuery("");
                        
                        while (resultSet.next()) {
//	                                int Id = resultSet.getInt("");
//	                                String name = resultSet.getString("");

                                System.out.println();
                        }
                } catch (Exception e) {
                        throw e;
                }
        }

        // You need to close the resultSet
        public void close() {
                try {
                        if (resultSet != null) {
                                resultSet.close();
                        }

                        if (statement != null) {
                                statement.close();
                        }

                        if (connect != null) {
                                connect.close();
                        }
                }catch (Exception e) {

                }
        }

}
