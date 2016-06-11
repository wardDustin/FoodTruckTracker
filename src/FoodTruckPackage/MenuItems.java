package FoodTruckPackage;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;

public class MenuItems{


	private String title;
	private ArrayList<Ingredients> ingredients;
	private float price;
	private int totalCalories;
	private String specialComments;
	private int truckID;
	private int menuID;
	
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
	
	public int getMenuID(){
		return this.menuID;
	}
	
	public void setMenuID(int newMenuID){
		this.menuID = newMenuID;
	}
	
	public static Comparator<MenuItems> menuItemsNameComp = new Comparator<MenuItems>() {

		public int compare(MenuItems mi1, MenuItems mi2) {
		   String menuItem1 = mi1.getTitle().toUpperCase();
		   String menuItem2 = mi2.getTitle().toUpperCase();

		   //ascending order
		   return menuItem1.compareTo(menuItem2);

		   //descending order
		   //return menuItem2.compareTo(menuItem1);
	    }
	};
	
	public String toString() {//fix price... prints off as 0.0
		DecimalFormat format = new DecimalFormat("0.00");
		String formattedPrice = format.format(price);
		return "Title: " + title + ", Price: $" + formattedPrice + ", Ingredients: " + ingredients + ", Total Calories: " + totalCalories
				+ ", Special Comments: " + specialComments;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(price);
		result = prime * result + ((specialComments == null) ? 0 : specialComments.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + totalCalories;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MenuItems))
			return false;
		MenuItems other = (MenuItems) obj;
		if (Float.floatToIntBits(price) != Float.floatToIntBits(other.price))
			return false;
		if (specialComments == null) {
			if (other.specialComments != null)
				return false;
		} else if (!specialComments.equals(other.specialComments))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (totalCalories != other.totalCalories)
			return false;
		return true;
	}

}
