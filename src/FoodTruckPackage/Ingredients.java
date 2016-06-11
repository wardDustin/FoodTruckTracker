package FoodTruckPackage;

import java.util.Comparator;

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
	
	public static Comparator<Ingredients> ingredientsNameComp = new Comparator<Ingredients>() {

		public int compare(Ingredients ing1, Ingredients ing2) {
		   String ingredient1 = ing1.getName().toUpperCase();
		   String ingredient2 = ing2.getName().toUpperCase();

		   //ascending order
		   return ingredient1.compareTo(ingredient2);

		   //descending order
		   //return ingredient2.compareTo(ingredient1);
	    }
	};
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Ingredients))
			return false;
		Ingredients other = (Ingredients) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public String toString() {
		return name;
	}
}
