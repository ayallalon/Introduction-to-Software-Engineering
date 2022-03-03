package geometrries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube {

    public double height;


    public Cylinder(double radius, Ray ray, double height) {
        super(radius , ray);
        this.height = height;
    }


    @Override
    public Vector getNormal(Point point) {
        return super.getNormal(point);
    }


    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Cylinder : " +
                "height=" + height +
                ", radius=" + radius +
                ", _ray=" + _ray ;
    }
}
