// Arup Guha
// 1/19/2010
// Solution for 2009 World Finals Problem A: Approach
// (Used for 1/23/2010 Team Practice)

import java.util.*;

// Just stores an interval for a plane landing.
class interval {
	
	public int start;
	public int end;
	
	public interval(int s, int e) {
		start = s;
		end = e;
	}
	
	// Returns true iff time is within this interval.
	public boolean contains(double time) {
		return start-1e-9 <= time && time <= end+1e-9;
	}
}

public class approach {
	
	public static void main(String[] args) throws Exception {
		
		Scanner stdin = new Scanner(System.in);
		
		int planes = stdin.nextInt();
		
		int caseNum = 1;
		while (planes != 0) {
			
			// Store the data.
			interval[] times = new interval[planes];
			for (int i=0; i<planes; i++) {
				int s = stdin.nextInt();
				int e = stdin.nextInt();
				times[i] = new interval(s,e);
			}
			
			// Output the answer.
			System.out.println("Case "+caseNum+": "+convertTime(getMax(times)));
			
			caseNum++;
			planes = stdin.nextInt();
		} 
		
	}
	
	// Takes a time in minutes and converts to the time the question wants.
	public static String convertTime(double t) {
		
		// Did this because my binary search usually returns a time that is a bit lower 
		// than the expected value.
		t += 1e-9;
		
		// Split out the components.
		int min = (int)t;
		int sec = (int)((t-(int)t)*60+0.5);
		
		// Format...
		if (sec < 10)
			return min+":"+"0"+sec;
		else
			return min+":"+sec;
	}
	
	// Returns the answer to the question.
	public static double getMax(interval[] times) {
		
		// Initialize the permutation we are trying.
		int[] perm = new int[times.length];
		for (int i=0; i<perm.length; i++)
			perm[i] = i;
			
		// Go through each permutation and find the best one.
		double max = 0;
		for (int i=0; i<fact(times.length); i++) {
			double thisscore = getMaxTime(perm, times);
			if (thisscore > max)
				max = thisscore;
			nextPerm(perm);
		}
		
		// Return it.
		return max;
	}
	
	// Returns n!
	public static int fact(int n) {
		int ans = 1;
		for (int i=2; i<=n; i++)
			ans*= i;
		return ans;
	}
	
	// Change perm to the next lexicographical permutation.
	public static void nextPerm(int[] perm) {
		
		// Find the spot that needs to change.
		int i = perm.length-1;
		while (i>0 && perm[i] < perm[i-1]) i--;
		i--;
		
		// So last perm doesn't cause a problem
		if (i == -1) return;
		
		// Find the spot with which to swap index i.
		int j=perm.length-1;
		while (j>i && perm[j]<perm[i]) j--;
		
		// Swap it.
		int temp = perm[i];
		perm[i] = perm[j];
		perm[j] = temp;
		
		// reverse from index i+1 to length-1.
		for (int k=i+1,m=perm.length-1; k<(perm.length+i+1)/2; k++,m--) {
			temp = perm[k];
			perm[k] = perm[m];
			perm[m] = temp;
		}
	}
	
	// Find the max time interval for THIS permutation.
	public static double getMaxTime(int[] perm, interval[] times) {
		
		// Set up our binary search.
		double high = getMaxInterval(times);
		double low = 0, mid = (low+high)/2;
		
		// Keep on going until interval is very small.
		while (high-low > 1e-9) {
			
			mid = (low+high)/2;
			
			// Standard binary search.
			if (works(perm,times,mid))
				low = mid;
			else
				high = mid;
		}
		
		return mid;
	}
	
	// Returns true if gap is permissible for THIS permutation.
	public static boolean works(int[] perm, interval[] times, double gap) {
		
		// First plane lands ASAP.
		double curtime = times[perm[0]].start;
		
		for (int i=1; i<perm.length; i++) {
			
			// Find the nearest time the next plane can land.
			double nexttime = curtime+gap;
			
			// Advance this if necessary to the start of the next plane,
			// since more time is allowable.
			if (nexttime < times[perm[i]].start)
				nexttime = times[perm[i]].start;
				
			// If the plane can't land at this time, this gap doesn't work.
			if (!times[perm[i]].contains(nexttime))
				return false;
			else 
				curtime = nexttime;
		}
		
		// We made it.
		return true;
	}
	
	// Just returns the difference between the first and last times in the
	// whole set of intervals.
	public static int getMaxInterval(interval[] times) {
		int min = times[0].start;
		int max = times[0].end;
		for (int i=1; i<times.length; i++) {
			if (times[i].start < min)
				min = times[i].start;
			if (times[i].end > max)
				max = times[i].end;
		}
		return max-min;
	}
	
}