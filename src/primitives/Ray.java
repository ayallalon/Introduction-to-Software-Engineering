package primitives;

import static primitives.Util.isZero;

import java.util.List;
import java.util.Objects;
import geometries.Intersectable.GeoPoint;

public class Ray {
    private final Point p0;
    private final Vector dir;

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
        return p0.add(dir.normalize().scale(delta));
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
     * @return the closes poin
     */
    public Point findClosestPoint(List<Point> pointList) {
        //the list is empty
        if (pointList == null) {
            return null;
        }

        double minDistance = Double.MAX_EXPONENT;
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
     * @return the closes poin
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> pointList) {
        //the list is empty
        if (pointList == null) {
            return null;
        }

        double minDistance = Double.MAX_EXPONENT;
        double pointDistance;

        GeoPoint closestPoint = null;

        for (var pt : pointList) {
            pointDistance = p0.distance(pt.point);
            if (pointDistance < minDistance) {
                minDistance = pointDistance;
                closestPoint = pt;
            }
        }
        return closestPoint;
    }
}
