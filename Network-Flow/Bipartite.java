/*
 * 	NOTES:
 * 
 * 	THIS FILE SHOWS EXAMPLES OF BIPARTITE MATCHING PROBLEMS: Welcome Party, Museum Guards, Cow Steeplechase
 * 
 *	MAX FLOW-MIN CUT THEOREM: In computer science and optimization theory, the max-flow min-cut theorem states that 
 *	in a flow network, the maximum amount of flow passing from the source to the sink is equal to the total weight of 
 *	the edges in a minimum cut, i.e., the smallest total weight of the edges which if removed would disconnect the 
 * 	source from the sink.
 *
 *	For example, imagine a network of pipes carrying water from a reservoir (the source) to a city (the sink). 
 *	Each pipe has a capacity representing the maximum amount of water that can flow through it per unit of time. 
 *	The max-flow min-cut theorem tells us that the maximum amount of water that can reach the city is limited by 
 *	the smallest total capacity of any set of pipes that, if cut, would completely isolate the reservoir from 
 *	the city. This smallest total capacity is the min-cut. So, if there's a bottleneck in the pipe network, 
 *	represented by a small min-cut, that bottleneck will determine the overall maximum flow of water to the city.
 */
 

// Arup Guha
// 3/4/2019
// Solution to 2013 MCPC Problem A: Welcome Party (for COP 4516)
import java.util.*;

public class welcome {
	
	public static void main(String[] args) {
		
		Scanner stdin = new Scanner(System.in);
		int n = stdin.nextInt();
		
		// Go through cases.
		while (n != 0) {
			
			// Need source, sink and 1 node for each letter.
			FordFulkerson graph = new FordFulkerson(52);
			
			// Edges just go from first to last name (use 1st letter)
			for (int i=0; i<n; i++) {
				int v1 = stdin.next().charAt(0) - 'A';
				int v2 = stdin.next().charAt(0) - 'A';
				graph.add(v1, v2+26, 1);
			}
			
			// Source sink links.
			for (int i=0; i<26; i++) {
				graph.add(52, i, 1);
				graph.add(i+26, 53, 1);
			}
			
			// Get max flow and print.
			System.out.println(graph.ff());
			
			// Get next case.
			n = stdin.nextInt();
		}
	}
}






//Arup Guha
//6/21/2012
//Solution to 2009 SER Problem: Museum Guards
//Edited on 3/6/2017 to use Dinic Code shown in class.

import java.util.*;

public class museum3 {

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);
		int n = stdin.nextInt();

		// Run all cases.
		while (n != 0) {

			// 0 = source, s-1 = sink, 1-n guards, n+1 -> n+48 shifts
			int s = n + 2 + 48;
			int[][] graph = new int[s][s];
			for (int i=0; i<s; i++)
				Arrays.fill(graph[i], 0);

			// Read in each guard.
			for (int i=0; i<n; i++) {
				int shifts = stdin.nextInt();
				int max = stdin.nextInt();
				boolean[] available = new boolean[1440];
				Arrays.fill(available, false);

				// Fill in guard's availability.
				for (int j=0; j<shifts; j++) {
					String t1 = stdin.next();
					String t2 = stdin.next();
					fillArray(convert(t1), convert(t2), available);
				}

				// Convert the minutes to 30 minute availability slots.
				int[] slots = getSlots(available);

				// These are the flows we want.
				graph[s-2][i] = max/30;
				for (int j=0; j<slots.length; j++) {
					graph[i][n+j] = slots[j];
				}
			}

			// Try 0 guards, then 1, etc.
			if (!solvable(graph, n, 1))
				System.out.println("0");
			else {
				int tryval = 1;
				while (solvable(graph, n, tryval)) tryval++;
				System.out.println(tryval-1);
			}

			n = stdin.nextInt();
		}
	}

	// Returns true iff we can cover each shift with tryval guards.
	public static boolean solvable(int[][] graph, int n, int tryval) {

		int s = graph.length;
		Dinic myNetFlow = new Dinic(n+48);
		for (int i=0; i<s; i++)
			for (int j=0; j<s; j++)
				if (graph[i][j] > 0)
					myNetFlow.add(i, j, graph[i][j], 0);

		// Fill in the flows from all the shifts to the sink.
		for (int j=0; j<48; j++)
			myNetFlow.add(n+j, s-1, tryval, 0);

		int flow = myNetFlow.flow();
		return flow == tryval*48;
	}

	// Returns the number of minutes after midnight represented by s.
	public static int convert(String s) {
		int hr = 10*(s.charAt(0) - '0') + s.charAt(1) - '0';
		int min = 10*(s.charAt(3) - '0') + s.charAt(4) - '0';
		return 60*hr + min;
	}

	// Fills available in interval [start, end) if start < end.
	// Or [start, 1439], [0, end) if end < start. Or all slots if start == end.
	public static void fillArray(int start, int end, boolean[] available) {

		// Easy case.
		if (start == end) Arrays.fill(available, true);

		// Standard case.
		if (start < end) {
			for (int i=start; i<end; i++)
				available[i] = true;
		}

		// Loop through midnight.
		else {
			for (int i=0; i<end; i++)
				available[i] = true;
			for (int i=start; i<available.length; i++)
				available[i] = true;
		}
	}

	// Convert minute availability to 30 minute time slots.
	public static int[] getSlots(boolean[] array) {

		int[] slots = new int[48];
		for (int i=0; i<slots.length; i++) {

			// Make sure we can make all 30 minutes before being available for this slot.
			int ans = 1;
			for (int j=0; j<30; j++)
				if (!array[30*i+j]) {
					ans = 0;
					break;
				}
			slots[i] = ans;
		}
		return slots;
	}
}





//Arup Guha
//3/2/2013
//Solution to UCF Practice Problem E: Cow Steeplechase
//Uses my previous network flow code to implement bipartite matching.

import java.io.*;
import java.util.*;

public class e {

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);
		int numCases = stdin.nextInt();
		
		for (int loop=0; loop<numCases; loop++) {
		
			int n = stdin.nextInt();

			// Read in all lines.
			int[][] lines = new int[n][4];
			for (int i=0; i<n; i++)
				for (int j=0; j<4; j++)
					lines[i][j] = stdin.nextInt();

			// Our strategy is to do a maximum matching between the horizontal and vertical lines.
			// Our source will connect to all horizontal lines and our sink to all vertical lines.
			// We'll connect horizontal lines to vertical ones iff they intersect.

			int[][] adj = new int[n+2][n+2];

			// Capacities from source to horizontal line and vertical to sink.
			for (int i=1; i<=n; i++) {
				if (horizontal(lines[i-1]))
					adj[0][i] = 1;
				else
					adj[i][n+1] = 1;
			}

			// Try all pairs.
			for (int i=0; i<n; i++) {
				for (int j=i+1; j<n; j++) {

					// Try both orders of intersection.
					if (horizontal(lines[i]) && vertical(lines[j])) {
						if (intersect(lines[i], lines[j]))
							adj[i+1][j+1] = 1;
					}
					else if (vertical(lines[i]) && horizontal(lines[j])) {
						if (intersect(lines[j], lines[i]))
							adj[j+1][i+1] = 1;
					}
				}
			}

			// Get our answer. We need to subtract these out of all the lines, namely
			// the flow of this graph equals what we must remove.
			networkflow graph = new networkflow(adj, 0, n+1);
			int ans = graph.getMaxFlow();
			System.out.println(n-ans);
			
		}
	}

	public static boolean horizontal(int[] line) {
		return line[1] == line[3];
	}

	public static boolean vertical(int[] line) {
		return line[0] == line[2];
	}

	public static boolean intersect(int[] hline, int[] vline) {

		return Math.min(hline[0], hline[2]) <= vline[0] && Math.max(hline[0], hline[2]) >= vline[0] &&
			   Math.min(vline[1], vline[3]) <= hline[1] && Math.max(vline[1], vline[3]) >= hline[1];
	}
}