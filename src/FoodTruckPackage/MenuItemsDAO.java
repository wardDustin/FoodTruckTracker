package FoodTruckPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuItemsDAO {
	//TODO: This should be used for Create/ Read (get) / Update / Destroy
	private Connection connect;
	private PreparedStatement prepState;
	private ResultSet rs;
	private Connect database;
	private MenuItems menuItem = new MenuItems();
	
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
		}
		catch(SQLException e){
			System.out.println(e);
			return false;
		}
		return true;
	}
	
	public MenuItems select(String foodsName) throws Exception{
    	String queryDB = "SELECT menuID, foodName, price, calories, specialComments, truckID FROM FoodTruckTracker.Menu WHERE foodName = ?";
        try{
        	prepState = connect.prepareStatement(queryDB);
            prepState.setString(1, foodsName);
            rs = prepState.executeQuery();
            
            while (rs.next()){
            	int menuID = rs.getInt("menuID");
            	String foodName = rs.getString("foodName");
            	float price = rs.getFloat("price");
            	int calories = rs.getInt("calories");
            	String specialComments = rs.getString("specialComments");
            	int truckID = rs.getInt("truckID");
            	
            	menuItem.setMenuID(menuID);
            	menuItem.setTitle(foodName);
            	menuItem.setPrice(price);
            	menuItem.setTotalCalories(calories);
            	menuItem.setSpecialComments(specialComments);
            	menuItem.setTruckID(truckID);
            }
        }
        catch (Exception e){
        	throw e;
        }
        return menuItem;
    }
	
	
	

}
