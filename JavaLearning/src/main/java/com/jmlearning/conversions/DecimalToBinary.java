package com.jmlearning.conversions;

import java.util.Scanner;

public class DecimalToBinary {
	
	public String toBinary(int n) {
		
		if(n == 0) {
			
			return "0";
		}
		
		String binary = "";
		
		while(n > 0) {
			
			int rem = n % 2;
			binary = rem + binary;
			n = n / 2;
		}
		return binary;
	}

	public static void main(String[] args) {

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter a Number: ");
		int decimal = scanner.nextInt();
		
		DecimalToBinary dtb = new DecimalToBinary();
		String binary = dtb.toBinary(decimal);
		System.out.println("This is the Number in Binary: " + binary);
	}

}
