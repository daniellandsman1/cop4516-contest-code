// Arup Guha
// 12/16/2014
// Implementation of some 2d geometry routines

// HOW TO USE
public class Test2DGeo {

	public static void main(String[] args) {

        pt2D a = new pt2D(3.0, 7.0);
        pt2D b = new pt2D(-4.0, 6.0);
        pt2D c = new pt2D(10.0, 2.0);

        // Create two vectors
        vect2D v1 = new vect2D(a, b);
        vect2D v2 = new vect2D(a, c);

        // Print v1 and v2 components
        System.out.printf("Vector v1: (%.3f, %.3f)\n", v1.x, v1.y);
        System.out.printf("Vector v2: (%.3f, %.3f)\n", v2.x, v2.y);

        // Test magnitude of v1 and v2
        System.out.printf("Magnitude of v1: %.3f\n", v1.mag());
        System.out.printf("Magnitude of v2: %.3f\n", v2.mag());

        // Test dot product
        System.out.printf("Dot product of v1 and v2: %.3f\n", v1.dot(v2));

        // Test angle between v1 and v2
        System.out.printf("Angle between v1 and v2: %.3f radians\n", v1.angle(v2));
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

    // DOT PRODUCT
    public double dot(vect2D other) {
        return this.x*other.x + this.y*other.y;
    }

    // MAGNITUDE/LENGTH OF A VECTOR
    public double mag() {
        return Math.sqrt(x*x+y*y);
    }

    // ANGLE BETWEEN TWO VECTORS IN RADIANS FROM 0 TO PI
    public double angle(vect2D other) {
        return Math.acos(this.dot(other)/mag()/other.mag());
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
