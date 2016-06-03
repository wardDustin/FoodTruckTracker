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
