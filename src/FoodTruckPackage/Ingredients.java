package FoodTruckPackage;

public class Ingredients {
	private String name;
	private int menuID;
	
	public Ingredients(){}
	public Ingredients(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String newName){
		this.name = newName;
	}
	
	public int getMenuID(){
		return this.menuID;
	}
	
	public void setMenuID(int newMenuID){
		this.menuID = newMenuID;
	}
	
	public String toString() {
		return name;
	}
}
