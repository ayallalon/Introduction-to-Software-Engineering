package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * @author Ayala alon & Tehila Gabay
 */
public class Triangle extends Polygon {

    final Point p0;
    final Point p1;
    final Point p2;
    Vector v0v1;
    Vector v0v2;
//    final Vector v0v1;
//    final Vector v0v2;

    public Triangle(Point pA, Point pB, Point pC) {
        super(pA, pB, pC);
        this.p0 = pA;
        this.p1 = pB;
        this.p2 = pC;

        /*Vector*/ v0v1 = p1.subtract(p0);      // v1 - v0
        /*Vector*/ v0v2 = p2.subtract(p0);      // v2 - v0


///        Vector N = U.crossProduct(V).normalize();   // AB X AC
//
//        // right hand rule

        this.normal = v0v1.crossProduct(v0v2).normalize();
//        this.normal = plane.getNormal();
    }

    @Override
    public Vector getNormal(Point point) {
        return this.normal;
    }

//    /**
//     * finding intersection points using barycentric coordinates
//     *
//     * @param ray         ray intersecting
//     * @param maxDistance
//     * @return intersection points (GeoPoint)
//     * {@see <a href="https://www.scratchapixel.com/lessons/3d-basic-rendering/ray-tracing-rendering-a-triangle/barycentric-coordinates"></a>}
//     */
//    @Override
//    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
//        // MOLLER_TRUMBORE
//        Vector dir = ray.getDir();
//        Point origin = ray.getP0();
//
//        Vector N = this.normal;
//        double denom = N.dotProduct(N);
//
////
////        //Step 1 finding P
////        double NdotRayDirection = N.dotProduct(dir);
////
////        if (isZero(NdotRayDirection)) { // almost 0
////            return null; // they are parallel so they don't intersect !
////        }
//
//        Vector pvec = dir.crossProduct(v0v2);
//
//        double det = alignZero(v0v1.dotProduct(pvec));
//
//        // if CULLING
//        // if the determinant is negative the triangle is backfacing
//        //if(det < 0){
//        //    return null;
//        //}
//
//        // if the determinant is close to 0, the ray misses the triangle
//
//        if (det <= 0) {
//            return null;
//        }
//        double invDet = 1d / det;
//
//        Vector tvec = origin.subtract(p0);                //orig - v0;
//
//        double u = alignZero(tvec.dotProduct(pvec) * invDet);
//        if (u < 0 || u >= 1)
//            return null;
//
//        Vector qvec = tvec.crossProduct(v0v1);
//        double v = alignZero(dir.dotProduct(qvec) * invDet);
//        if (v < 0 || u + v >= 1)
//            return null;
//
//        double t = v0v2.dotProduct(qvec) * invDet;
//
//        if (alignZero(t - maxDistance) > 0)
//            return null;
//
//        return List.of(new GeoPoint(this, ray.getPoint(t)));
//    }

    @Override
    public String toString() {
        return "Triangle {" + p0 + "," + p1 + "," + p2 + "}";
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> planeIntersections = plane.findGeoIntersections(ray, maxDistance);
        if (planeIntersections == null)
            return null;

        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        Vector v1 = this.p0.subtract(p0);
        Vector v2 = this.p1.subtract(p0);
        Vector v3 = this.p2.subtract(p0);

        double s1 = v.dotProduct(v1.crossProduct(v2));
        if (isZero(s1))
            return null;
        double s2 = v.dotProduct(v2.crossProduct(v3));
        if (isZero(s2))
            return null;
        double s3 = v.dotProduct(v3.crossProduct(v1));
        if (isZero(s3))
            return null;

        if ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0)) {
            Point point = planeIntersections.get(0).point;
            return List.of(new GeoPoint(this, point));
        }
        return null;
    }
}
