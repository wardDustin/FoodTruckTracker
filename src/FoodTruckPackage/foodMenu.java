package FoodTruckPackage;

public class foodMenu {


	private String title;
	private String[] ingredients;
	private int totalCalories;
	private String specialComments;
	
	public foodMenu (String title, String[] ingredients, int totalCalories, String specialComments){
		this.title = title;
		this.ingredients = ingredients;
		this.totalCalories = totalCalories;
		this.specialComments = specialComments;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getIngredients() {
		return ingredients;
	}

	public void setIngredients(String[] ingredients) {
		this.ingredients = ingredients;
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

}
