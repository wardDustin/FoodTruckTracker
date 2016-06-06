package FoodTruckPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FoodTruckDAO{
	//TODO: This should be used for Create/ Read (get) / Update / Destroy
	private Connect database;
	
	public FoodTruckDAO(){
		database  = new Connect();
	}
    
    public boolean insert(FoodTruck truckName){
    	String insertQuery = "INSERT INTO FoodTruckTracker.FoodTruck (truckName, password, owner, foodType) VALUES (?,?,?,?)";
    	try(Connection connect = database.connectToDB();
    			PreparedStatement prepState = connect.prepareStatement(insertQuery)){
            prepState.setString(1, truckName.getName());
            prepState.setString(2, truckName.getPassword());
            prepState.setString(3, truckName.getOwner());
            prepState.setString(4, truckName.getFoodType());
            prepState.executeUpdate();
	    }
	    catch (SQLException e) {
	    	System.out.println(e);
	    	return false;
	    }
    	return true;
    }
    
    public FoodTruck select(String trucksName){
    	String queryDB = "SELECT truckID, truckName, password, owner, foodType FROM FoodTruckTracker.FoodTruck WHERE truckName = ?";
    	FoodTruck truckOwner = new FoodTruck();
        try(Connection connect = database.connectToDB();
        		PreparedStatement prepState = connect.prepareStatement(queryDB)){
            prepState.setString(1, trucksName);
            
            try(ResultSet resultSet = prepState.executeQuery() ){
            
				while (resultSet.next()){
					truckOwner.setTruckID(resultSet.getInt("truckID"));
					truckOwner.setName(resultSet.getString("truckName"));
					truckOwner.setPassword(resultSet.getString("password"));
					truckOwner.setOwner(resultSet.getString("owner"));
					truckOwner.setFoodType(resultSet.getString("foodType"));
					
					System.out.println("Truck: " + truckOwner.getName() + "  Owner: " + truckOwner.getOwner() + "  Food Category: " + truckOwner.getFoodType() + "\n");
	    		}
            }
        }
        catch (SQLException e){
        	e.printStackTrace();
        }
        return truckOwner;
    }
    
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
