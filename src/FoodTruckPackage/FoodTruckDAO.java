package FoodTruckPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class FoodTruckDAO{
	//TODO: This should be used for Create/ Read (get) / Update / Destroy
	private Connect database;
	
	public FoodTruckDAO(){
		database  = new Connect();
	}
    
	/*
	 * Inserts food truck
	 */
    public boolean insert(FoodTruck truck){
    	String insertQuery = "INSERT INTO FoodTruckTracker.FoodTruck (truckName, password, owner, foodType) VALUES (?,?,?,?)";
    	try(Connection connect = database.connectToDB();
    			PreparedStatement prepState = connect.prepareStatement(insertQuery)){
            prepState.setString(1, truck.getName());
            prepState.setString(2, truck.getPassword());
            prepState.setString(3, truck.getOwner());
            prepState.setString(4, truck.getFoodType());
            prepState.executeUpdate();
	    }
	    catch (SQLException e) {
	    	System.out.println(e);
	    	return false;
	    }
    	return true;
    }
    
    /*
     * queries database to make sure truck exists
     */
    public FoodTruck select(String trucksName){
    	String queryDB = "SELECT truckID, truckName, password, owner, foodType FROM FoodTruckTracker.FoodTruck WHERE truckName = ?";
    	FoodTruck truck = new FoodTruck();
        try(Connection connect = database.connectToDB();
        		PreparedStatement prepState = connect.prepareStatement(queryDB)){
            prepState.setString(1, trucksName);
            
            try(ResultSet resultSet = prepState.executeQuery() ){
            
				while (resultSet.next()){
					truck.setTruckID(resultSet.getInt("truckID"));
					truck.setName(resultSet.getString("truckName"));
					truck.setPassword(resultSet.getString("password"));
					truck.setOwner(resultSet.getString("owner"));
					truck.setFoodType(resultSet.getString("foodType"));
					
					System.out.println("Truck: " + truck.getName() + "  Owner: " + truck.getOwner() + "  Food Category: " + truck.getFoodType() + "\n");
	    		}
            }
        }
        catch (SQLException e){
        	e.printStackTrace();
        }
        return truck;
    }
    
    /*
     * Returns all trucks in an alphabetically sorted array list
     */
    public ArrayList<FoodTruck> select(){
    	String queryDB = "SELECT truckID, truckName, password, owner, foodType FROM FoodTruckTracker.FoodTruck";
    	ArrayList<FoodTruck> truckArray = new ArrayList<FoodTruck>();
        try(Connection connect = database.connectToDB();
        		PreparedStatement prepState = connect.prepareStatement(queryDB)){
            
            try(ResultSet rs = prepState.executeQuery() ){
				while (rs.next()){
					FoodTruck truck = new FoodTruck();
					truck.setTruckID(rs.getInt("truckID"));
					truck.setName(rs.getString("truckName"));
					truck.setPassword(rs.getString("password"));
					truck.setOwner(rs.getString("owner"));
					truck.setFoodType(rs.getString("foodType"));
					
					truckArray.add(truck);
	    		}
				Collections.sort(truckArray, FoodTruck.foodTruckNameComp);
            }
        }
        catch (SQLException e){
        	e.printStackTrace();
        }
        return truckArray;
    }
    
    /*
     * Depending on what the user wants to update- and an integer is passed to indicate this- different queries are used
     */
    public boolean update(int x, String newInput, int truckID){
    	String update = "";
    	if (x == 1){
    		update = "UPDATE FoodTruckTracker.FoodTruck SET truckName = ? WHERE truckID = ?";
    	}
    	else if (x == 2){
    		update = "UPDATE FoodTruckTracker.FoodTruck SET owner = ? WHERE truckID = ?";
    	}
    	else if (x == 3){
    		update = "UPDATE FoodTruckTracker.FoodTruck SET foodType = ? WHERE truckID = ?";
    	}
    	else{
    		update = "UPDATE FoodTruckTracker.FoodTruck SET password = ? WHERE truckID = ?";
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
}
