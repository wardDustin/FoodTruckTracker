package FoodTruckPackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class foodTruckMenu {

	private Scanner input;
	private ResultSet resultSet;
	private HandlePW pwHandler;
	private UserDAO userDAO;
	private FoodTruckDAO foodDAO;
	private MenuItemsDAO menuDAO;
	private IngredientsDAO ingredientsDAO;
	private ArrayList<MenuItems> menuArray;
	private ArrayList<Ingredients> ingredientArray;
	private FoodTruck truckOwner;
	private User user;
	private MenuItems menuItem;
	private Ingredients ingredients;
	
	public static Connect database = new Connect();
	
	public foodTruckMenu(){
		input = new Scanner(System.in);
		pwHandler = new HandlePW();
		userDAO = new UserDAO();
		foodDAO = new FoodTruckDAO();
		menuDAO = new MenuItemsDAO();
		ingredientsDAO = new IngredientsDAO();
		menuArray = new ArrayList<MenuItems>();
		ingredientArray = new ArrayList<Ingredients>();
		user = new User();
		truckOwner = new FoodTruck();
		menuItem = new MenuItems();
		ingredients = new Ingredients();
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
			resultSet = userDAO.select(usernameQuery, username);
			
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
				
				user.setUsername(username);
				user.setPassword(pw);
				user.setName(name);
				user.setAddress(address);
				user.setEmail(email);
				
				boolean success = userDAO.insert(user);
				if (success){
					System.out.println("New User made!");
					System.out.println(user);
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
				
				truckOwner.setName(truckName);
				truckOwner.setPassword(pw);
				truckOwner.setOwner(owner);
				truckOwner.setFoodType(foodType);
				
				boolean success = foodDAO.insert(truckOwner);
				int truckID = getTruckID();
				truckOwner.setTruckID(truckID);
				
				if (success){
					System.out.println("New FoodTruck made!");
					makeMenuItemOption();
					//TODO: call next step of process
					System.exit(0);
				}
				else{
					System.out.println("Error creating FoodTruck. Please try again...");
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
	
	public float verifyInputFloat(float newFloat){
		while(!input.hasNextFloat()){
			input.next();
			System.out.println("I need a float please: ");
		}
		newFloat = input.nextFloat();
		input.nextLine();
		return newFloat;
	}
	
	public boolean confirmPW(String name, String column, String db) throws Exception{
		String hashedpw = null;
		String pw = input.nextLine();
		
		String passwordQuery = "SELECT password FROM " + db + " WHERE " + column + " = ?";
		resultSet = userDAO.select(passwordQuery, name);
		
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
		//TODO: fix
		
		return x;
	}
	
	public void makeMenuItem(){
		System.out.println("Let's build your menu item!");
		System.out.println("What is the name of the food item you would like to add: ");
		String title = input.nextLine();

		System.out.println("Sounds delicious!");
		System.out.println("What is the price of " + title + "?");
		float price = 0.0f;
		price = verifyInputFloat(price);
		while (price<0){
			System.out.println("Price must be positive: ");
			price = verifyInputFloat(price);
		}
		
		System.out.println("How many calories does " + title + " contain?");
		int calories = 0;
		calories = verifyInput(calories);
		while (calories < 0){
			System.out.println("I need a positive integer please: ");
			calories = verifyInput(calories);
		}
		
		System.out.println("And any special comments about your food (e.g. gluten free, dairy free, etc)?");
		String specialComments = input.nextLine();
		
		menuItem.setTitle(title);
		menuItem.setPrice(price);
		menuItem.setTotalCalories(calories);
		menuItem.setSpecialComments(specialComments);
		menuItem.setTruckID(truckOwner.getTruckID());
		
		menuDAO.insert(menuItem);
		int menuID = getMenuID();
		
		System.out.println("Finally, lets add the ingredients for " + title);
		int x = 0;
		do{
			System.out.println("(0 to exit) Ingredient " + (x+1) +": ");
			String y = input.nextLine();
			try{
				if (Integer.parseInt(y)==0){
					break;
				}
				else{
					continue;
				}
			}
			catch(NumberFormatException e){
				ingredients = new Ingredients();
				ingredients.setName(y);
				ingredients.setMenuID(menuID);
				
				ingredientsDAO.insert(ingredients);
				ingredientArray.add(ingredients);
			}
			x++;
		}while(x!=0);
		
		System.out.println(ingredientArray);
		
		menuItem.setIngredients(ingredientArray);
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
			}
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}
	
	public int getTruckID(){
		int truckID = 0;
		try {
			resultSet = foodDAO.select(truckOwner.getName());
			while (resultSet.next()){
				truckID = resultSet.getInt("truckID");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return truckID;
	}
	
	public int getMenuID(){
		int menuID = 0;
		try {
			resultSet = menuDAO.select(menuItem.getTitle());
			while (resultSet.next()){
				menuID = resultSet.getInt("menuID");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return menuID;
	}
}
