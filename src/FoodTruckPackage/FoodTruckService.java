package FoodTruckPackage;

import java.util.ArrayList;
import java.util.Scanner;

public class FoodTruckService {

	private Scanner input = new Scanner(System.in);
	private Verify verify = new Verify();
	private FoodTruck truckOwner = new FoodTruck();
	private FoodTruckDAO foodDAO;
	private Ingredients ingredients = new Ingredients();
	private MenuItemsDAO menuDAO = new MenuItemsDAO();
	private IngredientsDAO ingredientsDAO = new IngredientsDAO();
	private ArrayList<Ingredients> ingredientArray = new ArrayList<Ingredients>();
	
	public FoodTruckService() throws Exception{
		foodDAO = new FoodTruckDAO();
	}

	public void logOwnerIn() throws Exception{
		String db = null;
		System.out.println("Hello, owner! Thank you for choosing FoodTruckTracker!");
		System.out.println("We just need to verify if you are new or a returning owner");
		System.out.println("What is the name of your food truck?");
		String truckName = input.nextLine();
		
		truckOwner = foodDAO.select(truckName);
		
		if (truckOwner.getName() == null){
			System.out.println("" + truckName + " does not exist. Would you like to register this Truck?");
			System.out.println("1| Yes  2| No");
			int selection = 0;
			selection = verify.verifyInput(selection);
			
			while (selection != 1 && selection != 2){
				System.out.println("Invalid Selection, please choose again!");
				selection = verify.verifyInput(selection);
			}
			
			if (selection == 2){
				logOwnerIn();
			}
			
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
			}
			else{
				System.out.println("Error creating FoodTruck. Please try again...");
				exit();
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
				exit();
			}
			else{
				System.out.println("You are logged in, " + truckOwner.getOwner()  + "!\n");
				loggedInOwnerMenu();
				System.exit(0);
			}
		}
	}
	
	public void loggedInOwnerMenu() throws Exception{
		System.out.println("" + truckOwner.getOwner() + ", what would you like to do next?");
		System.out.println("1| Change your truck information  2| Manage your Menu  3| Manage your events  4| Exit");
		int selection = 0;
		selection = verify.verifyInput(selection);
		
		while (selection != 1 && selection != 2 && selection != 3 && selection!=4){
			System.out.println("Invalid Selection, please choose again!");
			selection = verify.verifyInput(selection);
		}
		
		if (selection == 1){
			int x = 0;
			int truckID = truckOwner.getTruckID();
			System.out.println("Truck: " + truckOwner.getName() + "  Owner: " + truckOwner.getOwner() + "  Food Category: " + truckOwner.getFoodType() + "\n");
			System.out.println("What would you like to change:");
			System.out.println("1| Your Truck's name  2| Your password  3| Owner's name  4| Food category  5| Nevermind");
			selection = verify.verifyInput(selection);
			
			while (selection != 1 && selection != 2 && selection != 3 && selection!=4 && selection!=5){
				System.out.println("Invalid Selection, please choose again!");
				selection = verify.verifyInput(selection);
			}
			
			if (selection == 1){
				System.out.println("What would you like to change your Truck's name to: ");
				String newTruckName = input.nextLine();
				System.out.println("Are you sure? 1| Change name  2| Nevermind");
				selection = verify.verifyInput(selection);
				
				while (selection != 1 && selection != 2){
					System.out.println("Invalid Selection, please choose again!");
					selection = verify.verifyInput(selection);
				}
				
				if (selection == 2){
					loggedInOwnerMenu();
				}
				
				x = 1;
				boolean success = foodDAO.update(x, newTruckName, truckID);
				if (success){
					truckOwner.setName(newTruckName);
					System.out.println("Updated successfully!\n");
				}
				else{
					System.out.println("Update failed\n");
				}
				
				loggedInOwnerMenu();
			}
			else if (selection == 2){
				System.out.println("Submit your (soon-to-be) old password: ");
				String db = "FoodTruckTracker.FoodTruck";
				boolean loggedIn = verify.confirmPW(truckOwner.getName(), "truckName", db);//TODO: not working
				if (!loggedIn){
					x = 0;
					while(x<2){
						System.out.println("Password does not match Username! Please try again: ");
						loggedIn = verify.confirmPW(truckOwner.getName(), "truckName", db);//fugnuggets
						if(loggedIn){
							boolean success = changePW();
							if (success){
								System.out.println("Password updated successfully!\n");
							}
							else{
								System.out.println("Password update failed\n");
							}
							loggedInOwnerMenu();
						}
						x++;
					}
					System.out.println("Unsuccessful password change");
					loggedInOwnerMenu();
				}
				else{
					boolean success = changePW();
					if (success){
						System.out.println("Password updated successfully!\n");
					}
					else{
						System.out.println("Password update failed\n");
					}
					loggedInOwnerMenu();
				}
			}
			else if (selection == 3){
				System.out.println("What would you like to change the Owner's name to: ");
				String newOwnerName = input.nextLine();
				System.out.println("Are you sure? 1| Change owner name  2| Nevermind");
				selection = verify.verifyInput(selection);
				
				while (selection != 1 && selection != 2){
					System.out.println("Invalid Selection, please choose again!");
					selection = verify.verifyInput(selection);
				}
				
				if (selection == 2){
					loggedInOwnerMenu();
				}
				
				x = 2;
				boolean success = foodDAO.update(x, newOwnerName, truckID);
				if (success){
					truckOwner.setOwner(newOwnerName);
					System.out.println("Updated successfully!\n");
				}
				else{
					System.out.println("Update failed\n");
				}
				
				loggedInOwnerMenu();
			}
			else if (selection == 4){
				System.out.println("What would you like to change the food type to: ");
				String newFoodType = input.nextLine();
				System.out.println("Are you sure? 1| Change food type  2| Nevermind");
				selection = verify.verifyInput(selection);
				
				while (selection != 1 && selection != 2){
					System.out.println("Invalid Selection, please choose again!");
					selection = verify.verifyInput(selection);
				}
				
				if (selection == 2){
					loggedInOwnerMenu();
				}
				
				x = 3;
				boolean success = foodDAO.update(x, newFoodType, truckID);
				if (success){
					truckOwner.setFoodType(newFoodType);
					System.out.println("Updated successfully!\n");
				}
				else{
					System.out.println("Update failed\n");
				}
				
				loggedInOwnerMenu();
			}
			else{
				loggedInOwnerMenu();
			}
		}
		else if (selection == 2){ 
			boolean hasAMenu = menuDAO.newTruck(truckOwner.getTruckID());
			if (!hasAMenu){
				makeMenuItem();
			}
			
			System.out.println("\nWelcome to your Menu menu! Here, you can: ");
			System.out.println("1| View Menu  2| Add to your Menu  3| Update existing menu items  4| Delete menu items  5| Exit");
			selection = verify.verifyInput(selection);
			
			while (selection != 1 && selection != 2 && selection != 3 && selection!=4 && selection!=5){
				System.out.println("Invalid Selection, please choose again!");
				selection = verify.verifyInput(selection);
			}
			
			if (selection == 1){
				viewMenu();
				loggedInOwnerMenu();
			}
			else if (selection == 2){
				makeMenuItem();
				loggedInOwnerMenu();
			}
			else if (selection == 3){
				int truckID = getTruckID();
				ArrayList<String> listMenu = menuDAO.listMenu(truckID);
				int x = listMenu.size();
				for (int i = 0; i < x; i++){
					System.out.println("" + (i+1) + "| " + listMenu.get(i));
				}
				System.out.println("\nWhich would you like to update?");
				selection = verify.verifyInput(selection);
				
				while(selection<0 && selection>x){
					System.out.println("Invalid Selection, please choose again!");
					selection = verify.verifyInput(selection);
				}
				
				String foodName = listMenu.get(selection-1);
				System.out.println(foodName);
				MenuItems menuItem = new MenuItems();
				menuItem = menuDAO.getMenuItem(foodName, truckID);
				int menuID = menuItem.getMenuID();
				boolean yes = true;
				System.out.println("Here is the menu item you selected:\n" + menuItem + "\n");
				System.out.println("What part would you like to update?");
				System.out.println("1| Food Title  2| Price  3| Total Calories  4| Special Comments  5| Ingredients  6| Nevermind");
				selection = verify.verifyInput(selection);
				
				while(selection != 1 && selection != 2 && selection != 3 && selection!=4 && selection!=5 && selection!=6){
					System.out.println("Invalid Selection, please choose again!");
					System.out.println("1| Food Title  2| Price  3| Total Calories  4| Special Comments  5| Ingredients  6| Nevermind");
					selection = verify.verifyInput(selection);
				}
				
				if (selection == 1){
					System.out.println("What is the name you would like to update " + menuItem.getTitle() + " to: ");
					String title = input.nextLine();
					boolean success = menuDAO.updateItem(yes, title, menuID);
					if (success){
						System.out.println("Updated successfully!\n");
					}
					else{
						System.out.println("Update failed\n");
					}
					loggedInOwnerMenu();
				}
				else if (selection == 2){
					System.out.println("What would you like to change the price to: ");
					float price = 0.0f;
					price = verify.verifyInputFloat(price);
					while (price<0){
						System.out.println("Price must be positive: ");
						price = verify.verifyInputFloat(price);
					}
					boolean success = menuDAO.updateItem(price, menuID);
					if (success){
						System.out.println("Updated successfully!\n");
					}
					else{
						System.out.println("Update failed\n");
					}
					loggedInOwnerMenu();
				}
				else if (selection == 3){
					System.out.println("What are the total calories changing to: ");
					int calories = 0;
					calories = verify.verifyInput(calories);
					while (calories < 0){
						System.out.println("I need a positive integer please: ");
						calories = verify.verifyInput(calories);
					}
					boolean success = menuDAO.updateItem(calories, menuID);
					if (success){
						System.out.println("Updated successfully!\n");
					}
					else{
						System.out.println("Update failed\n");
					}
					loggedInOwnerMenu();
				}
				else if (selection == 4){
					System.out.println("And any special comments about your food (e.g. gluten free, dairy free, etc)?");
					String specialComments = input.nextLine();
					yes = false;
					boolean success = menuDAO.updateItem(yes, specialComments, menuID);
					if (success){
						System.out.println("Updated successfully!\n");
					}
					else{
						System.out.println("Update failed\n");
					}
					loggedInOwnerMenu();
				}
				else if (selection == 5){
					//TODO: individual ingredients... delete query and then an insert query
					//TODO: make new methods in DAO boolean instead of void to make sure of success
					System.out.println("Here are the list of ingredients: ");
					
					ArrayList<String> listIngredients = ingredientsDAO.listIngredients(menuItem.getMenuID()); //listIngredients
					x = listIngredients.size();
					for (int i = 0; i < x; i++){
						System.out.println("" + (i+1) + "| " + listIngredients.get(i));
					}
					
					System.out.println("Would you like to 1| Add ingredients  2| Remove ingredients  3| Nevermind ");
					selection = verify.verifyInput(selection);
					
					while(selection!=1 && selection!=2 && selection!=3){
						System.out.println("Invalid Selection, please choose again!");
						selection = verify.verifyInput(selection);
					}
					
					if (selection == 1){
						System.out.println("Ok, lets add ingredients:");
						int z = 0;
						ingredientArray = new ArrayList<Ingredients>();
						do{
							System.out.println("(0 to exit) Ingredient " + (z+1) +": ");
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
								ingredients.setMenuID(menuItem.getMenuID());
								
								boolean success = ingredientsDAO.insert(ingredients);
								if (success){
									System.out.println("Updated successfully!\n");
								}
								else{
									System.out.println("Update failed\n");
								}
							}
							z++;
						}while(z!=0);
						loggedInOwnerMenu();
					}
					else if(selection == 2){
						//TODO: print off ingredients with numbers (like menuItems)
						System.out.println("Which ingredient would you like to remove: ");
						selection = verify.verifyInput(selection);
						
						while(selection<0 && selection>x){
							System.out.println("Invalid Selection, please choose again!");
							selection = verify.verifyInput(selection);
						}
						
						String selectedIng = listIngredients.get(selection-1);
						
						boolean success = ingredientsDAO.deleteIngredient(selectedIng);
						if (success){
							System.out.println("Deletion successfull!\n");
						}
						else{
							System.out.println("Deletion failed\n");
						}
						
						loggedInOwnerMenu();
					}
					else{
						loggedInOwnerMenu();
					}
				}
				else{
					loggedInOwnerMenu();
				}
			}
			else if (selection == 4){
				int truckID = getTruckID();
				ArrayList<String> listMenu = menuDAO.listMenu(truckID);
				int x = listMenu.size();
				for (int i = 0; i < x; i++){
					System.out.println("" + (i+1) + "| " + listMenu.get(i));
				}
				System.out.println("\nWhich would you like to delete?");
				selection = verify.verifyInput(selection);
				
				while(selection<0 && selection>x){
					System.out.println("Invalid Selection, please choose again!");
					selection = verify.verifyInput(selection);
				}
				
				String foodName = listMenu.get(selection-1);
				System.out.println(foodName);
				MenuItems menuItem = new MenuItems();
				menuItem = menuDAO.getMenuItem(foodName, truckID);
				int menuID = menuItem.getMenuID();
				
				boolean success = ingredientsDAO.deleteAllIngredients(menuID);
				if (success){
					success = menuDAO.deleteMenuItem(foodName, truckID);
					if (success){
						System.out.println("Deletion successfull!\n");
					}
					else{
						System.out.println("Deletion failed\n");
					}
				}
				else{
					System.out.println("Deletion failed\n");
				}
				loggedInOwnerMenu();
			}
			else{
				exit();
			}
		}
		else if (selection == 3){
			//TODO: Manage Events
		}
		else{
			exit();
		}
	}
	
	public void viewMenu() throws Exception{
		ArrayList<MenuItems> menuArray = new ArrayList<MenuItems>();
		int truckID = getTruckID();
		menuArray = new ArrayList<MenuItems>();
		System.out.println("Your current menu: ");
		menuArray = menuDAO.getMenu(truckID);
		System.out.println(menuArray + "\n");
	}
	
	public void makeMenuItem() throws Exception{
		ArrayList<MenuItems> menuArray = new ArrayList<MenuItems>();
		MenuItems menuItem = new MenuItems();
		System.out.println("Let's build your Menu!");
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
		menuItem = menuDAO.getMenuItem(title, truckOwner.getTruckID());
		int menuID = menuItem.getMenuID();
		System.out.println(menuID);
		
		//TODO: fix!
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
				
				boolean success = ingredientsDAO.insert(ingredients); //error here???
				if (success){
					System.out.println("Updated successfully!\n");
				}
				else{
					System.out.println("Update failed\n");
				}
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
	
	public boolean changePW() throws Exception{
		int x = 4;
		System.out.println("Okay, now we need your new password: ");
		String newInput = input.nextLine();
		System.out.println("Please repeat new password: ");
		String newInput2 = input.nextLine();
		
		int y = 0;
		while (!newInput.equals(newInput2)){
			if (y<3){
				System.out.println("Passwords did not match. Please try again\n");
				System.out.println("New password: ");
				newInput = input.nextLine();
				System.out.println("Repeat new password: ");
				newInput2 = input.nextLine();
				y++;
			}
			else{
				System.out.println("Password change failure");
				exit();
			}
		}
		
		String hashedPW = verify.encryptPW(newInput);
		
		boolean success = foodDAO.update(x, hashedPW, truckOwner.getTruckID());
		if (success){
			truckOwner.setPassword(hashedPW);
			return true;
		}
		else{
			return false;
		}
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
	
	public void exit(){
		System.out.println("\nThank you for using FoodTruckTracker!");
		System.out.println("Exiting...");
		System.exit(0);
	}
}
