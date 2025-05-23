// "UCF Programming Team" Hackpack Code
// Original Author(s) - Unknown
// Taken from Team Badlands Hackpack
// Commented and Edited by Arup Guha on 3/6/2017 for COP 4516
// Code for Ford Fulkerson Algorithm

import java.util.*;

public class FordFulkerson {

	// Stores graph.
	public int[][] cap;
	public int n;
	public int source;
	public int sink;

	// "Infinite" flow.
	final public static int oo = (int)(1E9);

	// Set up default flow network with size+2 vertices, size is source, size+1 is sink.
	public FordFulkerson(int size) {
		n = size + 2;
		source = n - 2;
		sink = n - 1;
		cap = new int[n][n];
	}

	// Adds an edge from v1 -> v2 with capacity c.
	public void add(int v1, int v2, int c) {
		cap[v1][v2] = c;
	}

	// Wrapper function for Ford-Fulkerson Algorithm
	public int ff() {

		// Set visited array and flow.
		boolean[] visited = new boolean[n];
		int flow = 0;

		// Loop until no augmenting paths found.
		while (true) {

			// Run one DFS.
			Arrays.fill(visited, false);
			int res = dfs(source, visited, oo);

			// Nothing found, get out.
			if (res == 0) break;

			// Add this flow.
			flow += res;
		}

		// Return it.
		return flow;
	}

	// DFS to find augmenting math from v with maxflow at most min.
	public int dfs(int v, boolean[] visited, int min) {

		// got to the sink, this is our flow.
		if (v == sink)  return min;

		// We've been here before - no flow.
		if (visited[v])  return 0;

		// Mark this node and recurse.
		visited[v] = true;
		int flow = 0;

		// Just loop through all possible next nodes.
		for (int i = 0; i < n; i++) {

			// We can augment in this direction.
			if (cap[v][i] > 0)
				flow = dfs(i, visited, Math.min(cap[v][i], min));

			// We got positive flow on this recursive route, return it.
			if (flow > 0) {

				// Subtract it going forward.
				cap[v][i] -= flow;

				// Add it going backwards, so that later, we can flow back through this edge as a backedge.
				cap[i][v] += flow;

				// Return this flow.
				return flow;
			}
		}

		// If we get here there was no flow.
		return 0;
	}
}





//Arup Guha
//Edit of FordFulkerson Code (from UCF Hackpack)
//3/6/2017
//Code for Edmunds Karp Algorithm

import java.util.*;

public class EdmundsKarp {

	// Stores graph.
	public int[][] cap;
	public int n;
	public int source;
	public int sink;

	// "Infinite" flow.
	final public static int oo = (int)(1E9);

	// Set up default flow network with size+2 vertices, size is source, size+1 is sink.
	public EdmundsKarp(int size) {
		n = size + 2;
		source = n - 2;
		sink = n - 1;
		cap = new int[n][n];
	}

	// Adds an edge from v1 -> v2 with capacity c.
	public void add(int v1, int v2, int c) {
		cap[v1][v2] = c;
	}

	// Wrapper function for Ford-Fulkerson Algorithm
	public int flow() {

		// Set visited array and flow.
		int flow = 0;

		// Loop until no augmenting paths found.
		while (true) {

			// Run one BFS.
			int res = bfs();

			// Nothing found, get out.
			if (res == 0) break;

			// Add this flow.
			flow += res;
		}

		// Return it.
		return flow;
	}

	// DFS to find augmenting math from v with maxflow at most min.
	public int bfs() {

		// Set up BFS.
		int[] reach = new int[n+2];
		int[] prev = new int[n+2];
		LinkedList<Integer> q = new LinkedList<Integer>();
		reach[source] = oo;
		Arrays.fill(prev, -1);
		prev[source] = source;
		q.offer(source);

		// Run BFS loop.
		while (q.size() > 0) {

			// Get next node - if it's sink, we're done.
			int v = q.poll();
			if (v == sink) break;

			// Try each neighbor.
			for (int i=0; i<n; i++) {

				// If we can go here, mark, previous, flow to i, and put i in queue.
				if (prev[i] == -1 && cap[v][i] > 0) {
					prev[i] = v;
					reach[i] = Math.min(cap[v][i], reach[v]);
					q.offer(i);
				}
			}
		}

		// Didn't work.
		if (reach[sink] == 0) return 0;

		// Mark last two vertices.
		int v1 = prev[sink];
		int v2 = sink;
		int flow = reach[sink];

		// Actually put flow through.
		while (v2 != source) {

			// Puts flow through.
			cap[v1][v2] -= flow;
			cap[v2][v1] += flow;

			// Moves to previous edge.
			v2 = v1;
			v1 = prev[v1];
		}

		// This was our flow.
		return flow;
	}
}