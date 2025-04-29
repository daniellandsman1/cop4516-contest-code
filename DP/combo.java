// Arup Guha
// 3/21/2017
// Shows recursion, memoization and DP approaches to solving combinations.

import java.util.*;

public class combo {

	// Often used in many contest problems to limit size for large binomial coefficients.
	final public static long MOD = 1000000007L;

	// Needed for memoization technique.
	public static long[][] memo;

	public static void main(String[] args) {

		// Just set up for a simple test.
		int n = 1000;
		int k = 373;

		// Set up the memo table.
		memo = new long[n+1][k+1];
		for (int i=0; i<memo.length; i++)
			Arrays.fill(memo[i], -1);

		// Run our recursion with memoization.
		System.out.println(choose(n, k));

		// Now, just form pascal's triangle, storing smaller cases first.
		long[][] table = new long[n+1][n+1];
		for (int i=0; i<=n; i++) {
			table[i][0] = 1;
			table[i][i] = 1;
		}

		// No recursion is necessary since each item we need is already calculated when
		// we plug in the recursive definition.
		for (int i=2; i<=n; i++)
			for (int j=1; j<i; j++)
				table[i][j] = (table[i-1][j-1] + table[i-1][j])%MOD;
		System.out.println(table[n][k]);
	}

	public static long choose(int n, int k) {

		// Regular base case.
		if (k == 0 || k == n) return 1;

		// Needed for memoization to avoid redundant recursive calls.
		if (memo[n][k] != -1) return memo[n][k];

		// Store before you return!
		return memo[n][k] = (choose(n-1,k-1) + choose(n-1,k))%MOD;
	}
}