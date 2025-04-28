// Arup Guha
// 12/16/2014
// Implementation of some 2d geometry routines

// HOW TO USE
public class Test2DGeo {

	public static void main(String[] args) {

        pt2D a = new pt2D(1.0, 2.0);
        pt2D b = new pt2D(4.0, 6.0);
        pt2D c = new pt2D(5.0, 1.0);

        // Create two vectors
        vect2D v1 = new vect2D(a, b);
        vect2D v2 = new vect2D(a, c);

        // Print v1 and v2 components
        System.out.printf("Vector v1: (%.3f, %.3f)\n", v1.x, v1.y);
        System.out.printf("Vector v2: (%.3f, %.3f)\n", v2.x, v2.y);

        // Test signed cross product (can be negative depending on order)
        System.out.printf("Signed cross product magnitude: %.3f\n", v1.signedCrossMag(v2));

        // Test absolute cross product (area of parallelogram)
        System.out.printf("Cross product magnitude (absolute): %.3f\n", v1.crossMag(v2));

        // Area of triangle formed by vectors
        double triangleArea = v1.crossMag(v2) / 2.0;
        System.out.printf("Area of triangle formed by v1 and v2: %.3f\n", triangleArea);
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

    public double signedCrossMag(vect2D other) {
        return this.x*other.y-other.x*this.y;
    }

    public double crossMag(vect2D other) {
        return Math.abs(signedCrossMag(other));
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
