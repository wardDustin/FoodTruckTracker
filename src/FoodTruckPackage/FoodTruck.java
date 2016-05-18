package FoodTruckPackage;

import java.sql.*;

public class FoodTruck {
	private String name;
	private String owner;
	private String foodType;
	
	public FoodTruck (String name, String owner, String foodType){
		this.name = name;
		this.owner = owner;
		this.foodType = foodType;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getFoodType() {
		return this.foodType;
	}

	public void setFoodType(String foodType) {
		this.foodType = foodType;
	}
	
	public static void main(String[] args){
		Connection conn = null;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost/stats", "root", "MUtants13");
			Statement sqlState = conn.createStatement();
			
			String selectStuff = "SELECT franchName FROM teamsfranchises";
			
			ResultSet rows = sqlState.executeQuery(selectStuff);
			
			while(rows.next()){
				System.out.println(rows.getString("franchName"));
			}
		}
		catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
}
