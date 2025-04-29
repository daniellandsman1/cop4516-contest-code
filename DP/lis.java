// Arup Guha
// 3/21/2017
// Shows DP approach to LIS problem.

import java.util.*;

public class lis {

	public static void main(String[] args) {

		// Read in one case from input.
		Scanner stdin = new Scanner(System.in);
		int n = stdin.nextInt();
		int[] vals = new int[n];
		for (int i=0; i<n; i++)
			vals[i] = stdin.nextInt();

		int[] dp = new int[n];
		dp[0] = 1;

		// Find the best sequence that ends at index i.
		System.out.print(dp[0]+" ");
		for (int i=1; i<n; i++) {

			// Initially we could just take this item.
			dp[i] = 1;

			// We try to build off j as the previous item in the increasing sequence.
			for (int j=0; j<i; j++)
				if (vals[j] < vals[i])
					dp[i] = Math.max(dp[i], dp[j]+1);

			// Just for learning...
			System.out.print(dp[i]+" ");
		}
	}
}