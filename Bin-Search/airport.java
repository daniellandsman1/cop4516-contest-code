// Arup Guha
// 7/15/2018
// Solution to 2018 SI@UCF Contest #4 Problem: Airport Shuttle

import java.util.*;

public class airport {
	
	public static int n;
	public static int k;
	public static int[] times;
	
	public static void main(String[] args) {
		
		// Read in the input.
		Scanner stdin = new Scanner(System.in);
		n = stdin.nextInt();
		k = stdin.nextInt();
		times = new int[n];
		for (int i=0; i<n; i++)
			times[i] = stdin.nextInt();
		
		// Sort it.
		Arrays.sort(times);
		
		// Run our binary search.
		int low = 0, high = 1000000000;
		while (low < high) {
			
			// Try half way in between.
			int mid = (low+high)/2;
			
			// Adjust our bounds.
			if (canDo(mid))
				high = mid;
			else
				low = mid+1;
		}

		// Ta da!!!
		System.out.println(low);
	}
	
	// Returns true iff k shuttle runs with maxtime max will get everyone.
	public static boolean canDo(int max) {
		
		// i is index of the camper, j is index of the driver.
		int i = 0, j = 0;
		
		while (i<n && j<k) {
			
			// Put as many people as possible on this shuttle.
			int curI = i;
			while (curI<n && times[curI]-times[i]<=max) curI++;
			
			// Update who we need to pick up next and our next shuttle driver.
			i = curI;
			j++;
		}
		
		// If we get here, we made it if we got all the campers.
		return i == n;
	}

}
