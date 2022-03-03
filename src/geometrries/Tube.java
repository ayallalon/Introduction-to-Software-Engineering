package geometrries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube implements Geometry{

    public double radius;
    final Ray _ray;

    public Tube(double radius, Ray ray) {
        this.radius = radius;
        _ray = ray;
    }

    public double getRadius() {
        return radius;
    }

    public Ray getRay() {
        return _ray;
    }



    @Override
    public String  toString() {
        return "Tube{" +
                "radius=" + radius +
                ", _ray=" + _ray +
                '}';
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
