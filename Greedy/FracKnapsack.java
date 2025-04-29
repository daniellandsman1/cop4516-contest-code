import java.util.*;

class FracKnapsack {

    // Comparison function to sort items based on value/weight ratio
    static class ItemComparator implements Comparator<int[]> {
        public int compare(int[] a, int[] b) {
            double a1 = (1.0 * a[0]) / a[1];
            double b1 = (1.0 * b[0]) / b[1];
            return Double.compare(b1, a1);
        }
    }

    static double fractionalKnapsack(int[] val, int[] wt, int capacity) {
        int n = val.length;

        // Create 2D array to store value and weight
        // items[i][0] = value, items[i][1] = weight
        int[][] items = new int[n][2];

        for (int i = 0; i < n; i++) {
            items[i][0] = val[i];
            items[i][1] = wt[i];
        }

        // Sort items based on value-to-weight ratio in descending order
        Arrays.sort(items, new ItemComparator());

        double res = 0.0;
        int currentCapacity = capacity;

        // Process items in sorted order
        for (int i = 0; i < n; i++) {

            // If we can take the entire item
            if (items[i][1] <= currentCapacity) {
                res += items[i][0];
                currentCapacity -= items[i][1];
            }

            // Otherwise take a fraction of the item
            else {
                res += (1.0 * items[i][0] / items[i][1]) * currentCapacity;

                // Knapsack is full
                break;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int[] val = {60, 100, 120};
        int[] wt = {10, 20, 30};
        int capacity = 50;

        System.out.println(fractionalKnapsack(val, wt, capacity));
    }
}