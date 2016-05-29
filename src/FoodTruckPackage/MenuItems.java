package FoodTruckPackage;

import java.util.ArrayList;

public class MenuItems{


	private String title;
	private ArrayList<Ingredients> ingredients;
	private float price;
	private int totalCalories;
	private String specialComments;
	private int truckID;
	
	public MenuItems() {}
	public MenuItems (String title, float price, int totalCalories, String specialComments){
		this.title = title;
		this.price = price;
		this.totalCalories = totalCalories;
		this.specialComments = specialComments;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<Ingredients> getIngredients() {
		return ingredients;
	}

	public void setIngredients(ArrayList<Ingredients> ingredients) {
		this.ingredients = ingredients;
	}
	
	//TODO: remove Ingredients from ArrayList?
	
	public float getPrice(){
		return this.price;
	}
	
	public void setPrice(float newPrice){
		this.price =  newPrice;
	}

	public int getTotalCalories() {
		return totalCalories;
	}

	public void setTotalCalories(int totalCalories) {
		this.totalCalories = totalCalories;
	}

	public String getSpecialComments() {
		return specialComments;
	}

	public void setSpecialComments(String specialComments) {
		this.specialComments = specialComments;
	}
	
	public int getTruckID(){
		return this.truckID;
	}
	
	public void setTruckID(int newTruckID){
		this.truckID = newTruckID;
	}
	
	public String toString() {
		return "Title: " + title + ", Ingredients: " + ingredients + ", Total Calories: " + totalCalories
				+ ", Special Comments: " + specialComments + "]";
	}

}
