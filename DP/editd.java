// Arup Guha
// 1/16/04
// Edit Distance example for Programming Team Lecture on Dynamic
// Programming. Both a recursive and DP solution are implemented.
// Converted to Java on 7/15/2014 for COP 3503

import java.util.*;

public class editd {

    public static void main(String[] args) {

        String word1, word2;
        Scanner stdin = new Scanner(System.in);

        // Small Test. Don't enter words longer than 12 or 13 characters for the
        // recursive function though!!!
        System.out.println("Enter the first word.");
        word1 = stdin.next();
        System.out.println("Enter the second word.");
        word2 = stdin.next();

        // Solve both ways.
        System.out.println("ED Rec = "+ strmatch(word1, word2, word1.length(), word2.length()));
        System.out.println("ED DP = "+ strmatchdyn(word1, word2));
    }


    // Returns the edit distance of strings s[0..i-1] and t[0..j-1].
    public static int strmatch(String s, String t, int i, int j) {

        // Base case: Either string is empty. We must delete all the characters
        // in the other string.
        if (i==0) return j;
        if (j==0) return i;

        // Check if the last characters match. If so, match them up.
        if (s.charAt(i-1) == t.charAt(j-1))
            return strmatch(s,t,i-1,j-1);

        // Otherwise, we have three options: delete either last character, or
        // switch one of the two characters.
        else
            return 1+min(strmatch(s,t,i,j-1),strmatch(s,t,i-1,j),strmatch(s,t,i-1,j-1));
    }

    // Returns the edit distance of strings s and t. Uses Dynamic Programming.
    public static int strmatchdyn(String s, String t) {

        int i,j;

        // DP table...
        int[][] table = new int[s.length()+1][t.length()+1];

        // Set up data corresponding to the base case in the table.
        for (i=0; i<table.length; i++)
            table[i][0] = i;
        for (i=0; i<table[0].length; i++)
            table[0][i] = i;

        // Fill in the table from the top left to the bottom right.
        for (i=1; i<table.length; i++) {
            for (j=1; j<table[0].length; j++) {

                // Check if the current characters match.
                if (s.charAt(i-1) == t.charAt(j-1))
                    table[i][j] = table[i-1][j-1];

                // Otherwise take the minimum of the three cases outlined in the
                // recursive case.
                else
                    table[i][j] = 1+min(table[i-1][j-1],table[i-1][j],table[i][j-1]);
            }
        }

        // This is the entry in the table we need to return.
        return table[s.length()][t.length()];
    }

    // Returns the minimum value of a, b and c.
    public static int min(int a, int b, int c) {
        if ((a<b) && (a<c)) return a;
        else if (b<c) return b;
        return c;
    }

}
