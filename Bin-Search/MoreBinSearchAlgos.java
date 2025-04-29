/*
 * BIN SEARCH DEBUGGING CHEAT SHEET:
 * 
 * SETTING LOW AND HIGH:
 * 	DISCRETE:
 * 		Make sure low <= ans, high >= ans,
 * 		make sure they are ints/longs and overflows
 * 		don't occur, and no array out of bounds issues
 * 	CONTINUOUS:
 * 		Make sure low <= ans, high >= ans, but also ake sure
 * 		that low isn't too low and high isn't too high, if
 * 		they are, they could crash math functions
 * LOOP:
 * 	DISCRETE:
 * 		while (low < high)
 * 	CONTINUOUS:
 * 		for (iter = 0; iter < 100; iter++)
 * ASSIGNING MID:
 * 	DISCRETE:
 * 		mid = (low+high)/2 or mid = (low + high + 1)/2
 * 		Plug in low = 2, high = 3 after code is written
 * 		to determine which will terminate
 * 	CONTINUOUS:
 * 		mid = (low+high)/2.0
 * UPDATING:
 * 	FOR BOTH:
 * 		After each iteration, either low OR high should
 * 		be updated, but not both. Make sure you update
 * 		the correct one!
 * WHAT TO UPDATE TO:
 * 	DISCRETE:
 * 		For low, we have two choices: low = mid or low = mid+1.
 * 		For high, we have two choices: high = mid or high = mid - 1.
 * 		We will typically pair low=mid with high=mid-1 or pair
 * 		low=mid+1 with high=mid
 * 	CONTINUOUS:
 * 		Always either low = mid or high = mid	 
 */

import java.util.*;
import java.io.*;

public class MoreBinSearchAlgos {
    static PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args) {
        // Test discrete binary search
        int target = 25;
        int resultDiscrete = discreteBinarySearch(target);
        out.println("Discrete Binary Search: Smallest x with x^2 >= " + target + " is " + resultDiscrete);

        // Test continuous binary search
        double resultContinuous = continuousBinarySearch(target);
        out.printf("Continuous Binary Search: Smallest x with x^2 >= %d is %.6f\n", target, resultContinuous);

        // Test last true (max x where x*x <= target)
        int resultLastTrue = lastTrueSearch(target);
        out.println("Last True Search: Largest x with x^2 <= " + target + " is " + resultLastTrue);

        // Test lower bound
        int[] arr = {1, 3, 3, 5, 8, 8, 10};
        int lb = lowerBound(arr, 5);
        out.println("Lower Bound: First index where arr[i] >= 5 is " + lb);

        // Test upper bound
        int ub = upperBound(arr, 5);
        out.println("Upper Bound: First index where arr[i] > 5 is " + ub);

        out.flush();
        out.close();
    }

    // --- Discrete Binary Search ---
    static int discreteBinarySearch(int target) {
        int low = 0, high = (int)1e9;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (mid * mid >= target) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    // --- Continuous Binary Search ---
    static double continuousBinarySearch(int target) {
        double low = 0, high = 1e9;
        double eps = 1e-6;
        while (high - low > eps) {
            double mid = (low + high) / 2;
            if (mid * mid >= target) {
                high = mid;
            } else {
                low = mid;
            }
        }
        return low;
    }

    // --- Last True Search ---
    // Finds max value that satisfies condition
    static int lastTrueSearch(int target) {
        int low = 0, high = (int)1e9, ans = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (mid * mid <= target) {
                ans = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return ans;
    }

    // --- Lower Bound ---
    // Smallest i where arr[i] >= x
    static int lowerBound(int[] arr, int x) {
        int low = 0, high = arr.length;
        while (low < high) {
            int mid = (low + high) / 2;
            if (arr[mid] >= x) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    // --- Upper Bound ---
    // Smallest i where arr[i] > x
    static int upperBound(int[] arr, int x) {
        int low = 0, high = arr.length;
        while (low < high) {
            int mid = (low + high) / 2;
            if (arr[mid] > x) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }
}
