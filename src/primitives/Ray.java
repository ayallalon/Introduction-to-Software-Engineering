package primitives;

import static primitives.Util.isZero;

import geometries.Intersectable.GeoPoint;
import java.util.List;
import java.util.Objects;

public class Ray {
    private static final double DELTA = 0.1;
    //Delta value to move the point away from original point
    private final Point p0;   // point that the ray started
    private final Vector dir; // direction

    /**
     * Constructor
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    public Ray(Point point, Vector direction, Vector normal) {
        //point + normal.scale(±EPSILON)
        this.dir = direction.normalize();
        double nv = normal.dotProduct(this.dir);
        Vector normalDelta = normal.scale((nv > 0 ? DELTA : -DELTA));
        this.p0 = point.add(normalDelta);
    }


    /**
     * get P0
     * @return P0
     */
    public Point getP0() {
        return p0;
    }

    /**
     * get Point
     * @return p0 + delta
     */
    public Point getPoint(double delta) {
        if (isZero(delta)) {
            return p0;
        }
        return p0.add(dir.scale(delta));
    }

    /**
     * get Dir
     * @return Dir
     */
    public Vector getDir() {
        return dir;
    }

    @Override
    public int hashCode() {
        return Objects.hash(p0, dir);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ray)) {
            return false;
        }
        Ray ray = (Ray)o;
        return getP0().equals(ray.getP0()) && getDir().equals(ray.getDir());
    }

    @Override
    public String toString() {
        return "Ray{" +
               "p0=" + p0 +
               ", dir=" + dir +
               '}';
    }


    /**
     * Calculator the closes point to the ray
     * @param pointList list of point that intersection with ray
     * @return the closes point
     */
    public Point findClosestPoint(List<Point> pointList) {
        return pointList == null || pointList.isEmpty()
               ? null
               : findClosestGeoPoint(pointList.stream()
                                              .map(p -> new GeoPoint(null, p))
                                              .toList()
                                    ).point;
    }

    /**
     * Calculator the closes point to the ray
     * @param geoPoints list of point that intersection with ray
     * @return the closes point
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints) {
        //the list is empty                           //
        if (geoPoints == null) {
            return null;
        }

        double minDistance = Double.MAX_VALUE;
        double pointDistance;
        GeoPoint closestPoint = null;

        for (var geoPoint : geoPoints) {
            pointDistance = geoPoint.point.distanceSquared(p0);
            if (pointDistance < minDistance) {
                minDistance = pointDistance;
                closestPoint = geoPoint;
            }
        }
        return closestPoint;
    }
}
