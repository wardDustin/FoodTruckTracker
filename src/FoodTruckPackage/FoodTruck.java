package FoodTruckPackage;

import java.sql.*;
import java.util.Scanner;

import org.mindrot.jbcrypt.BCrypt;

public class FoodTruck {
	private String name;
	private String owner;
	private String foodType;
	private foodMenu[] menu;
	
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
	
	public static void main(String[] args) throws Exception{
		
		int x = 0;
		foodTruckMenu menu = new foodTruckMenu();
		do{
			x = menu.startMenu();
			menu.processInput(x);
		}while (x!=6);
		
//		String passwd = "foobar";
//		String hashed = BCrypt.hashpw(passwd, BCrypt.gensalt());
//		System.out.println(hashed);
//		if (BCrypt.checkpw(passwd, hashed))
//			System.out.println("It matches");
//		else
//			System.out.println("It does not match");
//		
//		String userInsert = "INSERT IGNORE INTO FoodTruckTracker.User (username, password, name, address, email) VALUES ('drawnitsud', '" + hashed + "', 'Dustin W', '1600 Pennsylvania Ave', 'foo@bar.net')";
//		
//		String insert = "INSERT IGNORE INTO FoodTruckTracker.FoodTruck (truckName, owner, foodType) VALUES ('Rachels Radical (W)Raps','Rachel Ward','wraps')";
//		Connect db = new Connect();
//		db.connectToDB();
//		db.insertDB(insert);
//		db.insertDB(userInsert);
//		db.queryUser();
//		db.queryFoodTruck();
//		
//		db.close();
	}
}
