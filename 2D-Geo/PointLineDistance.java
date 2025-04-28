// Arup Guha
// 12/16/2014
// Implementation of some 2d geometry routines

// HOW TO USE
public class Test2DGeo {

	public static void main(String[] args) {

        pt2D a = new pt2D(0.0, 0.0);
        pt2D b = new pt2D(4.0, 4.0);

        line first = new line(a, b);

        // Test distance from point to line
        pt2D p = new pt2D(3.0, 0.0);
        double distance = first.distance(p);
        System.out.printf("The distance from point (%.3f, %.3f) to the first line is %.3f.\n", p.x, p.y, distance);
    }
}

class vect2D {

    public double x;
    public double y;

    public vect2D(double myx, double myy) {
        x = myx;
        y = myy;
    }

    public vect2D(pt2D start, pt2D end) {
        x = end.x - start.x;
        y = end.y - start.y;
    }

    public double mag() {
        return Math.sqrt(x*x+y*y);
    }

    public double crossMag(vect2D other) {
        return Math.abs(signedCrossMag(other));
    }
    
    public double signedCrossMag(vect2D other) {
        return this.x*other.y-other.x*this.y;
    }
}

class line {

    public pt2D p;
    public vect2D dir;

    public line(pt2D start, pt2D end) {
        p = start;
        dir = new vect2D(start, end);
    }

    // Returns the shortest distance from other to this line. Sets a vector from the starting
    // point of this line to other and uses the cross product with that vector and the direction
    // vector of the line.
    public double distance(pt2D other) {
        vect2D toPt = new vect2D(p, other);
        return dir.crossMag(toPt)/dir.mag();
    }
}

class pt2D {

    public double x;
    public double y;

    public pt2D(double myx, double myy) {
        x = myx;
        y = myy;
    }
}
