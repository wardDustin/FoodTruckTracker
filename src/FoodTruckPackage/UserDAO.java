package FoodTruckPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO{
	//TODO: This should be used for Create/ Read (get) / Update / Destroy
	private Connection connect;
	private PreparedStatement prepState;
	private ResultSet resultSet;
	private Connect database;
	
	public UserDAO(){
		database = new Connect();
		try {
			connect = database.connectToDB();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	 public boolean insert(User newUser) throws Exception{
		 String insertQuery = "INSERT INTO FoodTruckTracker.User (username, password, name, address, email) VALUES (?,?,?,?,?)";
		 try{
            prepState = connect.prepareStatement(insertQuery);
            prepState.setString(1, newUser.getUsername());
            prepState.setString(2, newUser.getPassword());
            prepState.setString(3, newUser.getName());
            prepState.setString(4, newUser.getAddress());
            prepState.setString(5, newUser.getEmail());
            prepState.executeUpdate();
            return true;
	    }
	    catch (SQLException e) {
	    	System.out.println(e);
	    	return false;
	    }
	 }
	 
	 public ResultSet select(String query, String input) throws Exception{
	    	prepState = connect.prepareStatement(query);
	    	prepState.setString(1, input);
	    	resultSet = prepState.executeQuery();
	    	return resultSet;
	    }
	 
	 public boolean update(String column, String newValue, String username){
		 String updateQuery = "UPDATE FoodTruckTracker.User SET ? = ? WHERE username = ?";
		 try{
			 prepState = connect.prepareStatement(updateQuery);
			 prepState.setString(1, column);
			 prepState.setString(2, newValue);
			 prepState.setString(3, username);
			 prepState.executeUpdate();
			 return true;
		 }
		 catch (SQLException e){
			 System.out.println(e);
			 return false;
		 }
	 }
	 
	 public boolean delete(String username){
		 try{
			 String deleteUser = "DELETE FROM FoodTruckTracker.User WHERE username = ?";
			 prepState = connect.prepareStatement(deleteUser);
			 prepState.setString(1, username);
			 prepState.executeUpdate();
			 return true;
		 }
		 catch (SQLException e){
			 System.out.println(e);
			 return false;
		 }
	 }
}