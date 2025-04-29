// Arup Guha
// 11/20/2013
// Solution to 2013 Pacific NorthWest Problem B: Bones's Battery

import java.util.*;

public class bones {

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);
		int numCases = stdin.nextInt();

		// Go through each case.
		for (int loop=0; loop<numCases; loop++) {

			int n = stdin.nextInt();
			int k = stdin.nextInt();
			int e = stdin.nextInt();

			// Initialize the graph.
			long[][] adj = new long[n][n];
			for (int i=0; i<n; i++) {
				Arrays.fill(adj[i], 1000000000000L);
				adj[i][i] = 0;
			}

			// Fill it in.
			for (int i=0; i<e; i++) {
				int v1 = stdin.nextInt();
				int v2 = stdin.nextInt();
				long d = stdin.nextLong();
				adj[v1][v2] = d;
				adj[v2][v1] = d;
			}

			// We only care about shortest distances between nodes, so run Floyd's.
			floyd(adj);

			// Use a binary search to solve.
			System.out.println(binsearch(adj,k));
		}
	}

	public static long binsearch(long[][] adj, int k) {

		// Set up binary search.
		int n = adj.length;
		long low = 1L, high = 1000000000000L;
		long mid = 0;

		while (low < high) {

			// See if mid miles will work for charging.
			mid = (low+high)/2;
			long[][] m = new long[n][n];
			for (int i=0; i<n; i++) {
				Arrays.fill(m[i], k+1);
				m[i][i] = 0;
			}

			// This is our auxiliary array. Only use edges with weight <= mid.
			for (int i=0; i<n; i++)
				for (int j=0; j<n; j++)
					if (adj[i][j] > 0 && adj[i][j] <= mid)
						m[i][j] = 1L;

			// Find graph girth and compare to k...
			floyd(m);
			if ((int)max(m) <= k)
				high = mid;
			else
				low = mid+1;

		}

		// Here is our answer.
		return low;
	}

	// Returns the max entry in m.
	public static long max(long[][] m) {
		int n = m.length;
		long best = 0;
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++)
				if (m[i][j] > best)
					best = m[i][j];
		}
		return best;
	}

	// Change adj to be shortest distance array.
	public static void floyd(long[][] adj) {
		int n = adj.length;
		for (int k=0; k<n; k++)
			for (int i=0; i<n; i++)
				for (int j=0; j<n; j++)
					adj[i][j] = Math.min(adj[i][j], adj[i][k]+adj[k][j]);
	}
}