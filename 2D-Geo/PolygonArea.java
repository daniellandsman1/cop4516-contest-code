// AREA OF A POLYGON USING TRIANGULATION

import java.util.*;

public class PolygonArea {

    public static void main(String[] args) {
       
        pt2D[] polygon = {
            new pt2D(0, 0),
            new pt2D(0, 5),
            new pt2D(5, 5),
            new pt2D(5, 0)
        };
        
        orderPoints(polygon);
        
        System.out.println("Area using Triangulation: " + areaUsingTriangulation(polygon));
    }

    // Method to calculate the area of a polygon using triangulation
    public static double areaUsingTriangulation(pt2D[] polygon) {
        int n = polygon.length;
        double area = 0.0;

        // Triangulation
        for (int i = 0; i < n; i++) {
            int j = (i + 1) % n;  // Next vertex
            area += polygon[i].x * polygon[j].y;
            area -= polygon[j].x * polygon[i].y;
        }

        area = Math.abs(area) / 2.0;
        return area;
    }

    // Class for representing a 2D point
    static class pt2D {
        public int x, y;

        public pt2D(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    // MIGHT NEED THESE TWO METHODS IF POINTS AREN'T GIVEN TO YOU ORDERED
    public static void orderPoints(pt2D[] points) {
        // Find the lowest-leftmost point
        pt2D reference = findLowestLeftmost(points);

        // Sort the points by polar angle with respect to the reference point
        Arrays.sort(points, (p1, p2) -> {
            double angle1 = Math.atan2(p1.y - reference.y, p1.x - reference.x);
            double angle2 = Math.atan2(p2.y - reference.y, p2.x - reference.x);
            return Double.compare(angle1, angle2);
        });
    }

    // Find the lowest-leftmost point
    public static pt2D findLowestLeftmost(pt2D[] points) {
        pt2D lowestLeftmost = points[0];
        for (pt2D p : points) {
            if (p.y < lowestLeftmost.y || (p.y == lowestLeftmost.y && p.x < lowestLeftmost.x)) {
                lowestLeftmost = p;
            }
        }
        return lowestLeftmost;
    }
}
