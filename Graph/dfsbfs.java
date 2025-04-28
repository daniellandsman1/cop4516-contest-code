// Arup Guha
// 7/13/2017
// Illustration of how to read in a graph as an Edge list and run DFS or BFS on it.

import java.util.*;

public class dfsbfs {

	public static ArrayList[] graph;
	public static int numV;

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);
		numV = stdin.nextInt();
		int numE = stdin.nextInt();

		// Set up each arraylist.
		graph = new ArrayList[numV];
		for (int i=0; i<numV; i++)
			graph[i] = new ArrayList<Integer>();

		// Add each edge, both ways to the graph.
		for (int i=0; i<numE; i++) {
			int v1 = stdin.nextInt()-1;
			int v2 = stdin.nextInt()-1;
			graph[v1].add(v2);
			graph[v2].add(v1);
		}

		// Count # of connected components.
		int numComponents = 0;
		int[] compNum = new int[numV];
		Arrays.fill(compNum, -1);
		// Try starting from each vertex.
		for (int i=0; i<numV; i++) {

			// We haven't been here yet, DFS from here.
			if (compNum[i] == -1) {
				dfs(i, compNum, numComponents);
				numComponents++;
			}
		}

		// Print out number of components.
		System.out.println("Your graph has "+numComponents+" components");

		// Go through each component.
		for (int i=0; i<numComponents; i++) {
			System.out.print("Items in component "+i+":");

			// Print everything in component i.
			for (int j=0; j<numV; j++)
				if (compNum[j] == i)
					System.out.print(" "+j);
			System.out.println();

		}

		// Run a BFS from each vertex, printing out distances.
		for (int i=0; i<numV; i++) {

			int[] dist = bfs(i);
			System.out.println("Here are the distances from "+i+" to each vertex.");
			for (int j=0; j<numV; j++)
				System.out.print(dist[j]+" ");
			System.out.println();


		}
	}

	public static void dfs(int v, int[] compNum, int ID) {

		// Mark this vertex.
		compNum[v] = ID;

		// Recursively visit all unvisted neighbors.
		for (Integer next : (ArrayList<Integer>)graph[v])
			if (compNum[next] == -1)
				dfs(next, compNum, ID);
	}

	// Runs a BFS from vertex v and returns a list of distances to each vertex from v.
	// -1 indicates no path.
	public static int[] bfs(int v) {

		// Set up our distance array.
		int[] dist = new int[numV];
		Arrays.fill(dist, -1);
		dist[v] = 0;

		// Set up our queue for hte BFS.
		LinkedList<Integer> q = new LinkedList<Integer>();
		q.offer(v);

		// Run the BFS until there are no new places to go.
		while (q.size() > 0) {

			// Get the next item.
			int cur = q.poll();

			// Go through neighbors - if there is a new one, add to the queue and set its distance.
			for (Integer next : (ArrayList<Integer>)graph[cur]) {
				if (dist[next] == -1) {
					q.offer(next);
					dist[next] = dist[cur] + 1;
				}
			}
		}

		return dist;
	}
}