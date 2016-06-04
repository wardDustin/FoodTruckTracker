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
	private Connect database = new Connect();
	private Ingredients ingredients = new Ingredients();
	
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
    	String queryDB = "SELECT truckID, truckName, password, owner, foodType FROM FoodTruckTracker.FoodTruck WHERE truckName = ?";
    	ResultSet resultSet;
    	FoodTruck truckOwner = new FoodTruck();
        try{
        	prepState = connect.prepareStatement(queryDB);
            prepState.setString(1, trucksName);
            resultSet = prepState.executeQuery();
            
			while (resultSet.next()){
				truckOwner.setTruckID(resultSet.getInt("truckID"));
				truckOwner.setName(resultSet.getString("truckName"));
				truckOwner.setOwner(resultSet.getString("owner"));
				truckOwner.setFoodType(resultSet.getString("foodType"));
				
				System.out.println("Truck: " + truckOwner.getName() + "  Owner: " + truckOwner.getOwner() + "  Food Category: " + truckOwner.getFoodType() + "\n");
    		}
        }
        catch (SQLException e){
        	throw e;
        }
        return truckOwner;
    }
    
    public ArrayList<MenuItems> getMenu(int truckID){
    	String firstQ = "SELECT menuID, foodName, price, calories, specialComments FROM Menu WHERE truckID = ?";
    	ArrayList<MenuItems> menuArray = new ArrayList<MenuItems>();
    	ArrayList<Ingredients> ingredientArray = new ArrayList<Ingredients>();
    	ResultSet resultSet;
    	try{
        	prepState = connect.prepareStatement(firstQ);
            prepState.setInt(1, truckID);
            resultSet = prepState.executeQuery();
            
    		while (resultSet.next()){
				MenuItems menuItem = new MenuItems();
				menuItem.setMenuID(resultSet.getInt("menuID"));
				menuItem.setTitle(resultSet.getString("foodName"));
				menuItem.setPrice(resultSet.getFloat("price"));
				menuItem.setTotalCalories(resultSet.getInt("calories"));
				menuItem.setSpecialComments(resultSet.getString("specialComments"));
				
				ingredientArray = getIngredients(menuItem.getMenuID());
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
    	ArrayList<Ingredients> ingredientArray = new ArrayList<Ingredients>();
    	ResultSet resultSet;
    	try{
        	prepState = connect.prepareStatement(secondQ);
            prepState.setInt(1, menuID);
            resultSet = prepState.executeQuery();
            
            while (resultSet.next()){
				ingredients = new Ingredients();
				ingredients.setName(resultSet.getString("ingredient"));
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
