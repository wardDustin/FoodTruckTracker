package FoodTruckPackage;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuItems{


	private String title;
	private ArrayList<String> ingredients;
	private int totalCalories;
	private String specialComments;
	private Scanner input;
	
	public MenuItems() {}
	public MenuItems (String title, ArrayList<String> ingredients, int totalCalories, String specialComments){
		this.title = title;
		this.ingredients = ingredients;
		this.totalCalories = totalCalories;
		this.specialComments = specialComments;
		input = new Scanner(System.in);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<String> getIngredients() {
		return ingredients;
	}

	public void setIngredients(ArrayList<String> ingredients) {
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
	
//	public MenuItems makeItem(){
//		System.out.println("Let's build your menu!");
//		System.out.println("What is the name of the food item you would like to add: ");
//		this.setTitle(input.nextLine());
//
//		
//		System.out.println("Sounds delicious. Let's add the ingredients!");
//		System.out.println("When you are done entering all the ingredients, hit 0 to quit");
//		int x = 0;
//		do{
//			System.out.println("(0 to exit) Ingredient " + (x+1) +": ");
//			String y = input.nextLine();
//			this.setIngredients(y);
//			try{
//				if (Integer.parseInt(y)==0){
//					break;
//				}
//			}
//			catch(NumberFormatException e){
//				
//			}
//			x++;
//		}while(x!=0);
//		
//		System.out.println(this.getIngredients());
//		
//		System.out.println("How many calories does " + this.title + " contain?");
//		int selection = 0;
//		selection = verifyInput(selection);
//		while (selection > 0){
//			this.setTotalCalories(selection);
//			System.out.println("I need a positive integer please: ");
//		}
//		
//		System.out.println("And finally, any special comments about your food?");
//		this.setSpecialComments(input.nextLine());
//		
//		MenuItems a = new MenuItems(this.getTitle(), this.getIngredients(), this.getTotalCalories(), this.getSpecialComments());
//		return a;
//	}
	
	public static void main(String[] args){
//		FoodTruck d = new FoodTruck("d", "dw", "bbq");
		
		
		
	}
	
	public int verifyInput(int newInt){
		while (!input.hasNextInt()){
			input.next();
			System.out.println("I need an integer please: ");
		}
		newInt = input.nextInt();
		input.nextLine();
		return newInt;
	}

	@Override
	public String toString() {
		return "Title: " + title + ", Ingredients: " + ingredients + ", Total Calories: " + totalCalories
				+ ", Special Comments: " + specialComments + "]";
	}

}
