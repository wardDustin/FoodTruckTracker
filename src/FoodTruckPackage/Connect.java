package FoodTruckPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Connect{

    private Connection connect = null;
    private Statement statement = null;
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

    public void queryFoodTruck() throws Exception{
    	String queryDB = "SELECT * FROM FoodTruckTracker.FoodTruck";
        try{
                statement = connect.createStatement();
                resultSet = statement.executeQuery(queryDB);
                
        		while (resultSet.next()){
        			int Id = resultSet.getInt("Id");
        			String truckName = resultSet.getString("truckName");
        			String owner = resultSet.getString("owner");
        			String foodType = resultSet.getString("foodType");
        			
        			System.out.println("ID: " + Id + "  Truck: " + truckName + "  Owner: " + owner + "  Food Category: " + foodType);
        		}
        }
        catch (Exception e){
        	throw e;
        }
    }
    
    public void queryUser() throws Exception{
    	String queryDB = "SELECT * FROM FoodTruckTracker.User";
        try{
                statement = connect.createStatement();
                resultSet = statement.executeQuery(queryDB);
                
        		while (resultSet.next()){
        			String username = resultSet.getString("username");
        			String password = resultSet.getString("password");
        			String name = resultSet.getString("name");
        			String address = resultSet.getString("address");
        			String email = resultSet.getString("email");
        			
        			System.out.println("Username: " + username + "  Password: " + password + "  Name: " + name + "  Address: " + address + "  Email: " + email);
        		}
        }
        catch (Exception e){
        	throw e;
        }
    }
    
    public void insertDB(String insert) throws Exception{
    	
    	try{
            statement = connect.createStatement();
            statement.executeUpdate(insert);
	    }
	    catch (Exception e){
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
