package geometries;

import java.util.List;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;


public class Sphere implements Geometry {

    /*private*/ final Point _center;
    /*private*/ final double _radius;

    /**
     * Constructor
     * @param center type point
     * @param radius type double
     */
    public Sphere(Point center, double radius) {
        _center = center;
        _radius = radius;
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
        /*Vector n = point.subtract(_center);
        return n.normalize();*/
        return point.subtract(_center).normalize();
    }

    /**
     * findIntersections find intersections between the sphere to ray
     * @param ray The Ray to intersect
     * @return list of point that intersections between the sphere to ray
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        Point P0 = ray.getP0();        //get point of start ray
        Vector v = ray.getDir();      //get dir of ray

        if (P0.equals(_center)) {    //if start of ray equal to the sphere's center
            return List.of(_center.add(v.scale(_radius)));
        }

        Vector U = _center.subtract(P0);

        double tm = alignZero(v.dotProduct(U));
        double d = alignZero(Math.sqrt(U.lengthSquared() - tm * tm));
        // no intersections : the ray direction is above the sphere
        if (d >= _radius) {
            return null;
        }

        double th = alignZero(Math.sqrt(_radius * _radius - d * d));
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        if (t1 > 0 && t2 > 0) {
            Point P1 =ray.getPoint(t1);
            Point P2 =ray.getPoint(t2);
            return List.of(P1, P2);
        }
        if (t1 > 0) {
            Point P1 =ray.getPoint(t1);
            return List.of(P1);
        }
        if (t2 > 0) {
            Point P2 =ray.getPoint(t2);
            return List.of(P2);
        }
        return null;
    }
}
