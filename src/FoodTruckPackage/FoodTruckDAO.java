package FoodTruckPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FoodTruckDAO{
	//TODO: This should be used for Create/ Read (get) / Update / Destroy
	private Connection connect;
	private PreparedStatement prepState;
	private ResultSet resultSet;
	private Connect database = new Connect();
	private FoodTruck truckOwner = new FoodTruck();
	private MenuItems menuItem = new MenuItems();
	private Ingredients ingredients = new Ingredients();
	private ArrayList<MenuItems> menuArray = new ArrayList<MenuItems>();
	private ArrayList<Ingredients> ingredientArray = new ArrayList<Ingredients>();
	
	public FoodTruckDAO(){
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
    
    public FoodTruck select(String trucksName) throws Exception{
    	String queryDB = "SELECT * FROM FoodTruckTracker.FoodTruck WHERE truckName = ?";
        try{
        	prepState = connect.prepareStatement(queryDB);
            prepState.setString(1, trucksName);
            resultSet = prepState.executeQuery();
            
            if (!resultSet.next()){
            	return null;
            }
            
			while (resultSet.next()){
				int truckID = resultSet.getInt("truckID");
				String truckName = resultSet.getString("truckName");
				String owner = resultSet.getString("owner");
				String foodType = resultSet.getString("foodType");
				
				System.out.println("Truck: " + truckName + "  Owner: " + owner + "  Food Category: " + foodType + "\n");
				truckOwner = new FoodTruck();
				truckOwner.setTruckID(truckID);
				truckOwner.setName(truckName);
				truckOwner.setOwner(owner);
				truckOwner.setFoodType(foodType);
    		}
        }
        catch (SQLException e){
        	throw e;
        }
        return truckOwner;
    }
    
    public ArrayList<MenuItems> getMenu(int truckID){
    	String firstQ = "SELECT menuID, foodName, price, calories, specialComments FROM Menu WHERE truckID = ?";
    	menuArray = new ArrayList<MenuItems>();
    	try{
        	prepState = connect.prepareStatement(firstQ);
            prepState.setInt(1, truckID);
            resultSet = prepState.executeQuery();
            
    		while (resultSet.next()){
				int menuID = resultSet.getInt("menuID");
				String foodName = resultSet.getString("foodName");
				float price = resultSet.getFloat("price");
				int calories = resultSet.getInt("calories");
				String specialComments = resultSet.getString("specialComments");
				
				menuItem = new MenuItems();
				menuItem.setTitle(foodName);
				menuItem.setPrice(price);
				menuItem.setTotalCalories(calories);
				menuItem.setSpecialComments(specialComments);
				
				ingredientArray = getIngredients(menuID);
				menuItem.setIngredients(ingredientArray);
				menuArray.add(menuItem);
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return menuArray;
    }
    
    public ArrayList<Ingredients> getIngredients(int menuID){
    	String secondQ = "SELECT ingredient FROM Ingredients WHERE menuID = ?";
    	ingredientArray = new ArrayList<Ingredients>();
    	try{
        	prepState = connect.prepareStatement(secondQ);
            prepState.setInt(1, menuID);
            resultSet = prepState.executeQuery();
            
            while (resultSet.next()){
				String ingredient = resultSet.getString("ingredient");
				
				ingredients = new Ingredients();
				ingredients.setName(ingredient);
				ingredientArray.add(ingredients);
			}
			return ingredientArray;
		}
		catch (SQLException e){
			System.out.println(e);
		}
		return ingredientArray;
    }
}
