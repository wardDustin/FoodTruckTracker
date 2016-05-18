package FoodTruckPackage;

public class User {
	private String name;
	private String username;
	private String address;
	
	public User (String name, String username, String address){
		this.name = name;
		this.username = username;
		this.address = address;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	

}
