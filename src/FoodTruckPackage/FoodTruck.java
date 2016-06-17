package FoodTruckPackage;

import java.util.ArrayList;
import java.util.Comparator;

public class FoodTruck {
	private int truckID;
	private String name;
	private String password;
	private String owner;
	private String foodType;
	private ArrayList<MenuItems> menuItems;
	
	public FoodTruck() {}
	public FoodTruck (String name, String owner, String foodType){
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
	
	public ArrayList<MenuItems> getMenuItems(){
		return this.menuItems;
	}
	
	public void setMenuItems(ArrayList<MenuItems> newMenuItems){
		this.menuItems = newMenuItems;
	}
	
	public static Comparator<FoodTruck> foodTruckNameComp = new Comparator<FoodTruck>() {

		public int compare(FoodTruck ft1, FoodTruck ft2) {
		   String truckName1 = ft1.getName().toUpperCase();
		   String truckName2 = ft2.getName().toUpperCase();

		   //ascending order
		   return truckName1.compareTo(truckName2);

		   //descending order
		   //return truckName2.compareTo(truckName1);
	    }
	};
	
	@Override
	public String toString() {
		return "Truck: " + name + ", Owner: " + owner + ", Food Type: " + foodType;
	}
	
}