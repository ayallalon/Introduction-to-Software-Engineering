package geometries;

import primitives.Ray;

/**
 * Cylinder extends Tube
 */
public class Cylinder extends Tube {

    private final double _height;

    /**
     * constructor
     * @param radius for the Cylinder
     * @param ray for the Cylinder
     * @param height for the Cylinder
     */
    public Cylinder(double radius, Ray ray, double height) {
        super(radius, ray);
        this._height = height;
    }

    /**
     * get height
     * @return height
     */
    public double getHeight() {
        return _height;
    }

    /**
     * print Cylinder's height, radius and ray
     */
    @Override
    public String toString() {
        return "Cylinder : " +
               "height=" + _height +
               ", radius=" + _radius +
               ", ray=" + _ray;
    }
}
