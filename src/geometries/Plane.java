package geometries;

import java.util.*;
import primitives.*;
import primitives.Vector;
import static primitives.Util.*;


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

    /**
     * get q0
     * @return q0
     */
    public Point getQ0() {
        return _q0;
    }

    /**
     * get Normal
     * @return normal
     */
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

    /**
     * get Normal
     * @param point
     * @return normal from point
     */
    @Override
    public Vector getNormal(Point point) {
        return getNormal();
    }

    /**
     * findIntersections find intersections between the plane to ray
     * @param ray The Ray to intersect
     * @return list of point that intersections between the plane to ray
     */
    @Override
    public List<Point> findIntersections(Ray ray) {

        Point P0 = ray.getP0();
        Vector v = ray.getDir();
        Vector n = _normal;

        if (_q0.equals(P0)) {//if start of ray equal to q0
            return null;
        }
        Vector P0_Q0 = _q0.subtract(P0);
        //numerator
        double nP0Q0 = alignZero(n.dotProduct(P0_Q0));
        if (isZero(nP0Q0)) {
            return null;
        }
        //denominator
        double nv = alignZero(n.dotProduct(v));
        // ray is lying in the plane axis
        if (isZero(nv)) {
            return null;
        }
        double t = alignZero(nP0Q0 / nv);
        if (t <= 0) {
            return null;
        }
        Point point = P0.add(v.scale(t));
        return List.of(point);

    }
}
