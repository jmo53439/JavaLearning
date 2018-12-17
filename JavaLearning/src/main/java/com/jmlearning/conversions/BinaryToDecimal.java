package com.jmlearning.conversions;

import java.util.Scanner;

public class BinaryToDecimal {
	
	Scanner scan;
	int num;
	
	void getVal() {
		
		System.out.println("Binary to Decimal");
		scan = new Scanner(System.in);
		
		System.out.println("\nEnter the number :");
		num = Integer.parseInt(scan.nextLine(), 2);
	}
	
	void convert() {
		
		String decimal = Integer.toString(num);
		System.out.println("Decimal Value is :" + decimal);
	}
}

	class MainClass {

	public static void main(String[] args) {
		
		BinaryToDecimal obj = new BinaryToDecimal();
		
		obj.getVal();
		obj.convert();
		
	}
}