// HAS TEMPLATE FOR MEMOIZATION, TABULATION,
// SUBSET SUM, BINOMIAL COEFFICIENTS, WORLD SERIES
// MAKE CHANGE 

import java.util.Arrays;

public class DPMaster {
	
	// Main method to test everything
    public static void main(String[] args) {

        // Test Memoization
        int n = 10;
        memo = new int[n+1];
        Arrays.fill(memo, -1);
        System.out.println("fibMemo(10): " + fibMemo(10)); // 55

        // Test Tabulation
        System.out.println("fibTab(10): " + fibTab(10)); // 55

        // Test Subset Sum
        int[] nums = {3, 34, 4, 12, 5, 2};
        int target = 9;
        System.out.println("subsetSum(nums, 9): " + subsetSum(nums, target)); // true

        // Test Binomial Coefficient
        System.out.println("binomialCoeff(5, 2): " + binomialCoeff(5, 2)); // 10

        // Test World Series Problem
        int toWin = 4; // Best of 7 series
        double p = 0.6; // Team A has 60% chance to win any game
        System.out.printf("World Series win probability: %.5f\n", worldSeries(toWin, p));
        
        // Test Make Change DP
        int[] coins = {1, 3, 4}; // Available coins
        int amount = 6;
        System.out.println("Minimum coins to make " + amount + " = " + minCoins(coins, amount));
        // Should print 2 (use two coins of 3)
    }

    // 1. Basic Memoization Template
    static int[] memo;
    public static int fibMemo(int n) {
        if (n <= 1) return n;
        if (memo[n] != -1) return memo[n];
        return memo[n] = fibMemo(n-1) + fibMemo(n-2);
    }

    // 2. Basic Tabulation Template
    public static int fibTab(int n) {
        if (n <= 1) return n;
        int[] dp = new int[n+1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n];
    }

    // 3. Subset Sum Problem
    public static boolean subsetSum(int[] nums, int target) {
        boolean[] dp = new boolean[target+1];
        dp[0] = true; // 0 sum is always possible (empty subset)

        for (int num : nums) {
            for (int sum = target; sum >= num; sum--) {
                dp[sum] |= dp[sum - num];
            }
        }
        return dp[target];
    }

    // 4. Compute Binomial Coefficients (n choose k) using DP
    public static int binomialCoeff(int n, int k) {
        int[][] C = new int[n+1][k+1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= Math.min(i, k); j++) {
                if (j == 0 || j == i)
                    C[i][j] = 1;
                else
                    C[i][j] = C[i-1][j-1] + C[i-1][j];
            }
        }
        return C[n][k];
    }

    // 5. World Series Problem
    // Probability that a team wins the series
    static double[][] wsMemo;
    public static double worldSeries(int toWin, double p) {
        wsMemo = new double[toWin+1][toWin+1];
        for (double[] row : wsMemo) Arrays.fill(row, -1.0);
        return dfs(toWin, toWin, p);
    }

    private static double dfs(int a, int b, double p) {
        if (a == 0) return 1.0; // Team A wins
        if (b == 0) return 0.0; // Team B wins
        if (wsMemo[a][b] != -1.0) return wsMemo[a][b];

        // Win next game (prob p), or lose next game (prob 1-p)
        return wsMemo[a][b] = p * dfs(a-1, b, p) + (1-p) * dfs(a, b-1, p);
    }
    
 // Returns minimum coins needed to make amount (or -1 if impossible)
    public static int minCoins(int[] coins, int amount) {
        int INF = (int)1e9; // A big number representing "impossible"
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, INF);
        dp[0] = 0; // 0 coins to make 0 amount

        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }

        return dp[amount] == INF ? -1 : dp[amount];
    }
}
