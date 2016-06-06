package FoodTruckPackage;

import java.util.Scanner;

public class UserService {
	private Scanner input;
	private User user;
	private UserDAO userDAO;
	private Verify verify;
	
	public UserService(){
		input = new Scanner(System.in);
		user = new User();
		userDAO = new UserDAO();
		verify = new Verify();
	}
	
	public void logUserIn(){
		System.out.println("Excellent! Welcome!!");
		System.out.println("Let's check and see if you've used FoodTruckTracker before");
		System.out.println("First, please enter a username between 3 and 15 characters: ");
		String username = input.nextLine();
		
		while (username.length() > 15 || username.length() < 3){
			System.out.println("Sorry. Your username is incorrect. It must be between 3 and 15 characters!");
			username = input.nextLine();
		}
		
		boolean found = userDAO.selectUsername(username);
		
		if (!found){
			System.out.println("Thank you for registering as a new User!");
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
		System.out.println("1| Manage your profile  2| View Food Trucks  3| View Events  4| Exit");
		int selection = 0;
		selection = verify.verifyInput(selection);
		
		while (selection != 1 && selection != 2 && selection != 3 && selection!=4){
			System.out.println("Invalid Selection, please choose again!");
			selection = verify.verifyInput(selection);
		}
		
		if (selection == 1){
			System.out.println(user);
			System.out.println("What would you like to change?");
			System.out.println("1| Username  2| Password  3| Name  4| Address  5| Email  6| Nevermind");
			selection = verify.verifyInput(selection);
			
			while (selection < 1 && selection > 6){
				System.out.println("Invalid Selection, please choose again!");
				selection = verify.verifyInput(selection);
			}
			
			userProfileHandler(selection);
			
		}
		else if (selection == 2){
			
		}
		else if (selection == 3){
			
		}
		else{
			exit();
		}
		
	}
	
	public void userProfileHandler(int selection){
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
					userProfileHandler(selection);
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
						boolean success = changePW();
						if (success){
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
				boolean success = changePW();
				if (success){
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
			String name = input.nextLine();
			
			System.out.println("Are you sure? 1| Yes  |2 No");
			selection = verify.verifyInput(selection);
			
			while (selection != 1 && selection != 2){
				System.out.println("Invalid Selection, please choose again!");
				selection = verify.verifyInput(selection);
			}
			
			if (selection == 2){
				loggedInUserMenu();
			}
			
			boolean success = userDAO.update(x, name, user.getUsername());
			if (success){
				System.out.println("Password updated successfully!\n");
			}
			else{
				System.out.println("Password update failed\n");
			}
			loggedInUserMenu();
		}
		else if (selection == 4){
			x=3;
			//TODO: update address
		}
		else if (selection == 5){
			x=4;
			//TODO: update email
		}
		else{
			loggedInUserMenu();
		}
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
