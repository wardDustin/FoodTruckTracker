package FoodTruckPackage;

public class User {
	private String name;
	private String username;
	private String address;
	private String email;
	
	public User() {}
	public User (String name, String username, String address, String email){
		this.name = name;
		this.username = username;
		this.address = address;
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
