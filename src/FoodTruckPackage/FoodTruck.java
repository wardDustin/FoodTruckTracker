package FoodTruckPackage;

import java.util.ArrayList;

public class FoodTruck {
	private int truckID;
	private String name;
	private String password;
	private String owner;
	private String location;
	private String foodType;
	private ArrayList<MenuItems> menuItems; //add, remove, list make all <----------
	
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
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	//Behaviors??
	public ArrayList<MenuItems> getMenuItems(){
		return this.menuItems;
	}
	
	public void setMenuItems(ArrayList<MenuItems> newMenuItems){
		this.menuItems = newMenuItems;
	}
	
//	public void removeFromMenu(int index){
//		menuArray.remove(index);
//	}
	
	//TODO: toString? removeMenuItems from ArrayList?
	
	
	
	public static void main(String[] args) throws Exception{
		
//		FoodTruck r = new FoodTruck("Rachels rad (w)raps", "Rach", "wraps");
		
	}
}
