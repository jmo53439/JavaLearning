package com.jmlearning.randomthings;

public class PascalTriangle {

	public static void main(String[] args) {
		
		int[][] triangle = new int[7][];
		fill(triangle);
		print(triangle);

	}
	
	public static void fill(int[][] triangle) {
		
		for(int i = 0; i < triangle.length; i++) {
			
			triangle[i] = new int[i + 1];
			triangle[i][0] = 1;
			triangle[i][i] = 1;
			
			for(int j = 1; j < i; j++) {
				
				triangle[i][j] = triangle[i - 1][j - 1] + triangle[i - 1][j];
			}
		}
	}
	
	public static void print(int[][] triangle) {
		
		for(int i = 0; i < triangle.length; i++) {
			
			for(int j = 0; j < triangle[i].length; j++) {
				
				System.out.print(triangle[i][j] + " ");
			}
			
			System.out.println();
		}
	}
}