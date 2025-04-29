// Arup Guha
// 3/18/2020
// Written for COP 4516 Video

import java.util.*;

public class fib {
	
	public static int[] res;

	public static void main(String[] args) {
	
		// Will store fibonacci answers here.
		res = new int[46];
		Arrays.fill(res, -1);
		
		// Run it!
		long start = System.currentTimeMillis();
		int ans = fibonacci(45);
		long end = System.currentTimeMillis();
		
		// Print the result and time.
		System.out.println("ans is "+ans);
		System.out.println("took "+(end-start)+ " ms.");
	}

	// Returns fib(n) uses memoization.
	public static int fibonacci(int n) {
		
		// normal base case.
		if (n < 2) return n;
		
		// I did this before, just return the answer (I have it stored.)
		if (res[n] != -1) return res[n];
		
		// Before I return the answer, just store it.
		return res[n] = fibonacci(n-1) + fibonacci(n-2);
	}
	
	// Returns fib(n) using dynamic programming.
	public static int fibonaccidp(int n) {
		
		// Allocate storage for all answers.
		int[] dp = new int[n+1];
		
		// Fill in the base cases from recursion.
		dp[1] = 1;
		
		// Fill in each answer of the table in an appropriate order, combining "smaller" answer as necessary.
		for (int i=2; i<=n; i++)
			dp[i] = dp[i-1] + dp[i-2];
		return dp[i];
	}
}