// SOME OTHER POSSIBLY USEFUL TREE ALGOS
// LIKE TREE DIAMETER, HEIGHT, LOWEST COMMON ANCESTOR

import java.util.*;

public class MasterTree {
    static ArrayList<ArrayList<Integer>> tree;
    static int[] depth;
    static int[] parent;
    static int[] subtreeSize;
    static int[][] up;
    static int LOG;
    static int N;

    // Compute depth, parent, and subtree size
    static void dfs(int node, int par, int d) {
        parent[node] = par;
        depth[node] = d;
        subtreeSize[node] = 1;
        for (int neighbor : tree.get(node)) {
            if (neighbor != par) {
                dfs(neighbor, node, d + 1);
                subtreeSize[node] += subtreeSize[neighbor];
            }
        }
    }

    // Preprocess up table for binary lifting (LCA)
    static void preprocessLCA() {
        LOG = 1;
        while ((1 << LOG) <= N) LOG++;
        up = new int[N][LOG];
        for (int i = 0; i < N; i++) {
            Arrays.fill(up[i], -1);
        }
        for (int i = 0; i < N; i++) {
            up[i][0] = parent[i];
        }
        for (int j = 1; j < LOG; j++) {
            for (int i = 0; i < N; i++) {
                if (up[i][j-1] != -1) {
                    up[i][j] = up[up[i][j-1]][j-1];
                }
            }
        }
    }

    // Lifts node u up by k steps
    static int lift(int u, int k) {
        for (int i = 0; i < LOG; i++) {
            if (((k >> i) & 1) != 0) {
                u = up[u][i];
                if (u == -1) break;
            }
        }
        return u;
    }

    // Find LCA of u and v
    static int lca(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u; u = v; v = temp;
        }
        u = lift(u, depth[u] - depth[v]);
        if (u == v) return u;
        for (int i = LOG-1; i >= 0; i--) {
            if (up[u][i] != -1 && up[u][i] != up[v][i]) {
                u = up[u][i];
                v = up[v][i];
            }
        }
        return parent[u];
    }

    // Find height of tree (max depth)
    static int findHeight(int root) {
        dfs(root, -1, 0);
        int maxDepth = 0;
        for (int d : depth) {
            maxDepth = Math.max(maxDepth, d);
        }
        return maxDepth;
    }

    // BFS to find farthest node from given start
    static int bfsFarthestNode(int start) {
        int[] dist = new int[N];
        Arrays.fill(dist, -1);
        Queue<Integer> q = new LinkedList<>();
        q.add(start);
        dist[start] = 0;

        while (!q.isEmpty()) {
            int curr = q.poll();
            for (int neighbor : tree.get(curr)) {
                if (dist[neighbor] == -1) {
                    dist[neighbor] = dist[curr] + 1;
                    q.add(neighbor);
                }
            }
        }

        int farthest = start;
        for (int i = 0; i < N; i++) {
            if (dist[i] > dist[farthest]) {
                farthest = i;
            }
        }
        return farthest;
    }

    // Find diameter of the tree
    static int findDiameter() {
        int u = bfsFarthestNode(0);
        int v = bfsFarthestNode(u);
        int[] dist = new int[N];
        Arrays.fill(dist, -1);
        Queue<Integer> q = new LinkedList<>();
        q.add(u);
        dist[u] = 0;
        while (!q.isEmpty()) {
            int curr = q.poll();
            for (int neighbor : tree.get(curr)) {
                if (dist[neighbor] == -1) {
                    dist[neighbor] = dist[curr] + 1;
                    q.add(neighbor);
                }
            }
        }
        return dist[v];
    }

    // Simple DFS traversal
    static void dfsPrint(int node, int par) {
        System.out.print(node + " ");
        for (int neighbor : tree.get(node)) {
            if (neighbor != par) {
                dfsPrint(neighbor, node);
            }
        }
    }

    // Simple BFS traversal
    static void bfsPrint(int start) {
        boolean[] visited = new boolean[N];
        Queue<Integer> q = new LinkedList<>();
        q.add(start);
        visited[start] = true;
        while (!q.isEmpty()) {
            int curr = q.poll();
            System.out.print(curr + " ");
            for (int neighbor : tree.get(curr)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    q.add(neighbor);
                }
            }
        }
    }

    public static void main(String[] args) {
        // Example Tree
        //
        //        0
        //       / \
        //      1   2
        //     / \
        //    3   4
        //
        N = 5;
        tree = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            tree.add(new ArrayList<>());
        }

        // Add edges
        tree.get(0).add(1);
        tree.get(1).add(0);

        tree.get(0).add(2);
        tree.get(2).add(0);

        tree.get(1).add(3);
        tree.get(3).add(1);

        tree.get(1).add(4);
        tree.get(4).add(1);

        depth = new int[N];
        parent = new int[N];
        subtreeSize = new int[N];

        System.out.println("DFS traversal:");
        dfsPrint(0, -1);
        System.out.println();

        System.out.println("BFS traversal:");
        bfsPrint(0);
        System.out.println();

        int height = findHeight(0);
        System.out.println("Height of the tree: " + height);

        System.out.println("Parent array: " + Arrays.toString(parent));
        System.out.println("Depth array: " + Arrays.toString(depth));

        int diameter = findDiameter();
        System.out.println("Diameter of the tree: " + diameter);

        System.out.println("Subtree sizes:");
        for (int i = 0; i < N; i++) {
            System.out.println("Subtree rooted at " + i + ": " + subtreeSize[i]);
        }

        preprocessLCA();
        System.out.println("LCA of 3 and 4: " + lca(3, 4)); // expect 1
        System.out.println("LCA of 2 and 3: " + lca(2, 3)); // expect 0
    }
}
