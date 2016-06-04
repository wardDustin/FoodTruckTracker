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
	
	public void logUserIn() throws Exception{
		System.out.println("Excellent! Welcome!!");
		System.out.println("Let's check and see if you've used FoodTruckTracker before");
		System.out.println("First, please enter a username no longer than 15 characters: ");
		String username = input.nextLine();
		
		while (username.length() > 15){
			System.out.println("Sorry. Your username is too long. Please keep the length to 15 characters or less!");
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
				System.out.println(user);
				exit();
			}
			else{
				System.out.println("Something is wrong here");
				//TODO: fix
				exit();
			}
		} 
		else{
			System.out.println("Thank you " + username + "!");
			System.out.println("Create a password: ");
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
						//TODO: call next step of process
						exit();
					}
					x++;
				}
				System.out.println("Unsuccessful login");
				//TODO: either exit or re-call beginning of program
				exit();
			}
			else{
				System.out.println("You are logged in!");
				//TODO: call next step of process
				exit();
			}
		}
	}
	
	public void exit(){
		System.out.println("Thank you for using FoodTruckTracker!");
		System.out.println("Exiting...");
		System.exit(0);
	}
}