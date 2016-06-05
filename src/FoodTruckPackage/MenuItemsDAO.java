package FoodTruckPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MenuItemsDAO {
	//TODO: This should be used for Create/ Read (get) / Update / Destroy
	private Connection connect;
	private PreparedStatement prepState;
	private ResultSet rs;
	private Connect database;
	private MenuItems menuItem = new MenuItems();
	private Ingredients ingredients = new Ingredients();
	
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
			prepState.close();
		}
		catch(SQLException e){
			System.out.println(e);
			return false;
		}
		return true;
	}
	
	public boolean newTruck(int truckID){
		String query = "SELECT menuID FROM FoodTruckTracker.Menu WHERE truckID = ?";
		ResultSet rs;
		try{
			prepState = connect.prepareStatement(query);
			prepState.setInt(1, truckID);
			rs = prepState.executeQuery();
			
			if (rs.next()){
				return true;
			}
		}
		catch(SQLException e){
			System.out.println(e);
			return false;
		}
		return false;
	}
	
	public MenuItems select(String foodsName, int trucksID) throws Exception{
    	String queryDB = "SELECT menuID, foodName, price, calories, specialComments, truckID FROM FoodTruckTracker.Menu WHERE foodName = ? AND truckID = ?";
        try{
        	prepState = connect.prepareStatement(queryDB);
            prepState.setString(1, foodsName);
            prepState.setInt(2, trucksID);
            rs = prepState.executeQuery();
            
            while (rs.next()){
            	menuItem.setMenuID(rs.getInt("menuID"));
            	menuItem.setTitle(rs.getString("foodName"));
            	menuItem.setPrice(rs.getFloat("price"));
            	menuItem.setTotalCalories(rs.getInt("calories"));
            	menuItem.setSpecialComments(rs.getString("specialComments"));
            	menuItem.setTruckID(rs.getInt("truckID"));
            }
        }
        catch (Exception e){
        	throw e;
        }
        return menuItem;
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
    
    public ArrayList<String> listMenu(int truckID){
    	String query = "SELECT menuID, foodName, price, calories, specialComments FROM FoodTruckTracker.Menu WHERE truckID = ?";
    	ArrayList<String> menuList = new ArrayList<String>();
    	ResultSet rs;
    	try{
    		prepState = connect.prepareStatement(query);
    		prepState.setInt(1, truckID);
    		rs = prepState.executeQuery();
    		
    		while (rs.next()){
    			MenuItems menuItem = new MenuItems();
    			menuItem.setMenuID(rs.getInt("menuID"));
				menuItem.setTitle(rs.getString("foodName"));
				menuItem.setPrice(rs.getFloat("price"));
				menuItem.setTotalCalories(rs.getInt("calories"));
				menuItem.setSpecialComments(rs.getString("specialComments"));
				
				menuList.add(menuItem.getTitle());
    		}
    	}
    	catch(SQLException e){
    		System.out.println(e);
    	}
    	return menuList;
    }
    
    public MenuItems getMenuItem(String foodsName, int truckID){
    	String query = "SELECT menuID, foodName, price, calories, specialComments FROM FoodTruckTracker.Menu WHERE foodName = ? AND truckID = ?";
    	ArrayList<Ingredients> ingredientArray = new ArrayList<Ingredients>();
    	MenuItems menuItem = new MenuItems();
    	ResultSet rs;
    	try{
    		prepState = connect.prepareStatement(query);
    		prepState.setString(1, foodsName);
    		prepState.setInt(2, truckID);
    		rs = prepState.executeQuery();
    		
    		while (rs.next()){
    			menuItem.setMenuID(rs.getInt("menuID"));
				menuItem.setTitle(rs.getString("foodName"));
				menuItem.setPrice(rs.getFloat("price"));
				menuItem.setTotalCalories(rs.getInt("calories"));
				menuItem.setSpecialComments(rs.getString("specialComments"));
				
				ingredientArray = getIngredients(menuItem.getMenuID());
				menuItem.setIngredients(ingredientArray);
    		}
    		
    	}
    	catch (SQLException e){
    		System.out.println(e);
    	}
    	return menuItem;
    }
    
    public boolean updateItem(boolean yes, String input, int menuID){
    	String update = "";
    	if (yes){
    		update = "UPDATE FoodTruckTracker.Menu SET foodName = ? WHERE menuID = ?";
    	}
    	else{
    		update = "UPDATE FoodTruckTracker.Menu SET specialComments = ? WHERE menuID = ?";
    	}
    	try{
    		prepState = connect.prepareStatement(update);
    		prepState.setString(1, input);
    		prepState.setInt(2, menuID);
    		prepState.executeUpdate();
    		prepState.close();
    	}
    	catch(SQLException e){
    		System.out.println(e);
    		return false;
    	}
    	return true;
    }
    
    public boolean updateItem(float input, int menuID){
    	String update = "UPDATE FoodTruckTracker.Menu SET price = ? WHERE menuID = ?";
    	try{
    		prepState = connect.prepareStatement(update);
    		prepState.setFloat(1, input);
    		prepState.setInt(2, menuID);
    		prepState.executeUpdate();
    		prepState.close();
    	}
    	catch(SQLException e){
    		System.out.println(e);
    		return false;
    	}
    	return true;
    }
    
    public boolean updateItem(int input, int menuID){
    	String update = "UPDATE FoodTruckTracker.Menu SET calories = ? WHERE menuID = ?";
    	try{
    		prepState = connect.prepareStatement(update);
    		prepState.setInt(1, input);
    		prepState.setInt(2, menuID);
    		prepState.executeUpdate();
    		prepState.close();
    	}
    	catch(SQLException e){
    		System.out.println(e);
    		return false;
    	}
    	return true;
    }
    
    public boolean deleteMenuItem(String foodName, int truckID){
    	String delete = "DELETE FROM FoodTruckTracker.Menu WHERE foodName = ? AND truckID = ?";
    	try{
    		prepState = connect.prepareStatement(delete);
    		prepState.setString(1, foodName);
    		prepState.setInt(2, truckID);
    		prepState.executeUpdate();
    		prepState.close();
    	}
    	catch (SQLException e){
    		System.out.println(e);
    		return false;
    	}
    	return true;
    }
}
