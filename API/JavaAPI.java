// HELPFUL JAVA API METHODS/DATA STRUCTURES
// PRIORITY QUEUE, SORTING WITH CUSTOM COMPARATOR
// TREEMAP, HASHMAP, HASHSET, FAST INPUT READING
// OTHERS

import java.util.*;
import java.io.*;

// Priority Queue (Min-Heap by default)
PriorityQueue<Integer> pq = new PriorityQueue<>();
pq.add(10);
pq.add(5);
pq.poll(); // removes and returns smallest element
pq.peek(); // looks at smallest element without removing

// Priority Queue (Max-Heap)
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
maxHeap.add(10);
maxHeap.add(5);

// Priority Queue with Custom Comparator
PriorityQueue<int[]> pqCustom = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));

// TreeMap (sorted by key)
TreeMap<Integer, String> treeMap = new TreeMap<>();
treeMap.put(3, "Three");
treeMap.put(1, "One");
treeMap.firstKey(); // 1
treeMap.lastKey();  // 3
treeMap.lowerKey(2); // key < 2
treeMap.higherKey(2); // key > 2

// HashMap
HashMap<Integer, String> hashMap = new HashMap<>();
hashMap.put(1, "One");
hashMap.getOrDefault(2, "Default"); // "Default"
hashMap.containsKey(1); // true

// HashSet
HashSet<Integer> hashSet = new HashSet<>();
hashSet.add(5);
hashSet.contains(5); // true
hashSet.remove(5);

// Custom Sorting with Comparator (Arrays)
int[][] arr = {{2, 3}, {1, 2}, {4, 1}};
Arrays.sort(arr, (a, b) -> Integer.compare(a[0], b[0])); // Sort by first element
Arrays.sort(arr, (a, b) -> Integer.compare(a[1], b[1])); // Sort by second element

// Custom Sorting with Comparator (ArrayList)
List<int[]> list = new ArrayList<>();
Collections.sort(list, (a, b) -> Integer.compare(a[0], b[0]));

// Fast Input Reader
class FastReader {
    BufferedReader br;
    StringTokenizer st;
    
    public FastReader() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }
    
    String next() {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }
    
    int nextInt() { return Integer.parseInt(next()); }
    long nextLong() { return Long.parseLong(next()); }
    double nextDouble() { return Double.parseDouble(next()); }
    String nextLine() {
        String str = "";
        try {
            str = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}

// StringBuilder for Fast Output
StringBuilder sb = new StringBuilder();
sb.append("some string").append("\n");
System.out.println(sb);

// Math Utilities
Math.min(a, b);
Math.max(a, b);
Math.abs(x);
Math.pow(a, b);
Math.sqrt(x);
Math.floor(x);
Math.ceil(x);

// Arrays Utilities
Arrays.sort(arr);
Arrays.fill(arr, value);

// Array reverse sorting
Integer[] arr = {5, 2, 8, 1};
Arrays.sort(arr, Collections.reverseOrder());
// arr becomes [8, 5, 2, 1]

// Array reverse sorting with primitives
int[] arr2 = {5, 2, 8, 1};
Integer[] boxed = Arrays.stream(arr2).boxed().toArray(Integer[]::new);
Arrays.sort(boxed, Collections.reverseOrder());
arr2 = Arrays.stream(boxed).mapToInt(Integer::intValue).toArray();

// Collections Utilities
Collections.sort(list);
Collections.reverse(list);
Collections.binarySearch(list, key);
Collections.min(list);
Collections.max(list);

// Graph Useful Structures
List<List<Integer>> graph = new ArrayList<>();
for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
graph.get(u).add(v);

// Pair class (easy custom class)
class Pair {
    int first, second;
    Pair(int first, int second) {
        this.first = first;
        this.second = second;
    }
}
