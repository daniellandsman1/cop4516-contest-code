// MasterMathAlgorithms.java
// Collection of useful math algorithms for contests.

import java.math.BigInteger;
import java.util.*;

public class MasterMathAlgorithms {

    public static void main(String[] args) {
        // BigInteger usage example
        BigInteger a = new BigInteger("123456789123456789");
        BigInteger b = new BigInteger("987654321987654321");

        BigInteger sum = a.add(b);
        BigInteger product = a.multiply(b);
        BigInteger gcd = a.gcd(b);

        System.out.println("BigInteger Sum: " + sum);
        System.out.println("BigInteger Product: " + product);
        System.out.println("BigInteger GCD: " + gcd);

        // LCM example
        int x = 12, y = 18;
        System.out.println("LCM of " + x + " and " + y + ": " + lcm(x, y));

        // Euler's Totient example
        int n = 36;
        System.out.println("Euler's Totient φ(" + n + "): " + phi(n));

        // Modular Inverse example
        int aInv = 3, mod = 11;
        System.out.println("Modular Inverse of " + aInv + " mod " + mod + ": " + modInverse(aInv, mod));

        // Fast Modular Exponentiation example
        int base = 3, exponent = 13, modulus = 7;
        System.out.println(base + "^" + exponent + " mod " + modulus + ": " + fastExp(base, exponent, modulus));

        // Sum of Divisors example
        int num = 28;
        System.out.println("Sum of divisors of " + num + ": " + sumOfDivisors(num));
        
        int numD = 28;
        System.out.println("Number of divisors of " + numD + ": " + numberOfDivisors(numD));
    }

    // Least Common Multiple (LCM) using GCD
    public static int lcm(int a, int b) {
        return a / gcd(a, b) * b;
    }

    public static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    // Euler's Totient Function (counts integers relatively prime to n)
    public static int phi(int n) {
        int result = n;
        for (int p = 2; p * p <= n; ++p) {
            if (n % p == 0) {
                while (n % p == 0)
                    n /= p;
                result -= result / p;
            }
        }
        if (n > 1)
            result -= result / n;
        return result;
    }

    // Modular Inverse (Assumes a and mod are coprime)
    public static int modInverse(int a, int mod) {
        return fastExp(a, mod - 2, mod);
    }

    // Fast Modular Exponentiation (a^b mod m)
    public static int fastExp(int base, int exp, int mod) {
        int result = 1;
        base = base % mod;
        while (exp > 0) {
            if ((exp & 1) == 1)
                result = (int)((long)result * base % mod);
            base = (int)((long)base * base % mod);
            exp >>= 1;
        }
        return result;
    }

    // Sum of Divisors
    public static int sumOfDivisors(int n) {
        int sum = 0;
        for (int i = 1; i * i <= n; i++) {
            if (n % i == 0) {
                sum += i;
                if (i != n / i)
                    sum += n / i;
            }
        }
        return sum;
    }
    
 // Number of Divisors
    public static int numberOfDivisors(int n) {
        int count = 0;
        for (int i = 1; i * i <= n; i++) {
            if (n % i == 0) {
                count++; // i is a divisor
                if (i != n / i)
                    count++; // n/i is a different divisor
            }
        }
        return count;
    }

}
