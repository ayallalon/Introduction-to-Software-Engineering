package geometries;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import java.util.List;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;


public class Plane extends Geometry {


    private final Point q0;
    private final Vector normal;

    /**
     * Constructor
     * @param q0 type point
     * @param normal type vector
     */
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal.normalize();
    }

    /**
     * Another constructor
     */
    public Plane(Point q0, Point q1, Point q2) {
        this.q0 = q0;
        Vector u = q1.subtract(q0);
        Vector v = q2.subtract(q0);
        Vector n = u.crossProduct(v);
        normal = n.normalize();
    }

    /**
     * get q0
     * @return q0
     */
    public Point getQ0() {
        return q0;
    }

    /**
     * get Normal
     * @return normal
     */
    public Vector getNormal() {
        return normal;
    }


    /**
     * Print plane's point and normal.
     */
    @Override
    public String toString() {
        return "Plane : " + "point=" + q0 + ", normal=" + normal;
    }

    /**
     * get Normal
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
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {

        Point P0 = ray.getP0();
        Vector v = ray.getDir();
        Vector n = normal;

        if (q0.equals(P0)) {//if start of ray equal to q0
            return null;
        }
        Vector P0_Q0 = q0.subtract(P0);
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
        if (t <= 0 || alignZero(t - maxDistance) > 0) {
            return null;
        }
        return List.of(new GeoPoint(this, ray.getPoint(t)));
    }
}