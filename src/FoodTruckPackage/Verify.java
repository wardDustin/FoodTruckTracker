package FoodTruckPackage;

import java.sql.ResultSet;
import java.util.Scanner;

import org.mindrot.jbcrypt.BCrypt;

public class Verify {
	private Scanner input = new Scanner(System.in);
	private ResultSet resultSet;
	private UserDAO userDAO = new UserDAO();
	
	
	public Verify() {}
	
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
		
		boolean itMatches = passwordMatch(pw, hashedpw);
		if (itMatches){
			return true;
		}
		else{
			return false;
		}
	}
	
	/*
	 * BCrypt is a great encryption tool that uses a complex hash function
	 * but also salts the password... 
	 */
	public String encryptPW(String passwd){
    	String hashed = BCrypt.hashpw(passwd, BCrypt.gensalt());
		System.out.println(hashed);
		
		return hashed;
    }
	
	/*
	 * This if/else statement simply confirms  or denies that the given password
	 * matches a hashed password... if it returns true, the passwords are the same
	 * if it returns false, the passwords are not the same
	 */
    public boolean passwordMatch(String passwd, String hashed){
    	if (BCrypt.checkpw(passwd, hashed))
			return true;
		else
			return false;
    }
}
