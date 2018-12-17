package com.jmlearning.sorts;

import java.util.Scanner;

public class QuickSort {

	public static void sort(int[] arr) {

		quickSort(arr, 0, arr.length - 1);
	}

	public static void quickSort(int arr[], int low, int high) {

		int i = low, j = high;
		int temp;
		int pivot = arr[(low + high) / 2];

		// Partition
		while (i <= j) {

			while (arr[i] < pivot) {

				i++;
			}

			while (arr[j] > pivot) {

				j--;
			}

			if (i <= j) {

				// Swap
				temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;

				i++;
				j--;
			}
		}

		// Sort Lower, Recursive
		if (low < j) {

			quickSort(arr, low, j);
		}

		// Sort Upper, Recursive
		if (i < high) {

			quickSort(arr, i, high);
		}
	}

	public static void main(String[] args) {

		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.println("Quick Sort Test\n");

		int n, i;

		// Enter the Number of Elements
		System.out.println("Enter Number of Integer Elements");
		n = scan.nextInt();

		// Create Array of 'n' Elements
		int arr[] = new int[n];

		// Accept Elements
		System.out.println("\nEnter " + n + " Integers you wanna use");

		for (i = 0; i < n; i++) {

			arr[i] = scan.nextInt();

		}

		// Call Method 'sort'
		sort(arr);

		// Print Sorted Array
		System.out.println("\nElements After Sorting ");

		for (i = 0; i < n; i++) {

			System.out.print(arr[i] + " ");

			System.out.println();
		}
	}

}
