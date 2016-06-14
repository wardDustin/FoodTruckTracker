package FoodTruckPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class FavoritesDAO {
	private Connect database;
	
	public FavoritesDAO(){
		database = new Connect();
	}
	
	public ArrayList<Favorites> displayFaves(int userID){
		 ArrayList<Favorites> faveArray = new ArrayList<Favorites>();
		 String query = "SELECT truckID, truckName, owner, foodType, userID FROM FoodTruckTracker.Favorites WHERE userID = ?";
		 try(Connection connect = database.connectToDB();
					PreparedStatement prepState = connect.prepareStatement(query)){
			 prepState.setInt(1, userID);
			 try (ResultSet rs = prepState.executeQuery()){
				 while (rs.next()){
					 Favorites fave = new Favorites();
					 fave.setTruckID(rs.getInt("truckID"));
					 fave.setName(rs.getString("truckName"));
					 fave.setOwner(rs.getString("owner"));
					 fave.setFoodType(rs.getString("foodType"));
					 fave.setUserID(rs.getInt("userID"));
					 
					 faveArray.add(fave);
				 }
				 Collections.sort(faveArray, Favorites.favesTruckNameComp);
			 }
			 
		 }
		 catch(SQLException e){
			 e.printStackTrace();
		 }
		 return faveArray;
	 }
	
	public boolean checkForFave(int truckID, int userID){
		String checkQuery = "SELECT truckID, truckName, owner, foodType, userID FROM FoodTruckTracker.Favorites WHERE truckID = ? AND userID = ?";
		try(Connection connect = database.connectToDB();
				PreparedStatement prepState = connect.prepareStatement(checkQuery)){
			prepState.setInt(1, truckID);
			prepState.setInt(2, userID);
			try (ResultSet rs = prepState.executeQuery()){
				while (rs.next()){
				return true;
				}
			}
		}
		catch(SQLException e){
			e.printStackTrace(); 
		}
		return false;
	}
	
	public boolean addFave(FoodTruck truck, int userID){
		String insertQuery = "INSERT INTO FoodTruckTracker.Favorites (truckID, truckName, owner, foodType, userID) VALUES (?,?,?,?,?)";
    	try(Connection connect = database.connectToDB();
    			PreparedStatement prepState = connect.prepareStatement(insertQuery)){
    		prepState.setInt(1, truck.getTruckID());
            prepState.setString(2, truck.getName());
            prepState.setString(3, truck.getOwner());
            prepState.setString(4, truck.getFoodType());
            prepState.setInt(5, userID);
            prepState.executeUpdate();
	    }
	    catch (SQLException e) {
	    	System.out.println(e);
	    	return false;
	    }
    	return true;
	}
	
	 public boolean update(int x, String newInput, int truckID){
	    	String update = "";
	    	if (x == 1){
	    		update = "UPDATE FoodTruckTracker.Favorites SET truckName = ? WHERE truckID = ?";
	    	}
	    	else if (x == 2){
	    		update = "UPDATE FoodTruckTracker.Favorites SET owner = ? WHERE truckID = ?";
	    	}
	    	else{
	    		update = "UPDATE FoodTruckTracker.Favorites SET foodType = ? WHERE truckID = ?";
	    	}
	    	
	    	try(Connection connect = database.connectToDB();
	    			PreparedStatement prepState = connect.prepareStatement(update)){
	    		prepState.setString(1, newInput);
	    		prepState.setInt(2, truckID);
	    		prepState.executeUpdate();
	    	}
	    	catch (SQLException e){
	    		System.out.println(e);
	    		return false;
	    	}
	    	return true;
	    }
	
	public boolean deleteFave(int truckID, int userID){
		String deleteQuery = "DELETE FROM FoodTruckTracker.Favorites WHERE truckID = ? AND userID = ?";
		try(Connection connect = database.connectToDB();
    			PreparedStatement prepState = connect.prepareStatement(deleteQuery)){
			prepState.setInt(1, truckID);
			prepState.setInt(2, userID);
			prepState.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
