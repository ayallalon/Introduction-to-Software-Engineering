package primitives;

import static primitives.Util.isZero;

import geometries.Intersectable.GeoPoint;
import java.util.List;
import java.util.Objects;

public class Ray {

    private final Point p0;   // point that the ray started
    private final Vector dir; // direction

    /**
     * Constructor
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
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
        return Objects.hash(getP0(), getDir());
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
        return "Ray : " +
               "p0=" + p0 +
               ", direction=" + dir;
    }


    /**
     * Calculator the closes point to the ray
     * @param pointList list of point that intersection with ray
     * @return the closes point
     */
    public Point findClosestPoint(List<Point> pointList) {
        //the list is empty
        if (pointList == null || pointList.isEmpty()) {
            return null;
        }

        double minDistance = Double.MAX_VALUE;
        double pointDistance;

        Point closestPoint = null;

        for (Point point : pointList) {
            pointDistance = point.distanceSquared(p0);
            if (pointDistance < minDistance) {
                minDistance = pointDistance;
                closestPoint = point;
            }
        }
        return closestPoint;
    }

    /**
     * Calculator the closes point to the ray
     * @param pointList list of point that intersection with ray
     * @return the closes point
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> pointList) {
        //the list is empty
        if (pointList == null) {
            return null;
        }

        double minDistance = Double.MAX_VALUE;
        double pointDistance;

        GeoPoint closestPoint = null;

        for (var geoPoint : pointList) {
            pointDistance = p0.distanceSquared(geoPoint.point);
            if (pointDistance < minDistance) {
                minDistance = pointDistance;
                closestPoint = geoPoint;
            }
        }
        return closestPoint;
    }
}
