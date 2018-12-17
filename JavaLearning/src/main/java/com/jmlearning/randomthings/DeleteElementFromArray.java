package com.jmlearning.randomthings;

public class DeleteElementFromArray {

	public static void main(String[] args) {
		
		int arrUserId[] = { 
				
				1234,
				5678,
				9011,
				1213,
				1415,
				1617
		};
		
		int pos = 3;
		
		
		for(int k = 0; k < arrUserId.length; k++) {
			
			System.out.println(arrUserId[k]);
		}
		
		for(int i = 0; i < arrUserId.length; i++) {
			
			if(pos == i) {
				
				for(int j = i + 1; i < arrUserId.length - 1; j++) {
					
					arrUserId[i] = arrUserId[j];
					i++;
				}
			}
		}
		
		System.out.println("The Output of the Array after Deleting: ");
		
		for(int i = 0; i < arrUserId.length - 1; i++) {
			
			System.out.println(arrUserId[i]);
		}

	}

}
