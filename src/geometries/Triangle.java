package geometries;

import static primitives.Util.isZero;

import java.util.List;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Triangle extends Polygon {

    final Point p0;
    final Point p1;
    final Point p2;
    Vector v0v1;
    Vector v0v2;


    public Triangle(Point pA, Point pB, Point pC) {
        super(pA, pB, pC);
        this.p0 = pA;
        this.p1 = pB;
        this.p2 = pC;
        v0v1 = p1.subtract(p0);      // v1 - v0
        v0v2 = p2.subtract(p0);      // v2 - v0
        this.normal = v0v1.crossProduct(v0v2).normalize();
    }


    /**
     * Print Triangle's vertices and plane
     */
    @Override
    public String toString() {
        return "Triangle {" + p0 + "," + p1 + "," + p2 + "}";
    }

    @Override
    public Vector getNormal(Point point) {
        return this.normal;
    }

    /**
     * findIntersections find intersections between the triangle to ray
     * @param ray The Ray to intersect
     * @return list of point that intersections between the triangle to ray
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> planeIntersections = plane.findGeoIntersections(ray, maxDistance);
        if (planeIntersections == null) {
            return null;
        }

        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        Vector v1 = this.p0.subtract(p0);
        Vector v2 = this.p1.subtract(p0);
        Vector v3 = this.p2.subtract(p0);

        double s1 = v.dotProduct(v1.crossProduct(v2));
        if (isZero(s1)) {
            return null;
        }
        double s2 = v.dotProduct(v2.crossProduct(v3));
        if (isZero(s2)) {
            return null;
        }
        double s3 = v.dotProduct(v3.crossProduct(v1));
        if (isZero(s3)) {
            return null;
        }

        if ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0)) {
            Point point = planeIntersections.get(0).point;
            return List.of(new GeoPoint(this, point));
        }
        return null;
    }
}