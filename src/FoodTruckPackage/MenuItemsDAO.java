package FoodTruckPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuItemsDAO {
	//TODO: This should be used for Create/ Read (get) / Update / Destroy
	private Connection connect;
	private PreparedStatement prepState;
	private ResultSet resultSet;
	private Connect database;
	
	public MenuItemsDAO(){
		database = new Connect();
		try {
			connect = database.connectToDB();
		} catch (Exception e){
			e.printStackTrace();
		}	
	}
	
	public boolean insert(MenuItems item){
		String menuQuery = "INSERT INTO FoodTruckTracker.Menu (foodName, price, calories, specialComments, truckID) VALUES (?,?,?,?,?)";
		try{
			prepState = connect.prepareStatement(menuQuery);
			prepState.setString(1, item.getTitle());
			prepState.setFloat(2, item.getPrice());
			prepState.setInt(3, item.getTotalCalories());
			prepState.setString(4, item.getSpecialComments());
			prepState.setInt(5, item.getTruckID());
			prepState.executeUpdate();
			return true;
		}
		catch(SQLException e){
			System.out.println(e);
			return false;
		}
	}
	
	public ResultSet select(String foodName) throws Exception{
    	String queryDB = "SELECT * FROM FoodTruckTracker.Menu WHERE foodName = ?";
        try{
        	prepState = connect.prepareStatement(queryDB);
            prepState.setString(1, foodName);
            resultSet = prepState.executeQuery();
            return resultSet;
        }
        catch (Exception e){
        	throw e;
        }
    }
	
	
	

}
