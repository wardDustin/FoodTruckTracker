package FoodTruckPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class MenuItemsDAO {
	//TODO: This should be used for Create/ Read (get) / Update / Destroy
	private Connect database;
	
	public MenuItemsDAO(){
		database = new Connect();
	}
	
	public boolean insert(MenuItems item){
		String menuQuery = "INSERT INTO FoodTruckTracker.Menu (foodName, price, calories, specialComments, truckID) VALUES (?,?,?,?,?)";
		try(Connection connect = database.connectToDB();
				PreparedStatement prepState = connect.prepareStatement(menuQuery)){
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
	
	public boolean newTruck(int truckID){
		String query = "SELECT menuID FROM FoodTruckTracker.Menu WHERE truckID = ?";
		try(Connection connect = database.connectToDB();
				PreparedStatement prepState = connect.prepareStatement(query)){
			prepState.setInt(1, truckID);
			try (ResultSet rs = prepState.executeQuery()){
				if (rs.next()){
					return true;
				}
			}
		}
		catch(SQLException e){
			System.out.println(e);
			return false;
		}
		return false;
	}
	
	public MenuItems select(String foodsName, int trucksID){
    	String queryDB = "SELECT menuID, foodName, price, calories, specialComments, truckID FROM FoodTruckTracker.Menu WHERE foodName = ? AND truckID = ?";
        MenuItems menuItem = new MenuItems();
    	try(Connection connect = database.connectToDB();
    			PreparedStatement prepState = connect.prepareStatement(queryDB)){
            prepState.setString(1, foodsName);
            prepState.setInt(2, trucksID);
            try(ResultSet rs = prepState.executeQuery()){
            
	            while (rs.next()){
	            	menuItem.setMenuID(rs.getInt("menuID"));
	            	menuItem.setTitle(rs.getString("foodName"));
	            	menuItem.setPrice(rs.getFloat("price"));
	            	menuItem.setTotalCalories(rs.getInt("calories"));
	            	menuItem.setSpecialComments(rs.getString("specialComments"));
	            	menuItem.setTruckID(rs.getInt("truckID"));
	            }
            }
        }
        catch (SQLException e){
        	e.printStackTrace();
        }
        return menuItem;
    }
	
	public ArrayList<MenuItems> getWholeMenu(int truckID){
    	String firstQ = "SELECT menuID, foodName, price, calories, specialComments FROM Menu WHERE truckID = ?";
    	ArrayList<MenuItems> menuArray = new ArrayList<MenuItems>();
    	ArrayList<Ingredients> ingredientArray = new ArrayList<Ingredients>();
    	try(Connection connect = database.connectToDB();
    			PreparedStatement prepState = connect.prepareStatement(firstQ)){
            prepState.setInt(1, truckID);
            try(ResultSet rs = prepState.executeQuery()){
            
	    		while (rs.next()){
					MenuItems menuItem = new MenuItems();
					menuItem.setMenuID(rs.getInt("menuID"));
					menuItem.setTitle(rs.getString("foodName"));
					menuItem.setPrice(rs.getFloat("price"));
					menuItem.setTotalCalories(rs.getInt("calories"));
					menuItem.setSpecialComments(rs.getString("specialComments"));
					
					ingredientArray = getIngredients(menuItem.getMenuID());
					menuItem.setIngredients(ingredientArray);
					menuArray.add(menuItem);
				}
	    		Collections.sort(menuArray, MenuItems.menuItemsNameComp);
            }
		} catch (SQLException e) {
			System.out.println(e);
		}
		return menuArray;
    }
    
    public ArrayList<Ingredients> getIngredients(int menuID){
    	String secondQ = "SELECT ingredient FROM Ingredients WHERE menuID = ?";
    	Ingredients ingredients = new Ingredients();
    	ArrayList<Ingredients> ingredientArray = new ArrayList<Ingredients>();
    	try(Connection connect = database.connectToDB();
    			PreparedStatement prepState = connect.prepareStatement(secondQ)){
            prepState.setInt(1, menuID);
            try (ResultSet rs = prepState.executeQuery()){
	            while (rs.next()){
					ingredients = new Ingredients();
					ingredients.setName(rs.getString("ingredient"));
					ingredientArray.add(ingredients);
				}
	            Collections.sort(ingredientArray, Ingredients.ingredientsNameComp);
            }
		}
		catch (SQLException e){
			System.out.println(e);
		}
		return ingredientArray;
    }
    
    public ArrayList<String> listMenu(int truckID){
    	String query = "SELECT menuID, foodName, price, calories, specialComments FROM FoodTruckTracker.Menu WHERE truckID = ?";
    	ArrayList<String> menuList = new ArrayList<String>();
    	try(Connection connect = database.connectToDB();
    			PreparedStatement prepState = connect.prepareStatement(query)){
    		prepState.setInt(1, truckID);
    		try (ResultSet rs = prepState.executeQuery()){
    			while (rs.next()){
	    			MenuItems menuItem = new MenuItems();
	    			menuItem.setMenuID(rs.getInt("menuID"));
					menuItem.setTitle(rs.getString("foodName"));
					menuItem.setPrice(rs.getFloat("price"));
					menuItem.setTotalCalories(rs.getInt("calories"));
					menuItem.setSpecialComments(rs.getString("specialComments"));
					
					menuList.add(menuItem.getTitle());
	    		}
    			Collections.sort(menuList);
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
    	try(Connection connect = database.connectToDB();
    			PreparedStatement prepState = connect.prepareStatement(query)){
    		prepState.setString(1, foodsName);
    		prepState.setInt(2, truckID);
    		try(ResultSet rs = prepState.executeQuery()){
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
    	try(Connection connect = database.connectToDB();
    			PreparedStatement prepState = connect.prepareStatement(update)){
    		prepState.setString(1, input);
    		prepState.setInt(2, menuID);
    		prepState.executeUpdate();
    	}
    	catch(SQLException e){
    		System.out.println(e);
    		return false;
    	}
    	return true;
    }
    
    public boolean updateItem(float input, int menuID){
    	String update = "UPDATE FoodTruckTracker.Menu SET price = ? WHERE menuID = ?";
    	try(Connection connect = database.connectToDB();
    			PreparedStatement prepState = connect.prepareStatement(update)){
    		prepState.setFloat(1, input);
    		prepState.setInt(2, menuID);
    		prepState.executeUpdate();
    	}
    	catch(SQLException e){
    		System.out.println(e);
    		return false;
    	}
    	return true;
    }
    
    public boolean updateItem(int input, int menuID){
    	String update = "UPDATE FoodTruckTracker.Menu SET calories = ? WHERE menuID = ?";
    	try(Connection connect = database.connectToDB();
    			PreparedStatement prepState = connect.prepareStatement(update)){
    		prepState.setInt(1, input);
    		prepState.setInt(2, menuID);
    		prepState.executeUpdate();
    	}
    	catch(SQLException e){
    		System.out.println(e);
    		return false;
    	}
    	return true;
    }
    
    public boolean deleteMenuItem(String foodName, int truckID){
    	String delete = "DELETE FROM FoodTruckTracker.Menu WHERE foodName = ? AND truckID = ?";
    	try(Connection connect = database.connectToDB();
    			PreparedStatement prepState = connect.prepareStatement(delete)){
    		prepState.setString(1, foodName);
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
