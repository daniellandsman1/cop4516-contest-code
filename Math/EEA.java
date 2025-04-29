/**
 * Returns an int array containing:
 * 0+1) x and y in ax + by = gcd(a,b)
 * 2) gcd(a,b)
 * Note: values at indexes 0 and 1 may be negative
 *
 * O(log(min(a,b)))
 **/
 
 import java.util.*;
 
public class EEA {
 	
 
 	// Returns b inverse mod a in res[1]. Note - value returned could be negative.
 	// Only works if a and b are ints due to the multiplication in the code.
	public static long[] extendedEuclideanAlgorithm(long a, long b) {
		if (b==0)
			return new long[]{1,0,a};
		else {
			long q = a/b;
			long r = a%b;
			long[] rec = extendedEuclideanAlgorithm(b,r);
			return new long[]{rec[1], rec[0] - q*rec[1], rec[2]};
		}
	}
	
	public static long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, a%b);
	}
	
	public static void main(String[] args) {
		
		Random r = new Random();
		
		// We will test the method 1000 times.
		for (int i=0; i<1000; i++) {
			
			// Generate random ints.
			long a = r.nextInt(2000000000);
			long b = r.nextInt(2000000000);
			
			// Until we get a gcd of 1.
			while (gcd(a, b) != 1) {
				a = r.nextInt(2000000000);
				b = r.nextInt(2000000000);
			}
			
			// Make sure a > b.
			if (a < b) {
				long tmp = a;
				a = b;
				b = tmp;
			}
			
			// Call it.
			long[] res = extendedEuclideanAlgorithm(a, b);
			long inverse = (res[1]+a)%a;
			
			// Print out an error message if the inverse doesn't work.
			if ((inverse*b)%a != 1) {
				System.out.println("Failed on "+a+" and "+b+" got "+inverse);
			}
			
		}
	}
}



