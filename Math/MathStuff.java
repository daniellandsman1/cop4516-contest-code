// Arup Guha
// 2/16/2016
// Some Math Code for COP 4516

import java.util.*;

public class MathStuff {

    public static void main(String[] args) {

        // Test GCD.
        Scanner stdin = new Scanner(System.in);
        System.out.println("Enter two non-negative integers of which to find the GCD.");
        int a = stdin.nextInt();
        int b = stdin.nextInt();
        System.out.println("The GCD is "+gcd(a,b));

        // Test Prime Factorization.
        System.out.println("Enter an integer to prime factorize.");
        int n = stdin.nextInt();
        ArrayList<pair> list = primeFactorize(n);
        System.out.print("Your prime factorization is ");
        for (int i=0; i<list.size()-1; i++)
            System.out.print(list.get(i).prime+"^"+list.get(i).exp+" * ");
        System.out.println(list.get(list.size()-1).prime+"^"+list.get(list.size()-1).exp);

        // Test Prime Sieve.
        System.out.println("Enter a maximum bound for your prime search.");
        n = stdin.nextInt();
        boolean[] sieve = primeSieve(n);
        System.out.print("Here are the primes up to n:");
        for (int i=0; i<sieve.length; i++)
            if (sieve[i])
                System.out.print(i+" ");
        System.out.println();

        // Test how many times p divides into n!
        System.out.println("Enter a positive integer n and a prime p.");
        n = stdin.nextInt();
        int p = stdin.nextInt();
        System.out.println(p+" divides "+n+"! "+numTimesDivide(n, p)+" times.");

    }

    // Returns the GCD of a and b.
    public static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a%b);
    }

    public static ArrayList<pair> primeFactorize(int n) {

        // Store the result here.
        ArrayList<pair> res = new ArrayList<pair>();

        int div = 2;

        // Go till we know we're left with a prime.
        while (div*div <= n) {

            // See how many times div divides into n.
            int exp = 0;
            while (n%div == 0) {
                n /= div;
                exp++;
            }

            // Add it, if it's a divisor.
            if (exp > 0) res.add(new pair(div, exp));
            div++;
        }

        // See if we have one last term to add before returning.
        if (n > 1) res.add(new pair(n, 1));
        return res;
    }

    // Returns an array of size n+1 such that array[i] = true iff i is prime.
    public static boolean[] primeSieve(int n) {

        // Initialize.
        boolean[] isPrime = new boolean[n+1];
        Arrays.fill(isPrime, true);
        isPrime[0]= false;
        isPrime[1] = false;

        // Run really basic sieve.
        for (int i=2; i*i<=n; i++)
            for (int j=2*i; j<=n; j+=i)
                isPrime[j] = false;

        // Here is our array.
        return isPrime;
    }

    // Pre-condition: p is prime, n > 0.
    // Post-condition: returns the number of times p divides into n!
    public static int numTimesDivide(int n, int p) {
        int res = 0;
        while (n > 0) {
            res += (n/p);
            n /= p;
        }
        return res;
    }

}

// Just for prime factorization.
class pair {
    public int prime;
    public int exp;

    public pair(int p, int e) {
        prime = p;
        exp = e;
    }
}
