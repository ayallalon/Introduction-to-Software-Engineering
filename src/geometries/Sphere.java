package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Objects;

import static primitives.Util.alignZero;

public class Sphere extends Geometry {
    final private  Point center;
    final private double radius;

    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sphere sphere = (Sphere) o;
        return Double.compare(sphere.radius, radius) == 0 && center.equals(sphere.center);
    }

    @Override
    public int hashCode() {
        return Objects.hash(center, radius);
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "_center=" + center +
                ", _radius=" + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }


    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point P0 = ray.getP0();
        Vector v = ray.getDir();

        if (P0.equals(center)) {
            if(alignZero(radius - maxDistance) > 0){
                return null;
            }
            return List.of(new GeoPoint(this, center.add(v.scale(radius))));
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

        if(t1 <=0 && t2 <= 0){
            return null;
        }

        if (t1 > 0 && t2 > 0 && alignZero(t1 - maxDistance) <= 0 && alignZero(t2 - maxDistance) <= 0) {
//            Point P1 = P0.add(v.scale(t1));
//            Point P2 = P0.add(v.scale(t2));
            Point P1 =ray.getPoint(t1);
            Point P2 =ray.getPoint(t2);
            return List.of(new GeoPoint(this,P1), new GeoPoint(this,P2));
        }
        if (t1 > 0  && alignZero(t1 - maxDistance) <= 0) {
//            Point P1 = P0.add(v.scale(t1));
            Point P1 =ray.getPoint(t1);
            return List.of(new GeoPoint(this,P1));
        }
        if (t2 > 0 && alignZero(t2 - maxDistance) <= 0) {
//            Point P2 = P0.add(v.scale(t2));
            Point P2 =ray.getPoint(t2);
            return List.of(new GeoPoint(this,P2));
        }
        return null;
    }
//
//
//    @Override
//    public List<GeoPoint> findGeoIntersections(Ray ray, double distance) {
//        Point3D p0 = ray.getP0();
//        Vector v = ray.getDirection();
//        Vector u;
//        try {
//            u = o.subtract(p0);
//        } catch (IllegalArgumentException e) {
//            return List.of(new GeoPoint(this, ray.getPoint(radius)));
//        }
//
//        double tm = v.dotProduct(u);
//        double dSquared = isZero(tm) ? u.lengthSquared() : u.lengthSquared() - tm * tm;
//        double thSquared = alignZero(rSquared - dSquared);
//        if (thSquared <= 0)
//            return null;
//
//        double th = Math.sqrt(thSquared);
//        double t1 = alignZero(tm + th);
//        if (t1 <= 0)
//            return null; // t1 is behind the head since th must be positive (sqrt), t2 must be
//        // non-positive as t1
//
//        double t2 = alignZero(tm - th);
//        if (alignZero(t2 - distance) >= 0)
//            return null;
//
//        // if both t1 and t2 are positive
//        if (alignZero(t1 - distance) >= 0) // t1 beyond the distance
//            return t2 <= 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t2)));
//        else if (t2 > 0) // both points are between 0 and distance
//            return List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
//        else // t2 is behind the head
//            return List.of(new GeoPoint(this, ray.getPoint(t1)));
//    }
}
