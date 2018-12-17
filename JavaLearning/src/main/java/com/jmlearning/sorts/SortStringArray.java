package com.jmlearning.sorts;

import java.util.Arrays;

public class SortStringArray {

	public static void main(String[] args) {

		String[] strGames = new String[] {
				
				"final Fantasy 7",
				"Chrono Trigger",
				"world of Warcraft",
				"Call of Duty",
				"Red Dead Redemption",
				"Gears of War",
				"Destiny",
				"halo"
		};
		
		// Um... the sort method?
		Arrays.sort(strGames);
		
		System.out.println("Sort the String Array in a Case Sensitive Manner: ");
		
		for(int i = 0; i < strGames.length; i++) {
			
			System.out.println(strGames[i]);
		}
	}

}
