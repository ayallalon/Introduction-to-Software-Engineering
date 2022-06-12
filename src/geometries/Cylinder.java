package geometries;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Cylinder extends Tube
 */
public class Cylinder extends Tube {

    private final double height;

    /**
     * constructor
     * @param radius for the Cylinder
     * @param ray for the Cylinder
     * @param height for the Cylinder
     */
    public Cylinder(double radius, Ray ray, double height) {
        super(radius, ray);
        this.height = height;
    }

    /**
     * get height
     * @return height
     */
    public double getHeight() {
        return height;
    }

    /**
     * print Cylinder's height, radius and ray
     */
    @Override
    public String toString() {
        return "Cylinder : " +
               "height=" + height +
               ", radius=" + radius +
               ", ray=" + ray;
    }

    @Override
    public Vector getNormal(Point p) {
        Point o = ray.getP0();
        Vector v = ray.getDir();

        // projection of P-O on the ray:
        double t;
        try {
            t = alignZero(p.subtract(o).dotProduct(v));
        } catch (IllegalArgumentException e) { // P = O
            return v;
        }

        // if the point is at a base
        if (t == 0 || isZero(height - t)) // if it's close to 0, we'll get ZERO vector exception
        {
            return v;
        }

        o = o.add(v.scale(t));
        return p.subtract(o).normalize();
    }
}
