package primitives;

import java.util.Objects;

public class Ray {
    Point p0;
    Vector dir;

    /**
     * constructor
     * @param p0
     * @param dir
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir ;
    }

    /**
     *
     * @return point
     */
    public Point getP0() {
        return p0;
    }

    /**
     *
     * @return direction of vector
     */
    public Vector getDir() {
        return dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray)) return false;
        Ray ray = (Ray) o;
        return getP0().equals(ray.getP0()) && getDir().equals(ray.getDir());
    }

    @Override
    public String toString() {
        return "Ray : " +
                "p0=" + p0 +
                ", direction=" + dir;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getP0(), getDir());
    }
}
