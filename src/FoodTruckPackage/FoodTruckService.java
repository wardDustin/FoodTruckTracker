package FoodTruckPackage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class FoodTruckService {

	private Scanner input;
	private Verify verify;
	private FoodTruck truckOwner;
	private Events event;
	private FoodTruckDAO foodDAO;
	private EventsDAO eventsDAO;
	private MenuItemsDAO menuDAO;
	private IngredientsDAO ingredientsDAO;
	private LocalDateTime dateTime;
	private Ingredients ingredients;
	private ArrayList<Ingredients> ingredientArray;
	
	public FoodTruckService(){
		input = new Scanner(System.in);
		verify = new Verify();
		truckOwner = new FoodTruck();
		event = new Events();
		foodDAO = new FoodTruckDAO();
		eventsDAO = new EventsDAO();
		menuDAO = new MenuItemsDAO();
		ingredientsDAO = new IngredientsDAO();
		dateTime = LocalDateTime.now();
		ingredients = new Ingredients();
		ingredientArray = new ArrayList<Ingredients>();
	}

	public void logOwnerIn(){
		String db = null;
		int selection = 0;
		System.out.println("\nHello, owner! Thank you for choosing FoodTruckTracker!");
		System.out.println("We just need to verify if you are new or a returning owner");
		System.out.println("What is the name of your food truck?");
		String truckName = input.nextLine();
		
		while (truckName.length() < 1 || truckName.trim().length() < 1){
			System.out.println("Invalid username, please try again: ");
			truckName = input.nextLine();
		}
		
		truckOwner = foodDAO.select(truckName);
		
		if (truckOwner.getName() == null){
			System.out.println("" + truckName + " does not exist. Would you like to register this Truck?");
			System.out.println("1| Yes  2| No");
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
			
			while (pw.length() < 1 || pw.trim().length() < 1){
				System.out.println("Invalid password, please try again: ");
				pw = input.nextLine();
			}
			
			pw = verify.encryptPW(pw);
			
			System.out.println("What is the name of the owner of " + truckName + "?");
			String owner = input.nextLine();
			
			while (owner.length() < 1 || owner.trim().length() < 1){
				System.out.println("Invalid name, please try again: ");
				owner = input.nextLine();
			}
			
			System.out.println("And what is the food type being served? (e.g. Mexican, Chinese, Greek, BBQ, etc)");
			String foodType = input.nextLine();
			
			truckOwner.setName(truckName);
			truckOwner.setPassword(pw);
			truckOwner.setOwner(owner);
			truckOwner.setFoodType(foodType);
			
			boolean success = foodDAO.insert(truckOwner);
			truckOwner = foodDAO.select(truckOwner.getName());
			int truckID = truckOwner.getTruckID();
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
			String pw = input.nextLine();
			db = "FoodTruckTracker.FoodTruck";
			boolean loggedIn = verify.confirmPW(truckName, "truckName", db, pw);
			if (!loggedIn){
				int x = 0;
				while(x<2){
					System.out.println("Password does not match Username! Please try again: ");
					pw = input.nextLine();
					loggedIn = verify.confirmPW(truckName, "truckName", db, pw);
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
	
	public void loggedInOwnerMenu(){
		System.out.println("\n" + truckOwner.getOwner() + ", welcome to the Main Menu!");
		System.out.println("What would you like to do next:");
		System.out.println("1| Truck Profile Menu  2| Food Menu menu  3| Events Menu  4| Exit");
		int selection = 0;
		selection = verify.verifyInput(selection);
		
		while (selection < 1 || selection > 4){
			System.out.println("Invalid Selection, please choose again!");
			selection = verify.verifyInput(selection);
		}
		
		if (selection == 1){
			truckProfileHandler();
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
			}
			else if (selection == 2){
				makeMenuItem();
				loggedInOwnerMenu();
			}
			else if (selection == 3){
				int truckID = truckOwner.getTruckID();
				ArrayList<String> listMenu = menuDAO.listMenu(truckID);
				int x = listMenu.size();
				for (int i = 0; i < x; i++){
					System.out.println("" + (i+1) + "| " + listMenu.get(i));
				}
				System.out.println("\nWhich would you like to update?");
				selection = verify.verifyInput(selection);
				
				while(selection<0 || selection>x){
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
				
				while(selection < 1 || selection > 6){
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
						//TODO: print off ingredients with numbers (like menuItems) make sure this is happening!!!
						System.out.println("Which ingredient would you like to remove (type the number of the ingredient): ");
						selection = verify.verifyInput(selection);
						
						while(selection < 1 || selection > x){
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
				int truckID = truckOwner.getTruckID();
				ArrayList<String> listMenu = menuDAO.listMenu(truckID);
				int x = listMenu.size();
				for (int i = 0; i < x; i++){
					System.out.println("" + (i+1) + "| " + listMenu.get(i));
				}
				System.out.println("\nWhich would you like to delete?");
				selection = verify.verifyInput(selection);
				
				while(selection < 0 || selection > x){
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
			eventHandler();
		}
		else{
			exit();
		}
	}
	
	public void viewMenu(){
		ArrayList<MenuItems> menuArray = new ArrayList<MenuItems>();
		int truckID = truckOwner.getTruckID();
		menuArray = new ArrayList<MenuItems>();
		System.out.println("\nYour current menu: ");
		menuArray = menuDAO.getWholeMenu(truckID);
		for (int i = 0; i < menuArray.size(); i++){
			System.out.println(menuArray.get(i));
		}
		loggedInOwnerMenu();
	}
	
	public void makeMenuItem(){
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
//		menuItem.setMenuID(menuItem.getMenuID());
		System.out.println(menuItem.getMenuID());
		
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
				ingredients.setMenuID(menuItem.getMenuID());
				
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
		System.out.println("Here is your menu: ");
		
		for (int i = 0; i < menuArray.size(); i++){
			System.out.println(menuArray.get(i));
		}
	}
	
	public void eventHandler(){
		//TODO: find discrepancy in time relaying
		System.out.println("\nWelcome to your Events Manager!");
		System.out.println("Here, you can: 1| Add an Event  2| View your Events  3| Edit an Event  4| Remove an Event  5| Return to Main Menu");
		int selection = 0;
		selection = verify.verifyInput(selection);
		
		while(selection < 1 || selection > 5){
			System.out.println("Invalid Selection, please choose again!");
			selection = verify.verifyInput(selection);
		}
		
		if (selection == 1){
			createEvent();
		}
		else if (selection == 2){
			viewEvents();
		}
		else if (selection == 3){
			editEvent();
		}
		else if (selection == 4){
			deleteEvent();
		}
		else{
			loggedInOwnerMenu();
		}
	}
	
	public void createEvent(){
		Events event = new Events();
		System.out.println("Time to make an event!");
		System.out.println("Where is the event happening?");
		String location = input.nextLine();
		
		System.out.println("What is the date and time? Must be written in this pattern: mm-dd-yyyy hh:mm AM/PM");
		
		makeDateTime();
		
		System.out.println("Okay, and do you have any notes or comments about the Event: ");
		String notes = input.nextLine();
		
		event.setLocation(location);
		event.setDateTime(dateTime);
		event.setNotes(notes);
		event.setTruckID(truckOwner.getTruckID());
		
		boolean success = eventsDAO.insert(event);
		if (success){
			System.out.println("Event created!");
		}
		else{
			System.out.println("Event creation failed!");
		}
		loggedInOwnerMenu();
	}
	
	public void viewEvents(){
		ArrayList<Events> eventArray = new ArrayList<Events>();
		int selection = 0;
		eventArray = eventsDAO.getEventsFromTruck(truckOwner.getTruckID());
		if (eventArray.size()==0){
			System.out.println("You have no Events at this time! Would you like to add some?");
			System.out.println("1| Yes  2| No");
			selection = verify.verifyInput(selection);
			
			while(selection != 1 && selection != 2){
				System.out.println("Invalid Selection, please choose again!");
				selection = verify.verifyInput(selection);
			}
			
			if (selection == 2){
				loggedInOwnerMenu();
			}
			
			createEvent();
		}
		
		System.out.println("\nHere are your events: ");
		
		for (int i = 0; i < eventArray.size(); i++){
			System.out.println(eventArray.get(i));
		}
		eventHandler();
	}
	
	public void editEvent(){
		int selection = 0;
		boolean yes = true;
		ArrayList<Events> eventArray = new ArrayList<Events>();
		eventArray = eventsDAO.getEventsFromTruck(truckOwner.getTruckID());
		
		if (eventArray.size() == 0){
			System.out.println("You have no events currently. Would you like to create one now?");
			System.out.println("1| Yes  2| No");
			selection = verify.verifyInput(selection);
			while(selection != 1 && selection != 2){
				System.out.println("Invalid Selection, please choose again!");
				selection = verify.verifyInput(selection);
			}
			
			if (selection == 1){
				createEvent();
			}
			else{
				loggedInOwnerMenu();
			}
		}
		int x = eventArray.size();
		System.out.println("\nHere are your events: ");
		
		eventArray = eventsDAO.getEventsFromTruck(truckOwner.getTruckID());
		for (int i = 0; i < x; i++){
			System.out.println((i+1) + "| " + eventArray.get(i));
		}
		System.out.println("\nWhich would you like to select?");
		selection = verify.verifyInput(selection);
		
		while(selection < 1 || selection > x){
			System.out.println("Invalid Selection, please choose again!");
			selection = verify.verifyInput(selection);
		}
		event = eventArray.get(selection-1);
		
		System.out.println("What would you like to change?");
		System.out.println("1| Location  2| Date or Time  3| Notes about the Event  4| Nevermind");
		selection = verify.verifyInput(selection);
		while(selection < 1 || selection > 4){
			System.out.println("Invalid Selection, please choose again!");
			selection = verify.verifyInput(selection);
		}
		
		if (selection == 1){
			yes = true;
			System.out.println("The location is currently: " + event.getLocation());
			System.out.println("What would you like to change it to: ");
			String newLocation = input.nextLine();
			System.out.println("Are you sure? 1| Change location  2| Nevermind");
			selection = verify.verifyInput(selection);
			
			while (selection != 1 && selection != 2){
				System.out.println("Invalid Selection, please choose again!");
				selection = verify.verifyInput(selection);
			}
			
			if (selection == 2){
				loggedInOwnerMenu();
			}
			
			boolean success = eventsDAO.update(yes, newLocation, event.getEventID());
			if (success){
				System.out.println("Location update successful!");
			}
			else{
				System.out.println("Could not change location!");
			}
			loggedInOwnerMenu();
		}
		else if (selection == 2){
			x = 0;
			System.out.println("The date and time are currently set to: " + event.getDateTime());
			System.out.println("What would you like to change it to? Must be written in this pattern: mm-dd-yyyy hh:mm AM/PM");
			
			dateTime = makeDateTime();
		
			System.out.println("Are you sure? 1| Change Date and Time  2| Nevermind");
			selection = verify.verifyInput(selection);
			
			while (selection != 1 && selection != 2){
				System.out.println("Invalid Selection, please choose again!");
				selection = verify.verifyInput(selection);
			}
			
			if (selection == 2){
				loggedInOwnerMenu();
			}
			
			boolean success = eventsDAO.update(dateTime, event.getEventID());
			if (success){
				System.out.println("Date and Time change successful!");
			}
			else{
				System.out.println("Could not change Date or Time!");
			}
			loggedInOwnerMenu();
		}
		else if (selection == 3){
			yes = false;
			System.out.println("Your notes are currently: " + event.getNotes());
			System.out.println("What would you like to change them to: ");
			String newNotes = input.nextLine();
			System.out.println("Are you sure? 1| Change notes  2| Nevermind");
			selection = verify.verifyInput(selection);
			
			while (selection != 1 && selection != 2){
				System.out.println("Invalid Selection, please choose again!");
				selection = verify.verifyInput(selection);
			}
			
			if (selection == 2){
				loggedInOwnerMenu();
			}
			
			boolean success = eventsDAO.update(yes, newNotes, event.getEventID());
			if (success){
				System.out.println("Note update successful!");
			}
			else{
				System.out.println("Could not change notes!");
			}
			loggedInOwnerMenu();
		}
		else{
			eventHandler();
		}
	}
	
	public void deleteEvent(){
		ArrayList<Events> eventArray = new ArrayList<Events>();
		Events event = new Events();
		int selection = 0;
		System.out.println("\nHere are your events: ");
		
		eventArray = eventsDAO.getEventsFromTruck(truckOwner.getTruckID());
		int x = eventArray.size();
		for (int i = 0; i < x; i++){
			System.out.println((i+1) + "| " + eventArray.get(i));
		}
		System.out.println("\nWhich would you like to delete?");
		selection = verify.verifyInput(selection);
		
		while(selection < 1 || selection > x){
			System.out.println("Invalid Selection, please choose again!");
			selection = verify.verifyInput(selection);
		}
		
		event = eventArray.get(selection-1);
		
		System.out.println("Are you sure? 1| Delete Event  2| Nevermind");
		selection = verify.verifyInput(selection);
		
		while (selection != 1 && selection != 2){
			System.out.println("Invalid Selection, please choose again!");
			selection = verify.verifyInput(selection);
		}
		
		if (selection == 2){
			loggedInOwnerMenu();
		}
		
		boolean success = eventsDAO.delete(event);
		if (success){
			System.out.println("Deletion successful!");
		}
		else{
			System.out.println("Could not delete the Event!");
		}
		loggedInOwnerMenu();
	}
	
	public void truckProfileHandler(){
		FavoritesDAO favesDAO = new FavoritesDAO();
		Favorites fave = new Favorites();
		int x = 0;
		int selection = 0;
		int truckID = truckOwner.getTruckID();
		System.out.println("Truck: " + truckOwner.getName() + "  Owner: " + truckOwner.getOwner() + "  Food Category: " + truckOwner.getFoodType() + "\n");
		System.out.println("What would you like to change:");
		System.out.println("1| Your Truck's name  2| Your password  3| Owner's name  4| Food category  5| Nevermind");
		selection = verify.verifyInput(selection);
		
		while (selection < 1 || selection > 5){
			System.out.println("Invalid Selection, please choose again!");
			selection = verify.verifyInput(selection);
		}
		
		if (selection == 1){
			String oldTruckName = truckOwner.getName();
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
				boolean success2 = favesDAO.update(x, newTruckName, truckID);
				if (success2){
					truckOwner.setName(newTruckName);
					fave.setName(newTruckName);
					System.out.println("Updated successfully!\n");
				}
				else{
					foodDAO.update(x, oldTruckName, truckID);
					System.out.println("Update failed\n");
				}
			}
			else{
				System.out.println("Update failed\n");
			}
			
			loggedInOwnerMenu();
		}
		else if (selection == 2){
			System.out.println("Submit your (soon-to-be) old password: ");
			String pw = input.nextLine();
			String db = "FoodTruckTracker.FoodTruck";
			boolean loggedIn = verify.confirmPW(truckOwner.getName(), "truckName", db, pw);
			if (!loggedIn){
				x = 0;
				while(x<2){
					System.out.println("Password does not match Username! Please try again: ");
					pw = input.nextLine();
					loggedIn = verify.confirmPW(truckOwner.getName(), "truckName", db, pw);
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
			String oldOwnerName = truckOwner.getOwner();
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
				boolean success2 = favesDAO.update(x, newOwnerName, truckID);
				if (success2){
					truckOwner.setOwner(newOwnerName);
					fave.setOwner(newOwnerName);
					System.out.println("Updated successfully!\n");
				}
				else{
					foodDAO.update(x, oldOwnerName, truckID);
					System.out.println("Update failed\n");
				}
			}
			else{
				System.out.println("Update failed\n");
			}
			
			loggedInOwnerMenu();
		}
		else if (selection == 4){
			String oldFoodType = truckOwner.getFoodType();
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
				boolean success2 = favesDAO.update(x, newFoodType, truckID);
				if (success2){
					truckOwner.setFoodType(newFoodType);
					fave.setFoodType(newFoodType);
					System.out.println("Updated successfully!\n");
				}
				else{
					foodDAO.update(x, oldFoodType, truckID);
					System.out.println("Update failed\n");
				}
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
	
	public LocalDateTime makeDateTime(){
		String dateAndTime = input.nextLine();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");
		
		try{
			dateTime = LocalDateTime.parse(dateAndTime, formatter);
			System.out.println(dateTime);//TODO: remove after testing
		}
		catch(DateTimeParseException e){
			System.out.println("The format is: mm-dd-yyyy hh:mm AM/PM ... Please try again!");
			makeDateTime();
		}
		return dateTime;
	}
	
	public boolean changePW(){
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
	
	public void exit(){
		System.out.println("\nThank you for using FoodTruckTracker!");
		System.out.println("Exiting...");
		System.exit(0);
	}
}
