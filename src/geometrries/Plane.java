package geometrries;

import primitives.Point;
import primitives.Vector;


public class Plane implements Geometry{

    final  Point _q0;
    final Vector _normal;

    /**
     * constructor
     * @param q0 type point
     * @param normal type vector
     */
    public Plane(Point q0, Vector normal) {
        _q0 = q0;
        _normal = normal.normalize();
    }

    /**
     * another constructor
     * @param q0
     * @param q1
     * @param q2
     */
    public Plane(Point q0, Point q1, Point q2) {
        _q0 = q0;
        _normal = null;
    }

    /**
     * get q0
     * @return q0
     */
    public Point getQ0() {
        return _q0;
    }

    /**
     * get normal.
     * @return normal.
     */
    public Vector getNormal() {
        return _normal;
    }


    /**
     * print plane's point and normal.
     */
    @Override
    public String toString() {
        return "Plane : " +
                "point=" + _q0 +
                ", normal=" + _normal;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
