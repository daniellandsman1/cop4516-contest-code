
import java.util.*;

class  Edge{
    int src;
    int dest;
    int weight;

    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
}

class Subset {
    int parent;
    int rank;

    public Subset(int parent, int rank) {
        this.parent = parent;
        this.rank = rank;
    }
}

class Graph {
    List<Edge> graph = new ArrayList<Edge>();

    public void add(int src, int dest, int weight) {
        graph.add(new Edge(src, dest, weight));
    }

    private Map<Integer, List<Integer>> buildAdjList() {
        Map<Integer, List<Integer>> adj = new HashMap<>();
        for (Edge edge : graph) {
            adj.computeIfAbsent(edge.src, k -> new ArrayList<>()).add(edge.dest);
            // If undirected graph, also add reverse
            adj.computeIfAbsent(edge.dest, k -> new ArrayList<>()).add(edge.src);
        }
        return adj;
    }

    public void dfs(int start) {
        Map<Integer, List<Integer>> adj = buildAdjList();
        Set<Integer> visited = new HashSet<>();
        System.out.print("DFS traversal starting from node " + start + ": ");
        dfsHelper(start, adj, visited);
        System.out.println();
    }

    private void dfsHelper(int node, Map<Integer, List<Integer>> adj, Set<Integer> visited) {
        visited.add(node);
        System.out.print(node + " ");

        if (adj.get(node) != null) {
            for (int neighbor : adj.get(node)) {
                if (!visited.contains(neighbor)) {
                    dfsHelper(neighbor, adj, visited);
                }
            }
        }
    }

    public void bfs(int start) {
        Map<Integer, List<Integer>> adj = buildAdjList();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        visited.add(start);
        queue.offer(start);

        System.out.print("BFS traversal starting from node " + start + ": ");
        while (!queue.isEmpty()) {
            int node = queue.poll();
            System.out.print(node + " ");

            if (adj.get(node) != null) {
                for (int neighbor : adj.get(node)) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.offer(neighbor);
                    }
                }
            }
        }
        System.out.println();
    }

    public void kruskals(int V) {

        graph.sort(new Comparator<Edge>() {
            public int compare(Edge e1, Edge e2) {
                return e1.weight - e2.weight;
            }
        });

        int j = 0;
        int n = 0;
        Subset subsets[] = new Subset[V];
        Edge result[] = new Edge[V];

        for(int i = 0; i < V; i++) {
            subsets[i] = new Subset(i, 0);
        }

        while(n < V-1) {
            Edge e = graph.get(j);
            int x = findRoot(subsets, e.src);
            int y = findRoot(subsets, e.dest);

            if(x!=y) {
                result[n] = e;
                union(subsets, x, y);
                n++;
            }

            j++;
        }

        int minCost = 0;
        for (int i = 0; i < n; i++) {
            System.out.println(result[i].src + " -- " + result[i].dest + " == " + result[i].weight);
            minCost += result[i].weight;
        }

        System.out.println(minCost);
    }

    private void union(Subset[] subsets, int x,int y) {
        int rootX = findRoot(subsets, x);
        int rootY = findRoot(subsets, y);

        if(subsets[rootY].rank < subsets[rootX].rank) {
        subsets[rootY].parent = rootX;
        } else if(subsets[rootX].rank < subsets[rootY].rank) {
        subsets[rootX].parent = rootY;
        } else {
        subsets[rootY].parent = rootX;
        subsets[rootX].rank++;
        }
    }


    private int findRoot(Subset[] subsets, int i) {
        if (subsets[i].parent == i)
        return subsets[i].parent;
        subsets[i].parent = findRoot(subsets, subsets[i].parent);
        return subsets[i].parent;
    }

    public void prims(int V) {
        List<Edge> mst = new ArrayList<>();
        boolean[] visited = new boolean[V];
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));


        visited[0] = true;
        for (Edge edge : graph) {
            if (edge.src == 0 || edge.dest == 0) {
                pq.add(edge);
            }
        }

        while (!pq.isEmpty() && mst.size() < V - 1) {
            Edge edge = pq.poll();

            int u = edge.src;
            int v = edge.dest;
            
            if (visited[u] && visited[v]) {
                continue;
            }

            mst.add(edge);
            int newVertex = visited[u] ? v : u;
            visited[newVertex] = true;

            for (Edge e : graph) {
                if ((e.src == newVertex || e.dest == newVertex) && !visited[e.src] || !visited[e.dest]) {
                    pq.add(e);
                }
            }
        }
        
        int minCost = 0;
        for(Edge e : mst) {
            System.out.println(e.src + " - " + e.dest + " : " + e.weight);
            minCost+=e.weight;
        }

        System.out.println(minCost);
    }

    public void floyd(int vertices) {
        int INF = Integer.MAX_VALUE;
        int[][] dist = new int[vertices][vertices];
        int[][] next = new int[vertices][vertices]; 

        
        for (int i = 0; i < vertices; i++) {
            Arrays.fill(dist[i], INF);
            Arrays.fill(next[i], -1);
            dist[i][i] = 0;  
        }

       
        for (Edge edge : graph) {
            dist[edge.src][edge.dest] = edge.weight;
            next[edge.src][edge.dest] = edge.dest;
        }

        for (int k = 0; k < vertices; k++) { 
            for (int i = 0; i < vertices; i++) { 
                for (int j = 0; j < vertices; j++) { 
                    if (dist[i][k] != INF && dist[k][j] != INF && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k]; 
                    }
                }
            }
        }

        printSolution(dist, next, vertices);
    }

    private void printSolution(int[][] dist, int[][] next, int vertices) {
        System.out.println("Shortest distances between all pairs of vertices:");
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                System.out.print((dist[i][j] == Integer.MAX_VALUE ? "INF" : dist[i][j]) + "\t");
            }
            System.out.println();
        }

        System.out.println("\nShortest Paths:");
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                if (i != j && next[i][j] != -1) {
                    System.out.print("Path from " + i + " to " + j + ": ");
                    printPath(i, j, next);
                    System.out.println();
                }
            }
        }
    }

    private void printPath(int u, int v, int[][] next) {
        if (next[u][v] == -1) {
            System.out.print("No path");
            return;
        }
        System.out.print(u);
        while (u != v) {
            u = next[u][v];
            System.out.print(" -> " + u);
        }
    }

    class Node implements Comparable<Node> {
        int vertex, cost;
        public Node(int vertex, int cost) {
            this.vertex = vertex;
            this.cost = cost;
        }
        public int compareTo(Node other) {
            return Integer.compare(this.cost, other.cost);
        }
    }

    public void dijkstra(int vertices, int start) {
        
        List<List<Edge>> graphDij = new ArrayList<>();
        for (int i = 0; i < vertices; i++) graphDij.add(new ArrayList<>());

        for (Edge edge : graph) {
            graphDij.get(edge.src).add(edge);
        }

        int[] dist = new int[vertices];
        int[] parent = new int[vertices];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        dist[start] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.vertex;

            for (Edge edge : graphDij.get(u)) {
                int v = edge.dest, weight = edge.weight;
                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    parent[v] = u;
                    pq.add(new Node(v, dist[v]));
                }
            }
        }

        System.out.println("Shortest distances from source " + start + ":");
        for (int i = 0; i < vertices; i++) {
            System.out.print("To " + i + " -> " + (dist[i] == Integer.MAX_VALUE ? "INF" : dist[i]));
            System.out.print(" | Path: ");
            printPathDij(i, parent);
            System.out.println();
        }
    }

    private static void printPathDij(int vertex, int[] parent) {
        if (parent[vertex] == -1) {
            System.out.print("[" + vertex + "]");
            return;
        }
        List<Integer> path = new ArrayList<>();
        while (vertex != -1) {
            path.add(vertex);
            vertex = parent[vertex];
        }
        Collections.reverse(path);
        System.out.print(path);
    }

    public void bellmanFord(int vertices, int start) {
        int[] dist = new int[vertices];  
        int[] parent = new int[vertices]; 
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        dist[start] = 0;

        for (int i = 0; i < vertices - 1; i++) {
            for (Edge edge : graph) {
                if (dist[edge.src] != Integer.MAX_VALUE && dist[edge.src] + edge.weight < dist[edge.dest]) {
                    dist[edge.dest] = dist[edge.src] + edge.weight;
                    parent[edge.dest] = edge.src; 
                }
            }
        }

        for (Edge edge : graph) {
            if (dist[edge.src] != Integer.MAX_VALUE && dist[edge.src] + edge.weight < dist[edge.dest]) {
                System.out.println("Graph contains a negative weight cycle!");
                return;
            }
        }

        System.out.println("Shortest distances from source " + start + ":");
        for (int i = 0; i < vertices; i++) {
            System.out.print("To " + i + " -> " + (dist[i] == Integer.MAX_VALUE ? "INF" : dist[i]));
            System.out.print(" | Path: ");
            printPath(i, parent);
            System.out.println();
        }
    }

    private void printPath(int vertex, int[] parent) {
        if (vertex == -1) {
            return;
        }
        List<Integer> path = new ArrayList<>();
        while (vertex != -1) {
            path.add(vertex);
            vertex = parent[vertex];
        }
        Collections.reverse(path);
        System.out.print(path);
    }

    public static int n;
	public static ArrayList[] adj;
	public static int[] inDegree;

    public static int[] getFirstTopSort() {
        
		int[] res = new int[n];

		PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
		for (int i=0; i<n; i++)
			if (inDegree[i] == 0)
				pq.add(i);

		for (int i=0; i<n; i++) {

			if (pq.size() == 0) return null;

			res[i] = pq.poll();

			for (Integer next : (ArrayList<Integer>)adj[res[i]]) {
				inDegree[next]--;

				if (inDegree[next] == 0)
					pq.offer(next);
			}
		}

		return res;
	}

}

public class graphTest {
    public static void main(String[] args) {
        Graph g = new Graph();

        g.add(0, 1, 1);
        g.add(0, 2, 1);
        g.add(1, 3, 1);
        g.add(2, 3, 1);
        

        //g.kruskals(4);

        g.dfs(0);  // Example DFS from node 0
        g.bfs(0);  // Example BFS from node 0

        //g.floyd(4);

        //g.dijkstra(7, 0);
    }
}
