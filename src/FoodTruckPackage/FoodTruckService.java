package FoodTruckPackage;

import java.util.ArrayList;
import java.util.Scanner;

public class FoodTruckService {

	private Scanner input = new Scanner(System.in);
	private Verify verify = new Verify();
	private FoodTruck truckOwner = new FoodTruck();
	private FoodTruckDAO foodDAO = new FoodTruckDAO();
	private MenuItems menuItem = new MenuItems();
	private Ingredients ingredients = new Ingredients();
	private MenuItemsDAO menuDAO = new MenuItemsDAO();
	private IngredientsDAO ingredientsDAO = new IngredientsDAO();
	private ArrayList<MenuItems> menuArray = new ArrayList<MenuItems>();
	private ArrayList<Ingredients> ingredientArray = new ArrayList<Ingredients>();
	
	public FoodTruckService(){}

	public void logOwnerIn() throws Exception{
		String db = null;
		System.out.println("Hello, owner! Thank you for choosing FoodTruckTracker!");
		System.out.println("We just need to verify if you are new or a returning owner");
		System.out.println("What is the name of your food truck?");
		String truckName = input.nextLine();
		
		truckOwner = foodDAO.select(truckName);
		
		if (truckOwner == null){
			System.out.println("Thank you for registering " + truckName + "!");
			System.out.println("Create a password please: ");
			String pw = input.nextLine();
			pw = verify.encryptPW(pw);
			
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
				loggedInOwnerMenu();
				//TODO: see if this goes to the end of program...
				System.exit(0);
			}
			else{
				System.out.println("Error creating FoodTruck. Please try again...");
//				menu.exit();
			}
		}
		else{
			System.out.println("Welcome back, " + truckOwner.getName());
			System.out.println("Please enter your password to confirm: ");
			db = "FoodTruckTracker.FoodTruck";
			boolean loggedIn = verify.confirmPW(truckName, "truckName", db);
			if (!loggedIn){
				int x = 0;
				while(x<2){
					System.out.println("Password does not match Username! Please try again: ");
					loggedIn = verify.confirmPW(truckName, "truckName", db);
					if(loggedIn){
						System.out.println("You are logged in, " + truckOwner.getOwner()  + "!\n");
						loggedInOwnerMenu();
						System.exit(0);
					}
					x++;
				}
				System.out.println("Unsuccessful login");
//				menu.exit();
			}
			else{
				System.out.println("You are logged in, " + truckOwner.getOwner()  + "!\n");
				loggedInOwnerMenu();
				System.exit(0);
			}
		}
	}
	
	public void loggedInOwnerMenu(){
		//TODO: handles all the features for a food truck owner logged in....
		System.out.println("What would you like to do next?");
		System.out.println("1| Change your truck information  2| Manage your menu  3| Manage your events  4| Exit");
		int selection = 0;
		selection = verify.verifyInput(selection);
		
		while (selection != 1 && selection != 2 && selection != 3 && selection!=4){
			System.out.println("Invalid Selection, please choose again!");
			selection = verify.verifyInput(selection);
		}
		
		if (selection == 1){
			
		}
		else if (selection == 2){
			//TODO: figure out order...
			viewMenu();
			makeMenuItem();
			loggedInOwnerMenu();
			
			//TODO: make this view part into a method so that it can be used again elsewhere
			//SELECT mi.foodName, mi.price, mi.calories, mi.specialComments, ind.ingredient FROM FoodTruck ft LEFT JOIN Menu mi ON mi.truckID = ft.truckID LEFT JOIN Ingredients ind ON ind.menuID = mi.menuID;
			
		}
		else if (selection == 3){
			
		}
		else{
//			menu.exit();
		}
	}
	
	public void viewMenu(){
		int truckID = getTruckID();
		menuArray = new ArrayList<MenuItems>();
		System.out.println("Your current menu: ");
		menuArray = foodDAO.getMenu(truckID);
		System.out.println(menuArray + "\n");
//		menu.exit();
	}
	
	public void makeMenuItem(){
		menuItem = new MenuItems();
		System.out.println("Let's build your menu item!");
		System.out.println("What is the name of the food item you would like to add: ");
		String title = input.nextLine();

		System.out.println("Sounds delicious!");
		System.out.println("What is the price of " + title + "?");
		float price = 0.0f;
		price = verify.verifyInputFloat(price);
		while (price<0){
			System.out.println("Price must be positive: ");
			price = verify.verifyInputFloat(price);
		}
		
		System.out.println("How many calories does " + title + " contain?");
		int calories = 0;
		calories = verify.verifyInput(calories);
		while (calories < 0){
			System.out.println("I need a positive integer please: ");
			calories = verify.verifyInput(calories);
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
		ingredientArray = new ArrayList<Ingredients>();
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
		menuArray.add(menuItem);
		truckOwner.setMenuItems(menuArray);
		System.out.println("Here is your menu: " + menuArray + "\n");
	}
	
	public void deleteMenuItem(){
		//TODO: show ordered menu first
		//TODO: ask if they want to delete ingredients first
		//TODO: then ask for whole menu items
	}
	
	public int getTruckID(){
		int truckID = 0;
		try {
			truckOwner = foodDAO.select(truckOwner.getName());
			truckID = truckOwner.getTruckID();
		} catch (Exception e) {
			System.out.println(e);
		}
		return truckID;
	}
	
	public int getMenuID(){
		int menuID = 0;
		try {
			menuItem = menuDAO.select(menuItem.getTitle());
			menuID = menuItem.getMenuID();
		} catch (Exception e) {
			System.out.println(e);
		}
		return menuID;
	}
}
