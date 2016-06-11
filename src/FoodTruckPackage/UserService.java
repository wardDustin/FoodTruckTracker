package FoodTruckPackage;

import java.util.ArrayList;
import java.util.Scanner;

public class UserService {
	private Scanner input;
	private User user;
	private UserDAO userDAO;
	private FoodTruckDAO foodDAO;
	private MenuItemsDAO menuDAO;
	private FavoritesDAO favesDAO;
	private Verify verify;
	
	public UserService(){
		input = new Scanner(System.in);
		user = new User();
		userDAO = new UserDAO();
		foodDAO = new FoodTruckDAO();
		menuDAO = new MenuItemsDAO();
		favesDAO = new FavoritesDAO();
		verify = new Verify();
	}
	
	public void logUserIn(){
		System.out.println("\nExcellent! Welcome!!");
		System.out.println("Let's check and see if you've used FoodTruckTracker before");
		System.out.println("First, please enter a username between 3 and 15 characters: ");
		String username = input.nextLine();
		
		while (username.length() > 15 || username.length() < 3){
			System.out.println("Sorry. Your username is incorrect. It must be between 3 and 15 characters!");
			username = input.nextLine();
		}
		
		boolean found = userDAO.selectUsername(username);
		
		if (!found){
			System.out.println("Looks like you're new here!");
			System.out.println("Would you like to register " + username + " as a new user? 1| Yes  2| No");
			int selection = 0;
			selection = verify.verifyInput(selection);
			
			while (selection != 1 && selection != 2){
				System.out.println("Invalid Selection, please choose again!");
				selection = verify.verifyInput(selection);
			}
			
			if (selection == 2){
				exit();
			}
			System.out.println("Create a password please: ");
			String pw = input.nextLine();
			pw = verify.encryptPW(pw);
				
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
				user = userDAO.selectAll(username);
				loggedInUserMenu();
			}
			else{
				System.out.println("Creation of Username failed!");
				exit();
			}
		} 
		else{
			System.out.println("Thank you " + username + "!");
			System.out.println("Enter your password: ");
			String db = "FoodTruckTracker.User";
			boolean loggedIn = verify.confirmPW(username, "username", db);
			if (!loggedIn){
				int x = 0;
				while(x<2){
					System.out.println("Password does not match Username! Please try again: ");
					loggedIn = verify.confirmPW(username, "username", db);
					if(loggedIn){
						System.out.println("You are logged in");
						x=2;
						user = userDAO.selectAll(username);
						loggedInUserMenu();
					}
					x++;
				}
				System.out.println("Unsuccessful login");
				exit();
			}
			else{
				System.out.println("You are logged in!");
				user = userDAO.selectAll(username);
				loggedInUserMenu();
			}
		}
	}
	
	public void loggedInUserMenu(){
		System.out.println("\nWelcome, " + user.getName() + ", what would you like to do?");
		System.out.println("1| Profile Menu  2| Favorites Menu  3| FoodTruck Menu  4| Events Menu  5| Exit");
		int selection = 0;
		selection = verify.verifyInput(selection);
		
		while (selection < 1 || selection > 5){
			System.out.println("Invalid Selection, please choose again!");
			selection = verify.verifyInput(selection);
		}
		
		if (selection == 1){
			userProfileHandler();
		}
		else if (selection == 2){
			userFavoritesHandler();
		}
		else if (selection == 3){
			userTruckHandler();
		}
		else if (selection == 4){
			userEventsHandler();
		}
		else{
			exit();
		}
	}
	
	public void userProfileHandler(){
		System.out.println(user);
		System.out.println("Would you like to change your profile?");
		System.out.println("1| Yes  2| No");
		int selection = 0;
		selection = verify.verifyInput(selection);
		
		while (selection!=1 && selection!=2){
			System.out.println("Invalid Selection, please choose again!");
			selection = verify.verifyInput(selection);
		}
		
		if (selection == 2){
			loggedInUserMenu();
		}
		
		System.out.println("What would you like to change?");
		System.out.println("1| Username  2| Password  3| Name  4| Address  5| Email  6| Nevermind");
		selection = verify.verifyInput(selection);
		
		while (selection < 1 || selection > 6){
			System.out.println("Invalid Selection, please choose again!");
			selection = verify.verifyInput(selection);
		}
		
		int x = 0;
		if (selection == 1){
			System.out.println("Your username is currently: " + user.getUsername());
			System.out.println("What would you like to change it to: ");
			String newUsername = input.nextLine();
			
			while (newUsername.length() > 15 || newUsername.length() < 3){
				System.out.println("Sorry. Your username is incorrect. It must be between 3 and 15 characters!");
				newUsername = input.nextLine();
			}
			
			boolean found = userDAO.selectUsername(newUsername);
			if (found){
				System.out.println("Sorry, username is taken!");
				System.out.println("Would you like to try again? 1| Yes  2| No");
				selection = verify.verifyInput(selection);
				
				while (selection != 1 && selection != 2){
					System.out.println("Invalid Selection, please choose again!");
					selection = verify.verifyInput(selection);
				}
				
				if (selection == 1){
					userProfileHandler();
				}
				else{
					loggedInUserMenu();
				}
			}
			else{
				System.out.println(newUsername + " is available. Are you sure you want this change? 1| Yes  2| No");
				selection = verify.verifyInput(selection);
				
				while (selection != 1 && selection != 2){
					System.out.println("Invalid Selection, please choose again!");
					selection = verify.verifyInput(selection);
				}
				
				if (selection == 2){
					loggedInUserMenu();
				}
				
				x=1;
				boolean success = userDAO.update(x, newUsername, user.getUsername());
				if (success){
					user.setUsername(newUsername);
					System.out.println("Updated successfully!\n");
				}
				else{
					System.out.println("Update failed\n");
				}
				loggedInUserMenu();
			}
		}
		else if (selection == 2){
			System.out.println("Submit your (soon-to-be) old password: ");
			String db = "FoodTruckTracker.User";
			boolean loggedIn = verify.confirmPW(user.getUsername(), "username", db);
			if (!loggedIn){
				x = 0;
				while(x<2){
					System.out.println("Password does not match Username! Please try again: ");
					loggedIn = verify.confirmPW(user.getUsername(), "username", db);
					if(loggedIn){
						boolean pwChanged = changePW();
						if (pwChanged){
							System.out.println("Password updated successfully!\n");
						}
						else{
							System.out.println("Password update failed\n");
						}
						loggedInUserMenu();
					}
					x++;
				}
				System.out.println("Unsuccessful password change");
				loggedInUserMenu();
			}
			else{
				boolean pwChanged = changePW();
				if (pwChanged){
					System.out.println("Password updated successfully!\n");
				}
				else{
					System.out.println("Password update failed\n");
				}
				loggedInUserMenu();
			}
		}
		else if (selection == 3){
			x=2;
			System.out.println("Your name is currently: " + user.getName());
			System.out.println("What would you like to change it to:");
			String newName = input.nextLine();
			
			System.out.println("Are you sure? 1| Yes  |2 No");
			selection = verify.verifyInput(selection);
			
			while (selection != 1 && selection != 2){
				System.out.println("Invalid Selection, please choose again!");
				selection = verify.verifyInput(selection);
			}
			
			if (selection == 2){
				loggedInUserMenu();
			}
			
			boolean success = userDAO.update(x, newName, user.getUsername());
			if (success){
				user.setName(newName);
				System.out.println("Updated successfully!\n");
			}
			else{
				System.out.println("Name update failed\n");
			}
			loggedInUserMenu();
		}
		else if (selection == 4){
			x=3;
			System.out.println("Your address is currently: " + user.getAddress());
			System.out.println("What would you like to change it to:");
			String newAddress = input.nextLine();
			
			System.out.println("Are you sure? 1| Yes  |2 No");
			selection = verify.verifyInput(selection);
			
			while (selection != 1 && selection != 2){
				System.out.println("Invalid Selection, please choose again!");
				selection = verify.verifyInput(selection);
			}
			
			if (selection == 2){
				loggedInUserMenu();
			}
			
			boolean success = userDAO.update(x, newAddress, user.getUsername());
			if (success){
				user.setAddress(newAddress);
				System.out.println("Password updated successfully!\n");
			}
			else{
				System.out.println("Password update failed\n");
			}
			loggedInUserMenu();
		}
		else if (selection == 5){
			x=4;
			System.out.println("Your email is currently: " + user.getEmail());
			System.out.println("What would you like to change it to:");
			String newEmail = input.nextLine();
			
			System.out.println("Are you sure? 1| Yes  |2 No");
			selection = verify.verifyInput(selection);
			
			while (selection != 1 && selection != 2){
				System.out.println("Invalid Selection, please choose again!");
				selection = verify.verifyInput(selection);
			}
			
			if (selection == 2){
				loggedInUserMenu();
			}
			
			boolean success = userDAO.update(x, newEmail, user.getUsername());
			if (success){
				user.setEmail(newEmail);
				System.out.println("Password updated successfully!\n");
			}
			else{
				System.out.println("Password update failed\n");
			}
			loggedInUserMenu();
		}
		else{
			loggedInUserMenu();
		}
	}
	
	public void userFavoritesHandler(){
		ArrayList<Favorites> faveArray = new ArrayList<Favorites>();
		ArrayList<MenuItems> menuArray = new ArrayList<MenuItems>();
		Favorites fave = new Favorites();
		int selection = 0;
		faveArray = favesDAO.displayFaves(user.getId());
		
		if(faveArray.size() == 0){
			System.out.println("\nYou have yet to favorite any Food Trucks!");
			System.out.println("Would you care to favorite any now? 1| Yes  2| No");
			selection = verify.verifyInput(selection);
			
			while(selection!=1 && selection!=2){
				System.out.println("Invalid selection, please try again!");
				selection = verify.verifyInput(selection);
			}
			
			if (selection == 2){
				loggedInUserMenu();
			}
			
			favoriteAFoodTruck();
		}
		
		for (int i = 0; i<faveArray.size(); i++){
			System.out.println((i+1) + "| " + faveArray.get(i));
		}
		
		System.out.println("Please select a favorite: ");
		selection = verify.verifyInput(selection);
		
		while (selection < 1 || selection > faveArray.size()){
			System.out.println("Invalid Selection, please choose again!");
			selection = verify.verifyInput(selection);
		}
		
		fave = faveArray.get(selection-1);
		System.out.println("\nYou have selected: " + fave);
		
		System.out.println("With your Favorite, you can:");
		System.out.println("1| View its Menu  2| Remove it from Favorites  3| See its Events  4| Choose another Favorite  5| Return to the main menu");
		selection = verify.verifyInput(selection);
		
		while (selection < 1 || selection > 5){
			System.out.println("Invalid Selection, please choose again!");
			selection = verify.verifyInput(selection);
		}
		
		if (selection == 1){
			int truckID = fave.getTruckID();
			menuArray = menuDAO.getWholeMenu(truckID);
			for (int i = 0; i < menuArray.size(); i++){
				System.out.println(menuArray.get(i));
			}
			loggedInUserMenu();
		}
		else if (selection == 2){
			System.out.println("You are sure? 1| Yes  2| Nevermind");
			selection = verify.verifyInput(selection);
			
			while (selection!=1 && selection!=2){
				System.out.println("Invalid Selection, please choose again!");
				selection = verify.verifyInput(selection);
			}
			
			if (selection == 2){
				userFavoritesHandler();
			}
			
			boolean exists = favesDAO.checkForFave(fave.getTruckID(), user.getId());
			if (!exists){
				System.out.println("That truck was not a favorite!");
				userFavoritesHandler();
			}
			
			boolean success = favesDAO.deleteFave(fave.getTruckID(), user.getId());
			if (success){
				System.out.println("Removal successful!");
				loggedInUserMenu();
			}
			else{
				System.out.println("Removal failed!");
				loggedInUserMenu();
			}
		}
		else if (selection == 3){
			EventsDAO eventsDAO = new EventsDAO();
			ArrayList<Events> eventArray = new ArrayList<Events>();
			eventArray = eventsDAO.getEventsFromTruck(fave.getTruckID());
			
			if (eventArray.size() == 0){
				System.out.println("This truck does not have any events yet. Please check back soon!");
				loggedInUserMenu();
			}
			
			System.out.println("Here are the events from " + fave.getName() + ":");
			for (int i = 0; i < eventArray.size(); i++){
				System.out.println(eventArray.get(i));
			}
			loggedInUserMenu();
		}
		else if (selection == 4){
			userFavoritesHandler();
		}
		else{
			loggedInUserMenu();
		}
	}
	
	public void userTruckHandler(){
		//TODO: display actual food trucks NOT favorites 
		int selection = 0;
		System.out.println("\nYou can: 1| Favorite a Food Truck  2| View a Food Truck's menu  3| See a Food Truck's events  4| Exit");
		selection = verify.verifyInput(selection);
		
		while (selection < 1 || selection > 4){
			System.out.println("Invalid Selection, please choose again!");
			selection = verify.verifyInput(selection);
		}
		
		ArrayList<FoodTruck> truckArray = new ArrayList<FoodTruck>();
		FoodTruck truck = new FoodTruck();
		
		if (selection == 1){
			favoriteAFoodTruck();
			//TODO: favorite a food truck
		}
		else if (selection == 2){
			ArrayList<MenuItems> menuArray = new ArrayList<MenuItems>();
			truckArray = foodDAO.select();
			System.out.println("\n");
			for (int i = 0; i < truckArray.size(); i++){
				System.out.println((i+1) + "| " + truckArray.get(i));
			}
			System.out.println("\nWhich Food Truck would you like to select: ");
			selection = input.nextInt();
			truck = truckArray.get(selection-1);
			System.out.println("You have chosen: " + truck);
			System.out.println("\nHere is the menu for " + truck.getName() + ":");
			menuArray = menuDAO.getWholeMenu(truck.getTruckID());
			for (int i = 0; i < menuArray.size(); i++){
				System.out.println(menuArray.get(i));
			}
			loggedInUserMenu();
		}
		else if (selection == 3){
			ArrayList<Events> eventArray = new ArrayList<Events>();
			EventsDAO eventsDAO = new EventsDAO();
			System.out.println("\n");
			truckArray = foodDAO.select();
			for (int i = 0; i < truckArray.size(); i++){
				System.out.println((i+1) + "| " + truckArray.get(i));
			}
			System.out.println("\nWhich food truck would you like to select: ");
			selection = verify.verifyInput(selection);
			
			while (selection < 1 || selection > truckArray.size()){
				System.out.println("Invalid Selection, please choose again!");
				selection = verify.verifyInput(selection);
			}
			
			truck = truckArray.get(selection-1);
			
			eventArray = eventsDAO.getEventsFromTruck(truck.getTruckID());
			if (eventArray.size()==0){
				System.out.println("This truck has not posted an events, yet!");
				loggedInUserMenu();
			}
			
			System.out.println("You have chosen: " + truck + ". Here are its events: ");
			for (int i = 0; i < eventArray.size(); i++){
				System.out.println(eventArray.get(i));
			}
			loggedInUserMenu();
		}
		else{
			exit();
		}
	}
		
	public void userEventsHandler(){
		System.out.println("Welcome to your Events Menu!");
		System.out.println("Here, you can: 1| View Events from your Favorited trucks  2| View all Events  3| Return to Main Menu");
		int selection = 0;
		selection = verify.verifyInput(selection);
		
		while (selection!=1 && selection!=2 && selection!=3){
			System.out.println("Invalid Selection, please choose again!");
			selection = verify.verifyInput(selection);
		}
		
		if (selection == 1){
			ArrayList<Events> eventArray = new ArrayList<Events>();
			ArrayList<Favorites> faveArray = new ArrayList<Favorites>();
			Favorites fave = new Favorites();
			EventsDAO eventsDAO = new EventsDAO();
			System.out.println("\n");
			faveArray = favesDAO.displayFaves(user.getId());
			for (int i = 0; i < faveArray.size(); i++){
				System.out.println((i+1) + "| " + faveArray.get(i));
			}
			System.out.println("\nWhich food truck would you like to select: ");
			selection = verify.verifyInput(selection);
			
			while (selection < 1 || selection > faveArray.size()){
				System.out.println("Invalid Selection, please choose again!");
				selection = verify.verifyInput(selection);
			}
			
			fave = faveArray.get(selection-1);
			
			eventArray = eventsDAO.getEventsFromTruck(fave.getTruckID());
			if (eventArray.size()==0){
				System.out.println("This truck has not posted an events, yet!");
				loggedInUserMenu();
			}
			
			System.out.println("You have chosen: " + fave + ". Here are its events: ");
			for (int i = 0; i < eventArray.size(); i++){
				System.out.println(eventArray.get(i));
			}
			loggedInUserMenu();
		}
		else if (selection == 2){
			ArrayList<Events> eventArray = new ArrayList<Events>();
			EventsDAO eventsDAO = new EventsDAO();
			eventArray = eventsDAO.getAllEvents();
			
			if (eventArray.size()==0){
				System.out.println("Sorry, there are no events listed at this time. Check back in soon!");
				loggedInUserMenu();
			}
			
			System.out.println("Here are all of the events scheduled: ");
			for (int i = 0; i < eventArray.size(); i++){
				System.out.println(eventArray.get(i));
			}
			
			loggedInUserMenu();
		}
		else{
			loggedInUserMenu();
		}
	}
	
	public void favoriteAFoodTruck(){
		ArrayList<FoodTruck> truckArray = new ArrayList<FoodTruck>();
		FoodTruck truck = new FoodTruck();
		truckArray = foodDAO.select();
		System.out.println("\n");
		for (int i = 0; i < truckArray.size(); i++){
			System.out.println((i+1) + "| " + truckArray.get(i));
		}
		System.out.println("\nWhich Food Truck would you like to favorite: ");
		int selection = 0;
		selection = verify.verifyInput(selection);
		
		while (selection < 1 || selection > truckArray.size()){
			System.out.println("Invalid selection, please try again!");
			selection = verify.verifyInput(selection);
		}
		
		truck = truckArray.get(selection-1);
		
		boolean exists = favesDAO.checkForFave(truck.getTruckID(), user.getId());
		if (exists){
			System.out.println("You have already favorited this truck!");
			loggedInUserMenu();
		}
		
		System.out.println("You have chosen: " + truck);
		System.out.println("Are you sure you would like to favorite this truck?");
		System.out.println("1| Yes  2| No");
		selection = verify.verifyInput(selection);
		
		while (selection!=1 && selection!=2){
			System.out.println("Invalid Selection, please choose again!");
			selection = verify.verifyInput(selection);
		}
		
		if (selection == 2){
			loggedInUserMenu();
		}
		
		boolean success = favesDAO.addFave(truck, user.getId());
		if (success){
			System.out.println("FoodTruck favorited!");
		}
		else{
			System.out.println("Favorite not added!");
		}
		
		loggedInUserMenu();
	}
	
	public boolean changePW(){
		int x = 5;
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
		
		boolean success = userDAO.update(x, hashedPW, user.getUsername());
		if (success){
			user.setPassword(hashedPW);
			return true;
		}
		else{
			return false;
		}
	}
	
	public void exit(){
		System.out.println("Thank you for using FoodTruckTracker!");
		System.out.println("Exiting...");
		System.exit(0);
	}
}
