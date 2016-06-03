package FoodTruckPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;

public class IngredientsDAO {
	//TODO: This should be used for Create/ Read (get) / Update / Destroy
	private Connection connect;
	private PreparedStatement prepState;
//	private ResultSet resultSet;
	private Connect database;
	
	public IngredientsDAO(){
		database = new Connect();
		try {
			connect = database.connectToDB();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean insert(Ingredients ingredient){
		String insertQuery = "INSERT INTO FoodTruckTracker.Ingredients (ingredient, menuID) VALUES (?,?)";
		try{
			prepState = connect.prepareStatement(insertQuery);
			prepState.setString(1, ingredient.getName());
			prepState.setInt(2, ingredient.getMenuID());
			prepState.executeUpdate();
			return true;
		}
		catch(SQLException e){
			System.out.println(e);
			return false;
		}
	}

}
