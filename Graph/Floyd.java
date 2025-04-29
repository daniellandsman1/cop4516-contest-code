// Stephen Fulwider
//	Floyd's all pairs shortest path algorithm
//	with path reconstruction
// Email knightry@gmail.com with questions

import java.util.LinkedList;

public class Floyd {

	public static void main(String[] args) {
		new Floyd(); // I do this so I don't have to use static variables everywhere
	}

	final int oo = (int) 1e9; // infinity!

	int N; // number of nodes
	int[][] G; // original graph in adj. matrix form

	int[][] D; // computed distance between each pair of vertices
	int[][] P; // predecessor matrix

	Floyd() {
		// set up a graph - draw this out so you can do some testing!
		// notice this is a directed graph. this means an edge from a->b
		// doesn't imply an edge of the same weight from b->a
		// oo denotes no edge, otherwise the number denotes the length
		// of the edge (negative edge weights are possible)
		N = 5;
		G = new int[][] {
				{ 0, 3, 8, oo, -4 },
				{ oo, 0, oo, 1, 7 },
				{ oo, 4, 0, oo, oo },
				{ oo, oo, -5, 0, oo },
				{ oo, oo, oo, 6, 0 }
		};

		// run floyds - we only need to run this ONCE to get the shortest path
		// between ALL pairs of points!
		floyds();

		// Print out all the paths
		for (int source = 0; source < N; ++source)
			for (int target = 0; target < N; ++target) {
				LinkedList<Integer> path = getPath(source, target);
				System.out.printf("Length of shortest path from %d to %d: %d%n", source, target, D[source][target]);
				if (path != null)
					System.out.printf("   Path: %s%n%n", path);
				else
					System.out.printf("   Path does not exist!%n%n");
			}
	}

	// run floyds all pairs shortest path algorithm
	// this algorithm runs in O(N^3) time
	void floyds() {
		// first set up the predecessor matrix
		// we will define P[i][j] to be the predecessor of node j
		// when traveling on the path from i->j.
		// Example 1: If the path from 1 to 2 is the single edge 1->2,
		// then P[1][2] = 1
		// Example 2: If the path from 3 to 4 is the path 3->4->5,
		// then P[3][5] = 4 and P[3][4] = 3
		// We use -1 to denote no path from i to j

		P = new int[N][N];
		for (int i = 0; i < N; ++i)
			for (int j = 0; j < N; ++j) {
				if (G[i][j] < oo) // only consider edges which exist originally
					P[i][j] = i;
				else
					P[i][j] = -1;
			}

		// next make a copy of the original graph to do work on
		// notice that you only need to do this if you need to maintain the
		// original graph for some reason. Otherwise you can just overwrite G

		D = new int[N][N];
		for (int i = 0; i < N; ++i)
			for (int j = 0; j < N; ++j)
				D[i][j] = G[i][j];

		// now run the algorithm itself

		for (int k = 0; k < N; ++k)
			for (int i = 0; i < N; ++i)
				for (int j = 0; j < N; ++j)
					if (D[i][j] > D[i][k] + D[k][j] && D[i][k] < oo && D[k][j] < oo) {
						// using node k helps, update the weight and the predecessor of i->j
						D[i][j] = D[i][k] + D[k][j];
						P[i][j] = P[k][j];
					}
	}

	// reconstruct the path in reverse (since we store the predecessor, it makes
	// sense that we would need to start and the end and work our way backwards)
	LinkedList<Integer> getPath(int source, int target) {
		// first check if the path exists - if it doesn't return null
		if (D[source][target] == oo)
			return null;

		// now we know the path exists, so reconstruct it
		LinkedList<Integer> path = new LinkedList<Integer>();
		path.addFirst(target);
		while (target != source) {
			target = P[source][target];
			path.addFirst(target);
		}
		return path;
	}

}
