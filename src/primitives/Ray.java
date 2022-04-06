package primitives;

import static primitives.Util.isZero;

import java.util.List;
import java.util.Objects;

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
     * find The Closest Point
     * @param pointList -> list of all point that intersection with ray
     * @return the closest point
     */
    public Point findClosestPoint(List<Point> pointList) {
        //the list is empty
        if (pointList == null) {    // if it has no intersection
            return null;
        }
        
        double minDistance = Double.MAX_EXPONENT; // the min distance
        double pointDistance;

        Point closestPoint = null;

        for (Point point : pointList) { //for all point in the list
            pointDistance = point.distanceSquared(p0);
            if (pointDistance < minDistance) { //if its closes more the minDistance point
                minDistance = pointDistance;
                closestPoint = point;
            }
        }
        return closestPoint;
    }
}
