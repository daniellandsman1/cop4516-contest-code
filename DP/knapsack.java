// Arup Guha
// 3/21/2017
// Shows DP approach to knapsack problem.

import java.util.*;

public class knapsack {

	public static void main(String[] args) {

		// Read in a single case from input.
		Scanner stdin = new Scanner(System.in);
		int n = stdin.nextInt();
		int[] weights = new int[n];
		int[] values = new int[n];
		for (int i=0; i<n; i++) {
			weights[i] = stdin.nextInt();
			values[i] = stdin.nextInt();
		}
		int capacity = stdin.nextInt();

		// dp[i] will store max value of knapsack with capacity i.
		int[] dp = new int[capacity+1];

		// go through each item.
		for (int i=0; i<n; i++) {

			// If we do the loop backwards, we only get 1 copy of each item.
			// Forwards, we can as many copies as we want.
			//for (int w=capacity; w>=weights[i]; w--)
			for (int w=weights[i]; w<=capacity; w++)
				dp[w] = Math.max(dp[w], dp[w-weights[i]] + values[i] );

			// Just to understand the algorithm, we print at the end of each iteration.
			for (int j=0; j<=capacity; j++)
				System.out.printf("%5d", dp[j]);
			System.out.println();
		}
	}
}