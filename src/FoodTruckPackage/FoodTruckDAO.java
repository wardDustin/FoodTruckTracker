package FoodTruckPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FoodTruckDAO{
	
	private Connection connect = null;
	private PreparedStatement prepState = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private Connect database;
	
	public FoodTruckDAO(){
		database = new Connect();
		try {
			connect = database.connectToDB();
		} catch (Exception e){
			e.printStackTrace();
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
    
    public boolean insert(String insertQuery, String truckName, String pw, String owner, String foodType){
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

}
