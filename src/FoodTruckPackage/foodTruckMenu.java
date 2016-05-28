package FoodTruckPackage;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class foodTruckMenu {

	private Scanner input;
	private ResultSet resultSet = null;
	private UserDAO userDAO;
	private HandlePW pwHandler;
	private FoodTruckDAO foodDAO;
	private ArrayList<MenuItems> menuArray;
	private FoodTruck truckOwner;
	private MenuItems menuItem;
	
	public static Connect database = new Connect();
	
	public foodTruckMenu(){
		input = new Scanner(System.in);
		userDAO = new UserDAO();
		pwHandler = new HandlePW();
		foodDAO = new FoodTruckDAO();
		menuArray = new ArrayList<MenuItems>();
		truckOwner = new FoodTruck();
		menuItem = new MenuItems();
	}
	
	public static void main(String[] args) throws Exception{
		database.connectToDB();
		
		int x = 0;
		foodTruckMenu menu = new foodTruckMenu();
		do{
			x = menu.startMenu(); //ask why make an instance? STATIC
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
			resultSet = userDAO.prepSelectQuery(usernameQuery, username);
			
			if (!resultSet.next()){
				System.out.println("Thank you for registering as a new User!");
				System.out.println("Create a password please: ");
				String pw = input.nextLine();
				pw = pwHandler.encryptPW(pw);
					
				System.out.println("Your name please:");
				String name = input.nextLine();
				
				System.out.println("Address:");
				String address = input.nextLine();
				
				System.out.println("And email:");
				String email = input.nextLine();
				
				String insertQuery = "INSERT INTO FoodTruckTracker.User (username, password, name, address, email) VALUES (?,?,?,?,?)";
				boolean success = userDAO.insert(insertQuery, username, pw, name, address, email);
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
				boolean loggedIn = confirmPW(username, "username", db);
				if (!loggedIn){
					int x = 0;
					while(x<2){
						System.out.println("Password does not match Username! Please try again: ");
						loggedIn = confirmPW(username, "username", db);
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
			
			resultSet = foodDAO.select(truckName);
			boolean returningOwner = parseSelect(resultSet);
			
			if(returningOwner){
				System.out.println("Welcome back, " + truckOwner.getName());
				System.out.println("Please enter your password to confirm: ");
				db = "FoodTruckTracker.FoodTruck";
				boolean loggedIn = confirmPW(truckName, "truckName", db);
				if (!loggedIn){
					int x = 0;
					while(x<2){
						System.out.println("Password does not match Username! Please try again: ");
						loggedIn = confirmPW(truckName, "truckName", db);
						if(loggedIn){
							System.out.println("You are logged in!");
							x=2;
							makeMenuItemOption();
							System.exit(1);
						}
						x++;
					}
					System.out.println("Unsuccessful login");
					System.out.println("Exiting...");
					System.exit(0);
				}
				else{
					System.out.println("You are logged in, " + truckOwner.getOwner()  + "!");
					makeMenuItemOption();
					//TODO: call next step of process
					System.exit(0);
				}
			}
			else{
				System.out.println("Thank you for registering " + truckName + "!");
				System.out.println("Create a password please: ");
				String pw = input.nextLine();
				pw = pwHandler.encryptPW(pw);
				
				System.out.println("What is the name of the owner of " + truckName + "?");
				String owner = input.nextLine();
				
				System.out.println("And what is the food type being served? (e.g. Mexican, Chinese, Greek, BBQ, etc)");
				String foodType = input.nextLine();
				
				String insertQuery = "INSERT INTO FoodTruckTracker.FoodTruck (truckName, password, owner, foodType) VALUES (?,?,?,?)";
//				a = new FoodTruck (truckName, owner, foodType); not needed...
				boolean success = foodDAO.insert(insertQuery, truckName, pw, owner, foodType);
				if (success){
					System.out.println("New FoodTruck made!");
					makeMenuItemOption();
					//TODO: call next step of process
					System.exit(0);
				}
				else{
					System.out.println("Error creating FoodTruck. Please contact owners");
					System.exit(2);
					//TODO: throw a real error and repeat the process?
				}
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
	
	public boolean confirmPW(String name, String column, String db) throws Exception{
		String hashedpw = null;
		String pw = input.nextLine();
		
		String passwordQuery = "SELECT password FROM " + db + " WHERE " + column + " = ?";
		resultSet = userDAO.prepSelectQuery(passwordQuery, name);
		
		while(resultSet.next()){
			hashedpw = resultSet.getString("password");
		}
		
		boolean itMatches = pwHandler.passwordMatch(pw, hashedpw);
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
	
	public int makeMenuItemOption(){
		int x = 0;
		do {
			System.out.println("Would you like to create and add items to your menu?");
			System.out.println("1| Yes 2| No");
			
			x = verifyInput(x);
			while (x!=1 && x!=2){
				System.out.println("1| Yes 2| No");
				x = verifyInput(x);
			}
			
			if (x == 1){
				makeMenuItem();
			}
		}while(x==1);
		
		System.out.println("Here is your menu: " + menuArray);
		
		return x;
	}
	
	public void makeMenuItem(){
		System.out.println("Let's build your menu item!");
		System.out.println("What is the name of the food item you would like to add: ");
		String title = input.nextLine();

		
		System.out.println("Sounds delicious. Let's add the ingredients!");
		System.out.println("When you are done entering all the ingredients, hit 0 to quit");
		int x = 0;
		ArrayList<String> ingredients= new ArrayList<String>();
		do{
			System.out.println("(0 to exit) Ingredient " + (x+1) +": ");
			String y = input.nextLine();
			try{
				if (Integer.parseInt(y)==0){
					break;
				}
			}
			catch(NumberFormatException e){
				ingredients.add(y);
			}
			x++;
		}while(x!=0);
		
		System.out.println(ingredients);
		
		System.out.println("How many calories does " + title + " contain?");
		int calories = 0;
		calories = verifyInput(calories);
		while (calories < 0){
			System.out.println("I need a positive integer please: ");
			calories = verifyInput(calories);
		}
		
		System.out.println("And finally, any special comments about your food?");
		String specialComments = input.nextLine();
		
		menuItem.setTitle(title);
		menuItem.setIngredients(ingredients);
		menuItem.setTotalCalories(calories);
		menuItem.setSpecialComments(specialComments);
		//TODO: Add to Menu DB as individual items.... foreign key link
		
		menuArray.add(menuItem);
	}
	
	public boolean parseSelect(ResultSet resultSet) throws Exception{
		while (resultSet.next()){
			int Id = resultSet.getInt("Id");
			String truckName = resultSet.getString("truckName");
			String owner = resultSet.getString("owner");
			String foodType = resultSet.getString("foodType");
			
			System.out.println("Truck: " + truckName + "  Owner: " + owner + "  Food Category: " + foodType);
			truckOwner.setName(truckName);
			truckOwner.setOwner(owner);
			truckOwner.setFoodType(foodType);
			return true;
		}
		return false;
	}
}
