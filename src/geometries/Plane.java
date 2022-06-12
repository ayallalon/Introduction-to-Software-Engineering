package geometries;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import java.util.List;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;


public class Plane extends FlatGeometry {

    final Point q0;


    /**
     * Constructor of Plane based on a point and a directional vector
     * @param q0 referenced Point
     * @param normal Positional vector for the Plane (will be normalized automatically)
     */
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal.normalize();
    }


    /**
     * Constructor of Plane based on three Points
     * @param p1 first Point
     * @param p2 second point
     * @param p3 third Point
     */
    public Plane(Point p1, Point p2, Point p3) {
        this.q0 = p1;
        Vector u = p1.subtract(p2);
        Vector v = p1.subtract(p3);
        Vector n = u.crossProduct(v);
        this.normal = n.normalize();
    }

    /**
     * getter for Q0 : referenced 3D Point of the plane
     * @return q0
     */
    public Point getQ0() {
        return q0;
    }


    /**
     * Print plane's point and normal.
     */
    @Override
    public String toString() {
        return "Plane : " + "point=" + q0 + ", normal=" + normal;
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
        //denominator
        double nv = alignZero(n.dotProduct(v));

        // ray is lying in the plane axis
        if (isZero(nv)) {
            return null;
        }

        //ray cannot start from the plane
        if (q0.equals(P0)) {
            return null;
        }

        Vector P0_Q0 = q0.subtract(P0);

        //numerator
        double nP0Q0 = alignZero(n.dotProduct(P0_Q0));

        // ray parallel to the plane
        if (isZero(nP0Q0)) {
            return null;
        }

        double t = alignZero(nP0Q0 / nv);

        if (t < 0 || alignZero(t - maxDistance) > 0) {
            return null;
        }


        Point point = ray.getPoint(t);

        return List.of(new GeoPoint(this, point));
    }

    /**
     * getter for _normal field of the Plane
     * @return normal
     */
    public Vector getNormal() {
        return normal;
    }

    /**
     * implementation of getNormal from {@link Geometry#getNormal(Point)}
     * @param point Point from where to create a Normal vector to the geometry object
     * @return normal
     */
    @Override
    public Vector getNormal(Point point) {
        return normal;
    }
}