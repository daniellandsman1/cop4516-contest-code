import java.util.*;

public class bruteForce {

    // Odometer
    public static void odometer(int[] digits, int numDigits, int k, boolean printFlag) {
        if(k == digits.length) {
            print(digits, digits.length, printFlag); // This is a function, call bruteprint
            return;
        }

        for(int i = 0; i < numDigits; i++) {
            digits[k] = i;
            odometer(digits, numDigits, k+1, printFlag);
        }
    }

    // Combination
    public static void printCombos(int[] combo, int k, int[] items) {
        print(combo, items, k, true);  // This is a function, call bruteprint

        int start = 0;
        if(k > 0) start = combo[k+1] + 1;

        for(int i = start; i < combo.length; i++) {
            combo[k] = i;
            printCombos(combo, k+1, items);
        }
    }

    public static void printperms(int[] perm, boolean[] used, int k, int[] items) {
    
    	// Filled out a permutation, print it.
    	if (k == perm.length) print(perm, items, perm.length, true);  // This is a function, call bruteprint

		// Try each unused item in slot k.
    	for (int i=0; i<perm.length; i++) {
        	if (!used[i]) {
        		
        		// Mark it and put it in.
            	used[i] = true;
           		perm[k] = i;
           		
           		// Recurse.
            	printperms(perm, items, used, k+1);
            	
            	// Must unmark...
            	used[i] = false;
        	}
    	}
	}

    public static void derange(int[] perm, boolean[] used, int k) {

    	if (k == perm.length) print(perm, perm.length, true);

    	for (int i=0; i<perm.length; i++) {
        	if (!used[i] && i != k) {
        		
            	used[i] = true;
            	perm[k] = i;
            	
            	derange(perm, used, k+1);
            	
            	used[i] = false;
        	}
    	}
	}
	
	// Prints all skip-level upwards of length word.length with the first k items fixed.
	public static void upwards(char[] word, int skip, int k) {
    
    	// Filled the word, print it.
    	if (k == word.length) System.out.println(new String(word));

		// Recursive case.
    	else {
        
        	// Calculate first possible letter to place in slot k.
        	char start = 'a';
        	if (k > 0) start = (char)(word[k-1] + skip + 1);

			// Try this and all subsequent letters in slot k.
        	for (char i=start; i<='z'; i++) {
            	word[k] = i;
            	upwards(word, skip, k+1);
        	}
    	}
	}
	
	// if mode is true, prints items[0..length-1]
	// if mode is false, it interprets items as boolean array with 0 as false
	// and all other values as true and prints out the indexes that store true values
	// in sorted order.
	public static void print(int[] index, int[] items, int length, boolean mode) {
		
		if (mode) {
			for (int i=0; i<length; i++)
				System.out.print(items[index[i]]+" ");
		}
		
		else {
			for (int i=0; i<length; i++)
				if (items[i] != 0)
					System.out.print(i+" ");
		}
		System.out.println();
	}
}