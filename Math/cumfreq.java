// Arup Guha
// 9/11/2014
// Code to illustrate a cumulative frequency array.

import java.util.*;

public class cumfreq {
	
	final public static int SIZE = 10;
	final public static int MAX = 20;
	
	public static void main(String[] args) {
		
		Random r = new Random();
		int[] orig = mkArray(r, SIZE, MAX+1);
		printArray(orig);
		
		// Forming cumulative frequency array.
		int[] cumfreq = new int[SIZE];
		cumfreq[0] = orig[0];
		for (int i=1; i<SIZE; i++)
			cumfreq[i] = cumfreq[i-1] + orig[i];
			
		// An example - sum index 2 to 7, inclusive.
		System.out.println("Sum from index 2 to index 7: "+(cumfreq[7]-cumfreq[1]));
		
		int sum = 0;
		for (int i=2; i<=7; i++)
			sum += orig[i];
		System.out.println("Long sum: "+sum);
		
		// Sorted List Matching.
		int[] arr1 = mkArray(r, SIZE, MAX+1);
		int[] arr2 = mkArray(r, SIZE, MAX+1);
		Arrays.sort(arr1);
		Arrays.sort(arr2);
		System.out.println("Arrays for sorted list matching.");
		printArray(arr1);
		printArray(arr2);
		
		// Sorted List Matching - prints out common names.
		System.out.print("Numbers on both lists: ");
		int i = 0, j = 0;
		while (i < arr1.length && j < arr2.length) {
			
			// Advance appropriate pointer with non-match.
			if (arr1[i] < arr2[j]) i++;
			else if (arr2[j] < arr1[i]) j++;
			
			// Process a match.
			else {
				System.out.print(arr1[i]+" ");
				i++;
				j++;
			}
		}
		System.out.println();
		
	}
	
	// Fills an array with size random values in between 0 and maxval-1,
	// inclusive. 
	public static int[] mkArray(Random r, int size, int maxval) {
		int[] arr = new int[size];
		for (int i=0; i<size; i++)
			arr[i] = r.nextInt(maxval);
		return arr;
	}
	
	// Prints Array.
	public static void printArray(int[] arr) {
		for (int i=0; i<arr.length; i++)
			System.out.print(arr[i]+" ");
		System.out.println();
	}
}