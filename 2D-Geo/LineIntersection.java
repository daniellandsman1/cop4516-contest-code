// Arup Guha
// 12/16/2014
// Implementation of some 2d geometry routines

// HOW TO USE
public class Test2DGeo {

	public static void main(String[] args) {

        pt2D a = new pt2D(0.0, 0.0);
        pt2D b = new pt2D(4.0, 4.0);
        pt2D c = new pt2D(0.0, 4.0);
        pt2D d = new pt2D(4.0, 0.0);

        line first = new line(a, b);
        line second = new line(c, d);

        // Test intersection
        pt2D meet = first.intersect(second);
        if (meet != null) {
            System.out.printf("The two lines meet at (%.3f, %.3f).\n", meet.x, meet.y);
        } else {
            System.out.println("The two lines do not meet (are parallel).");
        }

        // Test parallel case
        pt2D e = new pt2D(1.0, 1.0);
        pt2D f = new pt2D(5.0, 5.0);
        line third = new line(e, f);
        pt2D parallelMeet = first.intersect(third);
        if (parallelMeet != null) {
            System.out.printf("Unexpected intersection at (%.3f, %.3f).\n", parallelMeet.x, parallelMeet.y);
        } else {
            System.out.println("Lines are parallel; no intersection.");
        }
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
}

class line {

    final public static double EPSILON = 1e-9;

    public pt2D p;
    public vect2D dir;

    public line(pt2D start, pt2D end) {
        p = start;
        dir = new vect2D(start, end);
    }

    public pt2D intersect(line other) {

        // This is the denominator we get when setting up our system of equations for
        // our two parametric line parameters.
        double den = det(dir.x, -other.dir.x, dir.y, -other.dir.y);
        if (Math.abs(den) < EPSILON) return null;

        // We already have the denominator, now solve for the numerator for lambda, the
        // parameter for this line. Then return the resultant point.
        double numLambda = det(other.p.x-p.x, -other.dir.x, other.p.y-p.y, -other.dir.y);
        return eval(numLambda/den);
    }

    // Returns the point on this line corresponding to parameter lambda.
    public pt2D eval(double lambda) {
        return new pt2D(p.x+lambda*dir.x, p.y+lambda*dir.y);
    }

    public static double det(double a, double b, double c, double d) {
        return a*d - b*c;
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
