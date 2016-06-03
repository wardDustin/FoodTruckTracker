package FoodTruckPackage;

import java.sql.ResultSet;
import java.util.Scanner;

public class UserService {
	
	private ResultSet rs;
	private Scanner input = new Scanner(System.in);
	private MainMenu main = new MainMenu();
	private User user = new User();
	private UserDAO userDAO = new UserDAO();
	private Verify verify = new Verify();
	
	public UserService(){}
	
	public void logUserIn() throws Exception{
		String db = null;
		System.out.println("Excellent! Welcome!!");
		System.out.println("Let's check and see if you've used FoodTruckTracker before");
		System.out.println("First, please enter a username no longer than 15 characters: ");
		String username = input.nextLine();
		
		while (username.length() > 15){
			System.out.println("Sorry. Your username is too long. Please keep the length to 15 characters or less!");
			username = input.nextLine();
		}
		
		String usernameQuery = "SELECT username FROM FoodTruckTracker.User WHERE username = ?";
		rs = userDAO.select(usernameQuery, username);
		
		if (!rs.next()){
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
				main.exit();
			}
			else{
				System.out.println("Something is wrong here");
				//TODO: fix
				main.exit();
			}
		} 
		else{
			System.out.println("Thank you " + username + "!");
			System.out.println("Password: ");
			db = "FoodTruckTracker.User";
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
						main.exit();
					}
					x++;
				}
				System.out.println("Unsuccessful login");
				//TODO: either exit or re-call beginning of program
				main.exit();
			}
			else{
				System.out.println("You are logged in!");
				//TODO: call next step of process
				main.exit();
			}
		}
	}
}
