import java.util.*;

//CALCULATES THE PERIMETER OF THE CONVEX HULL

// HOW TO USE
public class ConvexHullWithPerimeterAndArea {

    public static void main(String[] args) {

    	pt[] pts = {
            new pt(0, 0),
            new pt(4, 0),
            new pt(2, 4)
        };

        // Set the reference point.
        int refIndex = getIndexMin(pts, pts.length);
        pt.refX = pts[refIndex].x;
        pt.refY = pts[refIndex].y;

        // Output perimeter
        double perimeter = grahamScan(pts, pts.length);
        System.out.printf("Perimeter: %.7f\n", perimeter);
    }

    // Returns the point in pts with minimum y, breaking tie by minimum x.
    public static int getIndexMin(pt[] pts, int n) {
        int res = 0;
        for (int i = 1; i < n; i++) {
            if (pts[i].y < pts[res].y || (pts[i].y == pts[res].y && pts[i].x < pts[res].x)) {
                res = i;
            }
        }
        return res;
    }

    // Graham scan to compute the convex hull and perimeter
    public static double grahamScan(pt[] pts, int n) {
        // Sort the points by polar angle with respect to the reference point.
        Arrays.sort(pts);

        // Stack to hold the convex hull
        Stack<pt> myStack = new Stack<>();
        myStack.push(pts[0]);
        myStack.push(pts[1]);

        // Iterate through the remaining points.
        for (int i = 2; i < n; i++) {
            pt cur = pts[i];
            pt mid = myStack.pop();
            pt prev = myStack.pop();

            // Pop off left turns.
            while (!prev.isRightTurn(mid, cur)) {
                mid = prev;
                prev = myStack.pop();
            }

            // Push back the last right turn
            myStack.push(prev);
            myStack.push(mid);
            myStack.push(cur);
        }

        // Add up distances around the hull, closing the loop
        double perimeter = 0.0;
        pt cur = myStack.pop();
        pt first = cur; // Save the first point to close the loop

        while (!myStack.isEmpty()) {
            pt next = myStack.pop();
            perimeter += cur.dist(next);
            cur = next;
        }

        // Add the distance from the last point back to the first point to close the polygon
        perimeter += cur.dist(first);

        return perimeter;
    }
}

class pt implements Comparable<pt> {

    public static int refX;
    public static int refY;

    public int x;
    public int y;

    public pt(int myx, int myy) {
        x = myx;
        y = myy;
    }

    // Returns the vector from this point to another
    public pt getVect(pt other) {
        return new pt(other.x - x, other.y - y);
    }

    // Returns the distance between this point and another
    public double dist(pt other) {
        return Math.sqrt((other.x - x) * (other.x - x) + (other.y - y) * (other.y - y));
    }

    // Returns the magnitude of the cross product between two vectors
    public int crossProductMag(pt other) {
        return this.x * other.y - other.x * this.y;
    }

    // Returns true if turning right (i.e., checking if the point is a right turn)
    public boolean isRightTurn(pt mid, pt next) {
        pt v1 = getVect(mid);
        pt v2 = mid.getVect(next);
        return v1.crossProductMag(v2) >= 0; // Change to > 0 to skip collinear points.
    }

    // Compare points based on polar angle with respect to the reference point
    public int compareTo(pt other) {
        pt myRef = new pt(refX, refY);
        pt v1 = myRef.getVect(this);
        pt v2 = myRef.getVect(other);

        if (v1.isZero()) return -1;
        if (v2.isZero()) return 1;

        if (v1.crossProductMag(v2) != 0)
            return -v1.crossProductMag(v2);

        if (myRef.dist(v1) < myRef.dist(v2)) return -1;
        return 1;
    }

    // Returns true if the point is the origin
    public boolean isZero() {
        return x == 0 && y == 0;
    }
}
