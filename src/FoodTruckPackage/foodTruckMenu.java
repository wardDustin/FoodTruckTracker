package FoodTruckPackage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.mindrot.jbcrypt.BCrypt;

import com.mysql.jdbc.Connection;

public class foodTruckMenu {

	private Scanner input;
	private Connection conn = null;
	private ResultSet resultSet = null;
	private User user; // should username really be User.setUserName(), password really be User.setPassword() etc?
	
	public static Connect database = new Connect();
	
	public foodTruckMenu(){
		input = new Scanner(System.in);
	}
	
	public static void main(String[] args) throws Exception{
		database.connectToDB();
		
		int x = 0;
		foodTruckMenu menu = new foodTruckMenu();
		do{
			x = menu.startMenu();
			menu.processInput(x);
		}while (x!=3);
		
		database.close();
	}
	
	public int startMenu(){
		// TODO: give menu options
		// TODO: return selection
		int selection = 0;
		System.out.println("Welcome to FoodTruckTracker!!");
		System.out.println("Are you a User or an owner of a Food Truck?");
		System.out.println("1| User\n2| Food Truck Owner\n3| Exit Program");
		selection = verifyInput(selection);
		
		while (selection != 1 && selection != 2 && selection != 3){
			System.out.println("Invalid Selection, please choose again!");
			selection = verifyInput(selection);
		}
		
		return selection;
	}
	
	public void processInput(int selection) throws Exception{
		//TODO: Do something with input
		String db = null;
		if (selection == 1){
			//TODO: clean all of this up....
			System.out.println("Excellent! Welcome!!");
			System.out.println("Let's check and see if you've used FoodTruckTracker before");
			System.out.println("First, please enter a username no longer than 15 characters: ");
			String username = input.nextLine();
			
			while (username.length() > 15){
				System.out.println("Sorry. Your username is too long. Please keep the length to 15 characters or less!");
				username = input.nextLine();
			}
			
			String usernameQuery = "SELECT username FROM FoodTruckTracker.User WHERE username = ?";
			resultSet = database.prepSelectQuery(usernameQuery, username);
			if (!resultSet.next()){
				System.out.println("Thank you for registering as a new User!");
				System.out.println("Create a password please: ");
				String pw = input.nextLine();
				pw = database.encryptPW(pw);
					
				System.out.println("Your name please:");
				String name = input.nextLine();
				
				System.out.println("Address:");
				String address = input.nextLine();
				
				System.out.println("And email:");
				String email = input.nextLine();
				
				String insertQuery = "INSERT INTO FoodTruckTracker.User (username, password, name, address, email) VALUES (?,?,?,?,?)";
				boolean success = database.insertUser(insertQuery, username, pw, name, address, email);
				if (success){
					System.out.println("New User made!");
					//TODO: call next step of process
					System.exit(0);
				}
				else{
					System.out.println("Something is wrong here");
					//TODO: throw a real error and repeat the process?
				}
			} 
			else{
				System.out.println("Thank you " + username + "!");
				System.out.println("Password: ");
				db = "FoodTruckTracker.User";
				boolean loggedIn = confirmPW(username, db);
				if (!loggedIn){
					int x = 0;
					while(x<2){
						System.out.println("Password does not match Username! Please try again: ");
						loggedIn = confirmPW(username, db);
						if(loggedIn){
							System.out.println("You are logged in");
							x=2;
							//TODO: call next step of process
							System.exit(1);
						}
						x++;
					}
					System.out.println("Unsuccessful login");
					System.out.println("Exiting...");
					System.exit(0);
				}
				else{
					System.out.println("You are logged in!");
					//TODO: call next step of process
					System.exit(0);
				}
			}
			
		}
		else if (selection == 2){
			//TODO: menu selection and verify FTT.FoodTruck first time user
			
			System.out.println("Hello, owner! Thank you for choosing FoodTruckTracker!");
			System.out.println("We just need to verify if you are new or a returning owner");
			System.out.println("What is the name of your food truck?");
			String truckName = input.nextLine();
			
			String usernameQuery = "SELECT truckName FROM FoodTruckTracker.FoodTruck WHERE truckName = ?";
			resultSet = database.prepSelectQuery(usernameQuery, truckName);
			if (!resultSet.next()){
				System.out.println("Thank you for registering " + truckName + "!");
				System.out.println("Create a password please: ");
				String pw = input.nextLine();
				pw = database.encryptPW(pw);
				
				System.out.println("What is the name of the owner of " + truckName + "?");
				String owner = input.nextLine();
				
				System.out.println("And what is the food type being served? (e.g. Mexican, Chinese, Greek, BBQ, etc)");
				String foodType = input.nextLine();
				
				String insertQuery = "INSERT INTO FoodTruckTracker.FoodTruck (truckName, password, owner, foodType) VALUES (?,?,?,?)";
				boolean success = database.insertFoodTruck(insertQuery, truckName, pw, owner, foodType);
				if (success){
					System.out.println("New FoodTruck made!");
					//TODO: call next step of process
					System.exit(0);
				}
				else{
					System.out.println("Error creating FoodTruck. Please contact owners");
					System.exit(2);
					//TODO: throw a real error and repeat the process?
				}
			}
			else{
				System.out.println("You exist");
				//TODO: call next step of process
			}
			
		}
		else if (selection == 3){
			System.out.println("Exiting...");
			System.out.println("Thank you for using FoodTruckTracker!");
			System.exit(0);
		}
	}
	
	public int verifyInput(int newInt){
		while (!input.hasNextInt()){
			input.next();
			System.out.println("I need an integer please: ");
		}
		newInt = input.nextInt();
		input.nextLine();
		return newInt;
	}
	
	public boolean confirmPW(String username, String db) throws SQLException{
		String hashedpw = null;
		String pw = input.nextLine();
		
		String passwordQuery = "SELECT password FROM " + db + " WHERE username = ?";
		resultSet = database.prepSelectQuery(passwordQuery, username);
		
		while(resultSet.next()){
			hashedpw = resultSet.getString("password");
		}
		
		boolean itMatches = database.passwordMatch(pw, hashedpw);
		if (itMatches){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void userMenu(String username){
		System.out.println(username + ", what would you like to do?");
		System.out.println("");
	}
	
	public void truckMenu(){
		
	}
	
}
