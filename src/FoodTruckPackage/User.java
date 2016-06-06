package FoodTruckPackage;

public class User {
	private int Id;
	private String name;
	private String username;
	private String password;
	private String address;
	private String email;
	
	public User() {}
	public User (String name, String username, String address, String email){
		this.name = name;
		this.username = username;
		this.address = address;
		this.email = email;
	}
	
	public int getId(){
		return this.Id;
	}
	
	public void setId(int newID){
		this.Id = newID;
	}

	public String getEmail() {
		return this.email;
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
	
	public String getPassword(){
		return this.password;
	}
	
	public void setPassword(String newPassword){
		this.password = newPassword;
	}
	
	@Override
	public String toString() {
		return "Name = " + name + ", Username = " + username + ", Address = " + address
				+ ", Email = " + email;
	}
	
}
