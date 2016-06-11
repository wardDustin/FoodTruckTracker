package FoodTruckPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO{
	//TODO: This should be used for Create/ Read (get) / Update / Destroy
	private Connect database;
	
	public UserDAO(){
		database = new Connect();
	}
	
	 public boolean insert(User newUser){
		 String insertQuery = "INSERT INTO FoodTruckTracker.User (username, password, name, address, email) VALUES (?,?,?,?,?)";
		 try(Connection connect = database.connectToDB();
				 PreparedStatement prepState = connect.prepareStatement(insertQuery)){
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
	 
	 public User selectAll(String username){
		 String query = "SELECT Id, username, password, name, address, email FROM FoodTruckTracker.User WHERE username = ?";
		 User user = new User();
		 try(Connection connect = database.connectToDB();
				 PreparedStatement prepState = connect.prepareStatement(query)){
			 prepState.setString(1, username);
			 try(ResultSet rs = prepState.executeQuery()){
				 while (rs.next()){
					 user.setId(rs.getInt("Id"));
					 user.setUsername(rs.getString("username"));
					 user.setPassword(rs.getString("password"));
					 user.setName(rs.getString("name"));
					 user.setAddress(rs.getString("address"));
					 user.setEmail(rs.getString("email"));
					 
					 System.out.println("\n" + user);
				 }
			 }
		 }
		 catch (SQLException e){
			 e.printStackTrace();
		 }
		 return user;
	 }
	 
	 /*
	  * This checks the database to see if a user's name has been taken already
	  */
	 public boolean selectUsername(String newUsername){
		String usernameQuery = "SELECT username FROM FoodTruckTracker.User WHERE username = ?";
		try(Connection connect = database.connectToDB();
				PreparedStatement prepState = connect.prepareStatement(usernameQuery)){
			prepState.setString(1, newUsername);
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
	 
	 public String selectPW(String passwordQuery, String username){
		String hashedpw = null;
		try(Connection connect = database.connectToDB();
				PreparedStatement prepState = connect.prepareStatement(passwordQuery)){
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
	 
	 public boolean update(int x, String newInput, String username){
	    	String update = "";
	    	if (x == 1){
	    		update = "UPDATE FoodTruckTracker.User SET username = ? WHERE username = ?";
	    	}
	    	else if (x == 2){
	    		update = "UPDATE FoodTruckTracker.User SET name = ? WHERE username = ?";
	    	}
	    	else if (x == 3){
	    		update = "UPDATE FoodTruckTracker.User SET address = ? WHERE username = ?";
	    	}
	    	else if (x == 4){
	    		update = "UPDATE FoodTruckTracker.User SET email = ? WHERE username = ?";
	    	}
	    	else{
	    		update = "UPDATE FoodTruckTracker.User SET password = ? WHERE username = ?";
	    	}
	    	
	    	try(Connection connect = database.connectToDB();
	    			PreparedStatement prepState = connect.prepareStatement(update)){
	    		prepState.setString(1, newInput);
	    		prepState.setString(2, username);
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
		 try(Connection connect = database.connectToDB();
				 PreparedStatement prepState = connect.prepareStatement(deleteUser)){
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
