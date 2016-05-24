package FoodTruckPackage;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.mindrot.jbcrypt.BCrypt;

public class Connect{

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement prepState = null;
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
    
    public ResultSet prepSelectQuery(String query, String input) throws SQLException{
    	prepState = connect.prepareStatement(query);
    	prepState.setString(1, input);
    	resultSet = prepState.executeQuery();
    	return resultSet;
    }
    
    /*
	 * BCrypt is a great encryption tool that uses a complex hash function
	 * but also salts the password... 
	 */
    public String encryptPW(String passwd){
    	String hashed = BCrypt.hashpw(passwd, BCrypt.gensalt());
		System.out.println(hashed);
		
		return hashed;
    }
    
    /*
	 * This if/else statement simply confirms  or denies that the given password
	 * matches a hashed password... if it returns true, the passwords are the same
	 * if it returns false, the passwords are not the same
	 */
    public boolean passwordMatch(String passwd, String hashed){
    	if (BCrypt.checkpw(passwd, hashed))
			return true;
		else
			return false;
    }
    
    public boolean insertUser(String insert, String username, String password, String name, String address, String email) throws Exception{
    	
    	try{
            prepState = connect.prepareStatement(insert);
            prepState.setString(1, username);
            prepState.setString(2, password);
            prepState.setString(3, name);
            prepState.setString(4, address);
            prepState.setString(5, email);
            prepState.executeUpdate();
            return true;
	    }
	    catch (SQLException e) {
	    	System.out.println(e);
	    	return false;
	    }
    }
    
    public boolean insertFoodTruck(String insertQuery, String truckName, String pw, String owner, String foodType){
    	try{
            prepState = connect.prepareStatement(insertQuery);
            prepState.setString(1, truckName);
            prepState.setString(2, pw);
            prepState.setString(3, owner);
            prepState.setString(4, foodType);
            prepState.executeUpdate();
            return true;
	    }
	    catch (SQLException e) {
	    	System.out.println(e);
	    	return false;
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
