// Danny Wasserman
// 7/14/2014
// Implementation of Dijkstra's Algorithm with a Priority Queue.

import java.util.ArrayList;
import java.util.PriorityQueue;

public class dijkstras {

  // Everyone has access to these.
  static int oo = (int) 1e9;
  static int n;
  static ArrayList<Edge>[] g;

  // Returns the shortest distance from vertex s to d.
  public static int dijkstras(int s, int d) {

    // Set up the priority queue.
    boolean[] visited = new boolean[n];
    PriorityQueue<Edge> pq = new PriorityQueue<Edge>();
    pq.add(new Edge(s, 0));

    // Go till empty.
    while (!pq.isEmpty()) {

      // Get the next edge.
      Edge at = pq.poll();
      if (visited[at.e]) continue;
      visited[at.e] = true;

      // We made it, return the distance.
      if (at.e == d) return at.w;

      // Enqueue all the neighboring edges.
      for (Edge adj : g[at.e])
        if (!visited[adj.e]) pq.add(new Edge(adj.e, adj.w + at.w));
    }
    return oo;
  }

  // Stores where an edge is going to and its weight.
  static class Edge implements Comparable<Edge> {
    int e, w;

    public Edge(int e, int w) {
      this.e = e;
      this.w = w;
    }

    public int compareTo(Edge o) {
      return w - o.w;
    }
  }
}dhijkstras{

}
