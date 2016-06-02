package DBTesting;

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
		
		while(x>1 && x<4){
			testMethod();
		}
	}
	
	public static void main (String[] args){
		Test test = new Test();
		test.testMethod();
	}
	
}
