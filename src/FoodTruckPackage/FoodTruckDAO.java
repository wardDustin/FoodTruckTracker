package FoodTruckPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FoodTruckDAO{
	//TODO: This should be used for Create/ Read (get) / Update / Destroy
	private Connection connect;
	private PreparedStatement prepState;
	private ResultSet resultSet;
	private Connect database;
	
	public FoodTruckDAO(){
		database = new Connect();
		try {
			connect = database.connectToDB();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
    
    public boolean insert(FoodTruck truckName){
    	String insertQuery = "INSERT INTO FoodTruckTracker.FoodTruck (truckName, password, owner, foodType) VALUES (?,?,?,?)";
    	try{
            prepState = connect.prepareStatement(insertQuery);
            prepState.setString(1, truckName.getName());
            prepState.setString(2, truckName.getPassword());
            prepState.setString(3, truckName.getOwner());
            prepState.setString(4, truckName.getFoodType());
            prepState.executeUpdate();
            return true;
	    }
	    catch (SQLException e) {
	    	System.out.println(e);
	    	return false;
	    }
    }
    
    public ResultSet select(String truckName) throws Exception{
    	String queryDB = "SELECT * FROM FoodTruckTracker.FoodTruck WHERE truckName = ?";
        try{
        	prepState = connect.prepareStatement(queryDB);
            prepState.setString(1, truckName);
            resultSet = prepState.executeQuery();
            return resultSet;
        }
        catch (Exception e){
        	throw e;
        }
    }

}