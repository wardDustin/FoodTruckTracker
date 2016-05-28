package FoodTruckPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO{
	//TODO: This should be used for Create/ Read (get) / Update / Destroy
	private Connection connect = null;
	private PreparedStatement prepState = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private Connect database;
	
	public UserDAO(){
		database = new Connect();
		try {
			connect = database.connectToDB();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void query() throws Exception{
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
	
	public ResultSet prepSelectQuery(String query, String input) throws Exception{
    	prepState = connect.prepareStatement(query);
    	prepState.setString(1, input);
    	resultSet = prepState.executeQuery();
    	return resultSet;
    }
	
	 public boolean insert(String insert, String username, String password, String name, String address, String email) throws Exception{
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
	

}
