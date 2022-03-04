package geometrries;

import primitives.Point;
import primitives.Vector;

public class Sphere implements Geometry{

    final Point _center ;
    private double radius;

    /**
     * constructor
     * @param center type point
     * @param radius type double
     */
    public Sphere(Point center, double radius) {
        _center = center;
        this.radius = radius;
    }

    /**
     * get Center
     * @return Center
     */
    public Point getCenter() {
        return _center;
    }

    /**
     * get Radius
     * @return Radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * print sphere's center and radius
     */
    @Override
    public String toString() {
        return "Sphere : " +
                "center=" + _center +
                ", radius=" + radius ;
    }


    /**
     * get Normal
     * @param point
     * @return normal vector from the point to sphere
     */
    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
