package primitives;

import java.util.Objects;

public class Ray {
    private final Point _p0;
    private final Vector _dir;

    /**
     * Constructor
     */
    public Ray(Point p0, Vector dir) {
        this._p0 = p0;
        this._dir = dir;
    }

    public Point getP0() {
        return _p0;
    }

    public Vector getDir() {
        return _dir;
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
               "p0=" + _p0 +
               ", direction=" + _dir;
    }
}
