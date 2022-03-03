package geometrries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry{

    final  Point _q0;
    final Vector _normal;


    public Plane(Point q0, Vector normal) {
        _q0 = q0;
        _normal = normal.normalize();
    }

    public Plane(Point q0, Point q1, Point q2) {
        _q0 = q0;
        _normal = null;
    }

    public Point getQ0() {
        return _q0;
    }

    public Vector getNormal() {
        return _normal;
    }



    @Override
    public String toString() {
        return "Plane : " +
                "qoint=" + _q0 +
                ", normal=" + _normal;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
