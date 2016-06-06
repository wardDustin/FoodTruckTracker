package FoodTruckPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IngredientsDAO {
	//TODO: This should be used for Create/ Read (get) / Update / Destroy
	private Connect database;
	
	public IngredientsDAO(){
		database = new Connect();
	}
	
	public boolean insert(Ingredients ingredient){
		String insertQuery = "INSERT INTO FoodTruckTracker.Ingredients (ingredient, menuID) VALUES (?,?)";
		try(Connection connect = database.connectToDB();
				PreparedStatement prepState = connect.prepareStatement(insertQuery)){
			prepState.setString(1, ingredient.getName());
			prepState.setInt(2, ingredient.getMenuID());
			prepState.executeUpdate();
		}
		catch(SQLException e){
			System.out.println(e);
			return false;
		}
		return true;
	}
	
	public boolean deleteIngredient(String removeIng){
		String delete = "DELETE FROM FoodTruckTracker.Ingredients WHERE ingredient = ?";
		try(Connection connect = database.connectToDB();
				PreparedStatement prepState = connect.prepareStatement(delete)){
			prepState.setString(1, removeIng);
			prepState.executeUpdate();
		}
		catch(SQLException e){
			System.out.println(e);
			return false;
		}
		return true;
	}
	
	public ArrayList<String> listIngredients(int menuID){
    	String query = "SELECT ingredient FROM FoodTruckTracker.Ingredients WHERE menuID = ?";
    	ArrayList<String> ingredientList = new ArrayList<String>();
    	try(Connection connect = database.connectToDB();
    			PreparedStatement prepState = connect.prepareStatement(query)){
    		prepState.setInt(1, menuID);
    		try(ResultSet rs = prepState.executeQuery()){
    			while (rs.next()){
	    			Ingredients ingredient = new Ingredients();
	    			ingredient.setName(rs.getString("ingredient"));
					
					ingredientList.add(ingredient.getName());
	    		}
    		}
    	}
    	catch(SQLException e){
    		System.out.println(e);
    	}
    	return ingredientList;
    }
	
	public boolean deleteAllIngredients(int menuID){
		String delete = "DELETE FROM FoodTruckTracker.Ingredients WHERE menuID = ?";
		try(Connection connect = database.connectToDB();
				PreparedStatement prepState = connect.prepareStatement(delete)){
			prepState.setInt(1, menuID);
			prepState.executeUpdate();
		}
		catch(SQLException e){
			System.out.println(e);
			return false;
		}
		return true;
	}

}
