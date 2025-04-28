// A CIRCLE AND A LINE CAN INTERSECT AT ONE POINT, TWO POINTS, OR ZERO POINTS

// HOW TO USE
public class Test2DGeo {

    public static void main(String[] args) {

        // Define a circle and a line
        circle c = new circle(new pt2D(0.0, 0.0), 5.0);
        line l = new line(new pt2D(-6.0, 0.0), new pt2D(6.0, 0.0));

        // Find intersection points
        pt2D[] points = c.intersect(l);

        if (points == null) {
            System.out.println("The circle and line do not intersect.");
        } else if (points.length == 1) {
            System.out.printf("The line is tangent to the circle at %s.\n", points[0]);
        } else {
            System.out.printf("The circle and line meet at %s and %s.\n", points[0], points[1]);
        }
    }
}

class pt2D {

    public double x;
    public double y;

    public pt2D(double myx, double myy) {
        x = myx;
        y = myy;
    }

    @Override
    public String toString() {
        return String.format("(%.3f, %.3f)", x, y);
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
        return Math.sqrt(x * x + y * y);
    }

    public double crossMag(vect2D other) {
        return Math.abs(x * other.y - y * other.x);
    }
}

class line {

    public pt2D p;
    public vect2D dir;
    final public static double EPSILON = 1e-9;

    public line(pt2D start, pt2D end) {
        p = start;
        dir = new vect2D(start, end);
    }

    // Returns the point on the line at parameter lambda
    public pt2D eval(double lambda) {
        return new pt2D(p.x + lambda * dir.x, p.y + lambda * dir.y);
    }
}

class circle {

    public pt2D center;
    public double radius;
    final public static double EPSILON = 1e-9;

    public circle(pt2D c, double r) {
        center = c;
        radius = r;
    }

    // Returns the intersection points between this circle and a line
    public pt2D[] intersect(line l) {

        // Set up vector from circle center to line start point
        vect2D f = new vect2D(center, l.p);

        double a = l.dir.x * l.dir.x + l.dir.y * l.dir.y;
        double b = 2 * (l.dir.x * f.x + l.dir.y * f.y);
        double c = f.x * f.x + f.y * f.y - radius * radius;

        double discriminant = b * b - 4 * a * c;

        // No intersection
        if (discriminant < -EPSILON) {
            return null;
        }

        // Tangent (one intersection)
        if (Math.abs(discriminant) < EPSILON) {
            double lambda = -b / (2 * a);
            return new pt2D[]{ l.eval(lambda) };
        }

        // Two points of intersection
        double sqrtDisc = Math.sqrt(discriminant);
        double lambda1 = (-b + sqrtDisc) / (2 * a);
        double lambda2 = (-b - sqrtDisc) / (2 * a);

        pt2D p1 = l.eval(lambda1);
        pt2D p2 = l.eval(lambda2);

        return new pt2D[]{ p1, p2 };
    }
}
