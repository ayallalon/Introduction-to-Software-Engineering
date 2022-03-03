package geometrries;

import primitives.Point;
import primitives.Vector;

public class Sphere implements Geometry{

    final Point _center ;
    private double radius;

    public Sphere(Point center, double radius) {
        _center = center;
        this.radius = radius;
    }

    public Point getCenter() {
        return _center;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Sphere : " +
                "center=" + _center +
                ", radius=" + radius ;
    }


    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
