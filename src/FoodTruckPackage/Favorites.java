package FoodTruckPackage;

import java.util.Comparator;

public class Favorites {
	private int truckID;
	private String name;
	private String password;
	private String owner;
	private String foodType;
	private int userID;
	
	public Favorites() {}
	public Favorites (String name, String owner, String foodType){
		this.name = name;
		this.owner = owner;
		this.foodType = foodType;
	}
	
	public int getTruckID(){
		return this.truckID;
	}
	
	public void setTruckID(int newTruckID){
		this.truckID = newTruckID;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return this.owner;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public void setPassword(String newPassword){
		this.password = newPassword;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getFoodType() {
		return this.foodType;
	}

	public void setFoodType(String foodType) {
		this.foodType = foodType;
	}
	
	public int getUserID(){
		return this.userID;
	}
	
	public void setUserID(int newUserID){
		this.userID = newUserID;
	}
	
	public static Comparator<Favorites> favesTruckNameComp = new Comparator<Favorites>() {

		public int compare(Favorites fav1, Favorites fav2) {
		   String fave1 = fav1.getName().toUpperCase();
		   String fave2 = fav2.getName().toUpperCase();

		   //ascending order
		   return fave1.compareTo(fave2);

		   //descending order
		   //return fave2.compareTo(fave1);
	    }
	};
	
	@Override
	public String toString() {
		return "Name: " + name + ", Owner: " + owner + ", Food Type: " + foodType;
	}

}
