package DBTesting;

import java.time.*;
import java.util.Scanner;

public class Test {

	Scanner input;
	
	public Test(){
		input = new Scanner(System.in);
	}
	
	public void testMethod(){
		String y = null;
		int x = 0;
		do{
			System.out.println("Ha!");
			y = input.nextLine();
			x = Integer.parseInt(y);
		}
		while(x==1 || x ==2  || x == 3 || x==4);
		
		while(x>1 || x<4){
			testMethod();
		}
	}
	
	public static void main (String[] args){
		LocalDate today = LocalDate.now();
		System.out.println(today.getDayOfMonth());
		System.out.println("The date is: " + today);
		
		YearMonth currentYearMonth = YearMonth.now();
		System.out.printf("Days in month year %s: %d%n", currentYearMonth, currentYearMonth.lengthOfMonth()); 
		YearMonth creditCardExpiry = YearMonth.of(2018, Month.FEBRUARY); 
		System.out.printf("Your credit card expires on %s %n", creditCardExpiry); 
		
	}
	
}
