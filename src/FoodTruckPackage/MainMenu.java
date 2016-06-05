package FoodTruckPackage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MainMenu {
	public static Connect database = new Connect();

	private FoodTruck truckOwner;
	private FoodTruckService truckService;
	private UserService user;
	private Verify verify;
	
	public MainMenu(){
		truckOwner = new FoodTruck();
		truckService = new FoodTruckService();
		user = new UserService();
		verify = new Verify();
	}
	
	public static void main(String[] args) throws Exception{
		database.connectToDB();
		
		int x = 0;
		MainMenu menu = new MainMenu();
		do{
			x = menu.getUserType();
			if (x==1){
				menu.processUserActions();
			}
			else{
				menu.processFoodTruckActions();
			}
		}while (x!=3);
		
		database.close();
	}
	
	public int getUserType(){
		int selection = 0;
		System.out.println("Welcome to FoodTruckTracker!!");
		System.out.println("Are you a User or an owner of a Food Truck?");
		System.out.println("1| User\n2| Food Truck Owner\n3| Exit Program");
		selection = verify.verifyInput(selection);
		
		while (selection != 1 && selection != 2 && selection != 3){
			System.out.println("Invalid Selection, please choose again!");
			selection = verify.verifyInput(selection);
		}
		
		return selection;
	}
	
	public void processUserActions() throws Exception{
		user.logUserIn();
	}
	
	public void processFoodTruckActions() throws Exception{
		truckService.logOwnerIn();
	}
	
	public boolean parseSelect(ResultSet resultSet){
		try {
			while (resultSet.next()){
				int truckID = resultSet.getInt("truckID");
				String truckName = resultSet.getString("truckName");
				String owner = resultSet.getString("owner");
				String foodType = resultSet.getString("foodType");
				
				System.out.println("Truck: " + truckName + "  Owner: " + owner + "  Food Category: " + foodType + "\n");
				truckOwner.setTruckID(truckID);
				truckOwner.setName(truckName);
				truckOwner.setOwner(owner);
				truckOwner.setFoodType(foodType);
				return true;
			}
			return false;
		} catch (SQLException e) {
			System.out.println(e);
		}
		return false;
	}
}
