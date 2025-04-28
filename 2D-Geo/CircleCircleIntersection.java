// TWO CIRCLES CAN INTERSECT AT ONE POINT, TWO POINTS, ZERO POINTS
// (or technically infinite points if they're the same circle)

// HOW TO USE
public class Test2DGeo {

    public static void main(String[] args) {

        // Define two circles
        circle c1 = new circle(new pt2D(0.0, 0.0), 5.0);
        circle c2 = new circle(new pt2D(6.0, 0.0), 5.0);

        // Find intersection points
        pt2D[] points = c1.intersect(c2);

        if (points == null) {
            System.out.println("The circles do not intersect.");
        } else if (points.length == 0) {
            System.out.println("The two circles are identical and meet at every point.");
        } else if (points.length == 1) {
            System.out.printf("The circles are tangent and meet at %s.\n", points[0]);
        } else {
            System.out.printf("The circles meet at %s and %s.\n", points[0], points[1]);
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

class circle {

    public pt2D center;
    public double radius;
    final public static double EPSILON = 1e-9;

    public circle(pt2D c, double r) {
        center = c;
        radius = r;
    }

    // Returns the intersection points between this circle and another
    public pt2D[] intersect(circle other) {
        double dx = other.center.x - center.x;
        double dy = other.center.y - center.y;
        double d = Math.hypot(dx, dy);

        // Check for identical circles
        if (d < EPSILON && Math.abs(radius - other.radius) < EPSILON) {
            // Infinite number of intersection points
            return new pt2D[0];
        }
        
        // No solution: circles are too far apart or one inside the other
        if (d > radius + other.radius + EPSILON) return null;
        if (d < Math.abs(radius - other.radius) - EPSILON) return null;

        // Solve for point along line between centers
        double a = (radius * radius - other.radius * other.radius + d * d) / (2 * d);
        double px = center.x + a * dx / d;
        double py = center.y + a * dy / d;

        // Circles are tangent (one point)
        if (Math.abs(d - (radius + other.radius)) < EPSILON || Math.abs(d - Math.abs(radius - other.radius)) < EPSILON) {
            return new pt2D[]{ new pt2D(px, py) };
        }

        // Circles intersect at two points
        double h = Math.sqrt(radius * radius - a * a);
        double rx = -dy * (h / d);
        double ry = dx * (h / d);

        pt2D p1 = new pt2D(px + rx, py + ry);
        pt2D p2 = new pt2D(px - rx, py - ry);

        return new pt2D[]{ p1, p2 };
    }
}
