package geometrries;

import primitives.Point;
import primitives.Vector;


public class Plane implements Geometry {

    private final Point _q0;
    private final Vector _normal;

    /**
     * Constructor
     * @param q0 type point
     * @param normal type vector
     */
    public Plane(Point q0, Vector normal) {
        _q0 = q0;
        _normal = normal.normalize();
    }

    /**
     * Another constructor
     */
    public Plane(Point q0, Point q1, Point q2) {
        _q0 = q0;
        Vector u = q1.subtract(q0);
        Vector v = q2.subtract(q0);
        Vector n = u.crossProduct(v);
        _normal = n.normalize();
    }


    public Point getQ0() {
        return _q0;
    }

    @Deprecated
    public Vector getNormal() {
        return _normal;
    }


    /**
     * Print plane's point and normal.
     */
    @Override
    public String toString() {
        return "Plane : " + "point=" + _q0 + ", normal=" + _normal;
    }


    @Override
    public Vector getNormal(Point point) {
        return getNormal();
    }
}
