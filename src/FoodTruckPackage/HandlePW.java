package FoodTruckPackage;

import org.mindrot.jbcrypt.BCrypt;

public class HandlePW {

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
