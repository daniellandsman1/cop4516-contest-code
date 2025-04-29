// Arup Guha
// 11/15/2014
// Solution to 2014 South East Regional D1 and D2 Problem: Stained Carpet

import java.util.*;

public class carpet {

	final public static double EPSILON = 1e-5;

	public static double[] rays;

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);

		// Read and sort sides.
		rays = new double[3];
		for (int i=0; i<3; i++)
			rays[i] = stdin.nextDouble();
		Arrays.sort(rays);

		// Figure out dimensions for boundary case.
		double maxSide = rays[0] + rays[1];
		double offCenter = Math.abs(maxSide/2 - rays[0]);
		double height = maxSide*Math.sqrt(3)/2;

		// Simple impossible case - draw short pieces on one side...
		if (height*height + offCenter*offCenter + EPSILON < rays[2]*rays[2])
			System.out.printf("%.3f\n", -1.0);

		// Do binary search
		else
			System.out.printf("%.3f\n", solve());
	}

	// Wrapper function for binary search.
	public static double solve() {
		double high = rays[0] + rays[1] + EPSILON;
		double low = lawCos(rays[0], rays[1], Math.PI*2/3) - EPSILON;
		double side = binSearch(low, high, 0);
		return side*side*Math.sqrt(3)/4;
	}

	// Returns side length of c given sides a and b and angle C (included)...
	public static double lawCos(double a, double b, double angleC) {
		return Math.sqrt(a*a+b*b-2*a*b*Math.cos(angleC));
	}

	// Given a, b and c in a triangle, returns angle C, included btw sides a and b.
	public static double lawCosAngleC(double a, double b, double c) {
		return Math.acos((a*a+b*b-c*c)/(2*a*b));
	}

	// Binary search answer - must be in between low and high...
	public static double binSearch(double low, double high, double iter) {

		double mid = (high+low)/2;

		// Stop after 50 iterations.
		if (iter > 50) return mid;

		// Use Law of cosines to figure out three inner angles that should add to 2pi.
		double angle1 = lawCosAngleC(rays[0], rays[1], mid);
		double angle2 = lawCosAngleC(rays[0], rays[2], mid);
		double angle3 = lawCosAngleC(rays[1], rays[2], mid);
		double sumAngles = angle1 + angle2 + angle3;

		// Continue the search =)
		if (sumAngles < 2*Math.PI) return binSearch(mid, high, iter+1);
		else					   return binSearch(low, mid, iter+1);
	}
}