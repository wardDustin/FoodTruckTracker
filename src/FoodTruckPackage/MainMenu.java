package FoodTruckPackage;

public class MainMenu {
	private FoodTruckService truckService;
	private UserService user;
	private Verify verify;
	
	public MainMenu() throws Exception{
		truckService = new FoodTruckService();
		user = new UserService();
		verify = new Verify();
	}
	
	public static void main(String[] args) throws Exception{
		int x = 0;
		MainMenu menu = new MainMenu();
		do{
			x = menu.getUserType();
			if (x==1){
				menu.processUserActions();
			}
			else if (x==2){
				menu.processFoodTruckActions();
			}
			else{
				System.out.println("Thank u for using FoodTruckTracker!");
			}
		}while (x!=3);
	}
	
	public int getUserType(){
		int selection = 0;
		System.out.println("Welcome to FoodTruckTracker!!");
		System.out.println("Are you a User or an owner of a Food Truck?");
		System.out.println("1| User\n2| Food Truck Owner\n3| Exit Program");
		selection = verify.verifyInput(selection);
		
		while (selection != 1 && selection != 2 && selection != 3){
			System.out.println("Invalid Selection, please choose again!");
			selection = verify.verifyInput(selection);
		}
		
		return selection;
	}
	
	public void processUserActions() throws Exception{
		user.logUserIn();
	}
	
	public void processFoodTruckActions() throws Exception{
		truckService.logOwnerIn();
	}
}
