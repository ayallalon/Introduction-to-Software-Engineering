package geometries;

import static primitives.Util.alignZero;

import java.util.List;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;


public class Sphere extends Geometry {

    final Point center; //center of the sphere
    final double radius; //radius of sphere

    /**
     * Constructor
     * @param center type point
     * @param radius type double
     */
    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }


    /**
     * get Center
     * @return Center
     */
    public Point getCenter() {
        return center;
    }

    /**
     * get Radius
     * @return Radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Print sphere's center and radius
     */
    @Override
    public String toString() {
        return "Sphere : " + "center=" + center + ", radius=" + radius;
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

        return point.subtract(center).normalize();
    }

    /**
     * findIntersections find intersections between the sphere to ray
     * @param ray The Ray to intersect
     * @return list of point that intersections between the sphere to ray
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point P0 = ray.getP0();        //get point of start ray
        Vector v = ray.getDir();      //get dir of ray

        if (P0.equals(center)) {    //if start of ray equal to the sphere's center
            return List.of(new GeoPoint(this, ray.getPoint(radius)));
        }

        Vector U = center.subtract(P0);

        double tm = alignZero(v.dotProduct(U));
        double d = alignZero(Math.sqrt(U.lengthSquared() - tm * tm));
        // no intersections : the ray direction is above the sphere
        if (d >= radius) {
            return null;
        }

        double th = alignZero(Math.sqrt(radius * radius - d * d));
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        if (t1 > 0 && t2 > 0 && alignZero(t1 - maxDistance) < 0
            && alignZero(t2 - maxDistance) < 0) {
            Point P1 = ray.getPoint(t1);
            Point P2 = ray.getPoint(t2);
            return List.of(new GeoPoint(this, P1), new GeoPoint(this, P2));
        }
        if (t1 > 0 && alignZero(t1 - maxDistance) < 0) {
            Point P1 = ray.getPoint(t1);
            return List.of(new GeoPoint(this, P1));
        }
        if (t2 > 0 && alignZero(t2 - maxDistance) < 0) {
            Point P2 = ray.getPoint(t2);
            return List.of(new GeoPoint(this, P2));
        }
        return null;
    }
}
