// Arup Guha
// 5/24/2017
// Solution to 2017 World Finals Problem E: Need for Speed

import java.util.*;

public class speed {

	public static int[] dist;
	public static int[] speed;
	public static int n;

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);
		n = stdin.nextInt();
		int total = stdin.nextInt();

		// Read in all segments.
		int min = 10000;
		dist = new int[n];
		speed = new int[n];
		for (int i=0; i<n; i++) {
			dist[i] = stdin.nextInt();
			speed[i] = stdin.nextInt();
			min = Math.min(speed[i], min);
		}

		// Set up parameters for binary search, can't have speed of 0 in our search range.
		double low = -min+1e-9;
		double high = 10000000;

		// Run binary search 100 iterations.
		for (int i=0; i<100; i++) {
			double mid = (low+high)/2;
			double f = eval(mid);
			if (f > total)
				low = mid;
			else
				high = mid;
		}

		// Output result.
		System.out.println(low);
	}

	// Returns how long the trip would take if the odometer was off by c.
	public static double eval(double c) {
		double t = 0;
		for (int i=0; i<n; i++) {
			t += ((double)dist[i])/(speed[i]+c);
		}
		return t;
	}
}