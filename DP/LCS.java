// Arup Guha
// 9/26/03
// The methods below solve the longest common subsequence problem
// recursively and using dynamic programming
// Edited slightly on 3/18/2020 for COP 4516

import java.io.*;
import java.util.*;

public class LCS {
	
	public static int[][] memo;
	public static String s1 = "YECNQCGKAFAABBABABA";
	public static String s2 = "LYECNERSKABBABABABA";

  	public static void main(String [] args) throws IOException {
    	System.out.println("LCS = "+lcsrec(s1,s2));
    	System.out.println("LCS = "+lcsrec2(s1,s2));
    	System.out.println("LCS = "+lcsdyn(s1,s2));
		
		// Set up the memo table.
		memo = new int[s1.length()][s2.length()];
		for (int i=0;i<s1.length(); i++) Arrays.fill(memo[i],-1);
		
		// Run it!
		System.out.println("LCS memo = "+lcsmemo(s1.length()-1,s2.length()-1));
  	}

  	// Precondition: Both x and y are non-empty strings.
  	public static int lcsrec(String x, String y) {

    	// Simple base case...
    	if (x.length() == 0 || y.length() == 0) return 0; 
     
    	// Corresponding beginning characters match.
    	if (x.charAt(0) == y.charAt(0))
      		return 1+lcsrec(x.substring(1), y.substring(1));

    	// Corresponding characters do not match.
    	else 
      		return Math.max(lcsrec(x,y.substring(1)), lcsrec(x.substring(1),y));
  }

  	// Note: This second recursive version, though more cumbersome in code
  	//       because of how the substring method works more accurately
  	//       mirrors the algorithms used in the dynamic programming method
  	//       below, since this uses LCS's of prefixes to compute LCS's
  	//       of longer strings.
  	// Precondition: Both x and y are non-empty strings.
  	public static int lcsrec2(String x, String y) {

    	// Simple base case...
    	if (x.length() == 0 || y.length() == 0) return 0; 

    	// Corresponding last characters match.
    	if (x.charAt(x.length()-1) == y.charAt(y.length()-1))
      		return 1+lcsrec2(x.substring(0,x.length()-1), y.substring(0,y.length()-1));

    	// Corresponding last characters do not match.
    	else 
      		return Math.max(lcsrec2(x,y.substring(0,y.length()-1)), lcsrec2(x.substring(0,x.length()-1),y));
  	}
	
	// Returns the LCS of s1[0..idx1] and s2[0..idx2].
  	public static int lcsmemo(int idx1, int idx2) {

    	// Simple base case...
    	if (idx1 < 0 || idx2 < 0) return 0;

		// I did this case before.
		if (memo[idx1][idx2] != -1) return memo[idx1][idx2];

    	// Corresponding last characters match.
    	if (s1.charAt(idx1) == s2.charAt(idx2))
      		return memo[idx1][idx2] = 1+lcsmemo(idx1-1, idx2-1);

    	// Corresponding last characters do not match.
    	else 
      		return memo[idx1][idx2] = Math.max(lcsmemo(idx1,idx2-1), lcsmemo(idx1-1, idx2));
  	}

  	// Precondition: Both x and y are non-empty strings.
  	public static int lcsdyn(String x, String y) {

    	int i,j;
    	int lenx = x.length();
    	int leny = y.length();
    	int[][] table = new int[lenx+1][leny+1];

    	// Initialize table that will store LCS's of all prefix strings.
    	// This initialization is for all empty string cases.
    	for (i=0; i<=lenx; i++) 
      		table[i][0] = 0;
    	for (i=0; i<=leny; i++)
      		table[0][i] = 0;
    
    	// Fill in each LCS value in order from top row to bottom row,
    	// moving left to right.
    	for (i = 1; i<=lenx; i++) {
      		for (j = 1; j<=leny; j++) {

        		// If last characters of prefixes match, add one to former value.
        		if (x.charAt(i-1) == y.charAt(j-1))
          			table[i][j] = 1+table[i-1][j-1];

        		// Otherwise, take the maximum of the two adjacent cases.
        		else
          			table[i][j] = Math.max(table[i][j-1], table[i-1][j]);
        
        		System.out.print(table[i][j]+" ");
      		}
      		System.out.println();
    	}
    	
    	// This is our answer.
    	return table[lenx][leny];
  	}

}

