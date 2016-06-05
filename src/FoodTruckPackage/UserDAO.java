package FoodTruckPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO{
	//TODO: This should be used for Create/ Read (get) / Update / Destroy
	private Connection connect;
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
		 try(PreparedStatement prepState = connect.prepareStatement(insertQuery)){
			prepState.setString(1, newUser.getUsername());
            prepState.setString(2, newUser.getPassword());
            prepState.setString(3, newUser.getName());
            prepState.setString(4, newUser.getAddress());
            prepState.setString(5, newUser.getEmail());
            prepState.executeUpdate();
	    }
	    catch (SQLException e) {
	    	System.out.println(e);
	    	return false;
	    }
		 return true;
	 }
	 
	 public boolean selectUsername(String username) throws Exception{
		String usernameQuery = "SELECT username FROM FoodTruckTracker.User WHERE username = ?";
		try(PreparedStatement prepState = connect.prepareStatement(usernameQuery)){
			prepState.setString(1, username);
			try (ResultSet rs = prepState.executeQuery()){
				if (rs.next()){
					return true;
				}
			}
		}
		catch(SQLException e){
			System.out.println(e);
		}
		return false;
	 }
	 
	 public String selectPW(String passwordQuery, String username) throws Exception{
		String hashedpw = null;
		try(PreparedStatement prepState = connect.prepareStatement(passwordQuery)){
			prepState.setString(1, username);
			try(ResultSet rs = prepState.executeQuery()){
				if (rs.next()){
					hashedpw = rs.getString("password");
				}
			}
		}
		catch(SQLException e){
			System.out.println(e);
		}
		return hashedpw;
	 }
	 
	 public boolean update(String column, String newValue, String username){
		 String updateQuery = "UPDATE FoodTruckTracker.User SET ? = ? WHERE username = ?";
		 try(PreparedStatement prepState = connect.prepareStatement(updateQuery)){
			 prepState.setString(1, column);
			 prepState.setString(2, newValue);
			 prepState.setString(3, username);
			 prepState.executeUpdate();
		 }
		 catch (SQLException e){
			 System.out.println(e);
			 return false;
		 }
		 return true;
	 }
	 
	 public boolean delete(String username){
		 String deleteUser = "DELETE FROM FoodTruckTracker.User WHERE username = ?";
		 try(PreparedStatement prepState = connect.prepareStatement(deleteUser)){
			prepState.setString(1, username);
			prepState.executeUpdate();
		 }
		 catch (SQLException e){
			 System.out.println(e);
			 return false;
		 }
		 return true;
	 }
}
