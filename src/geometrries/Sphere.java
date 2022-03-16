package geometrries;

import primitives.Point;
import primitives.Vector;

public class Sphere implements Geometry {

    private final Point _center;
    private final double _radius;

    /**
     * Constructor
     * @param center type point
     * @param radius type double
     */
    public Sphere(Point center, double radius) {
        _center = center;
        this._radius = radius;
    }


    public Point getCenter() {
        return _center;
    }


    public double getRadius() {
        return _radius;
    }

    /**
     * Print sphere's center and radius
     */
    @Override
    public String toString() {
        return "Sphere : " + "center=" + _center + ", radius=" + _radius;
    }


    /**
     * Get Normal
     * @return normal vector from the point to sphere
     */
    @Override
    public Vector getNormal(Point point) {
        /**
         if(point.equals(_center)){
         throw new IllegalArgumentException("ERROR - p equal center");}
         */
        Vector n = point.subtract(_center);
        return n.normalize();
    }
}
