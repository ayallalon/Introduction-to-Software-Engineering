package primitives;

import java.util.Objects;

import static primitives.Util.isZero;

public class Ray {
    private final Point _p0;
    private final Vector _dir;

    /**
     * Constructor
     */
    public Ray(Point p0, Vector dir) {
        this._p0 = p0;
        this._dir = dir.normalize();
    }

    /**
     * get P0
     * @return P0
     */
    public Point getP0() {
        return _p0;
    }

    /**
     * get Point
     * @param delta
     * @return p0 + delta
     */
    public Point getPoint(double delta ){
        if (isZero(delta)){return  _p0;}
        return _p0.add(_dir.normalize().scale(delta));
    }

    /**
     * get Dir
     * @return Dir
     */
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
