public class MakeChange {
    
    // Function to find the minimum number of coins
    public static int findMin(int n, int[] denomination) {
        // Traverse through all denominations in reverse order
        int count =0; 
        for (int i = denomination.length - 1; i >= 0; i--) {
            // Find denominations
            while (n >= denomination[i]) {
                n -= denomination[i];
                count ++;
            }
        }
        return count; 
    }

    public static void main(String[] args) {
        int[] denomination = {1, 2, 5, 10};
        int n = 39;
        System.out.print(findMin(n, denomination));
    }
}