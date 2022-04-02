package geometries;


import static primitives.Util.isZero;

import java.util.List;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;


public class Tube implements Geometry {

    protected final Ray _ray;
     final double _radius;

    /**
     * Constructor
     * @param radius type double
     * @param ray type ray
     */
    public Tube(double radius, Ray ray) {
        _radius = radius;
        _ray = ray;
    }

    /**
     * get Radius
     * @return Radius
     */
    public double getRadius() {
        return _radius;
    }

    /**
     * get Ray
     * @return Ray
     */
    public Ray getRay() {
        return _ray;
    }


    /**
     * Print tube's radius and ray
     */
    @Override
    public String toString() {
        return "Tube{" + "radius=" + _radius + ", ray=" + _ray + '}';
    }

    /**
     * @return normal from the point to geometrries
     */
    @Override
    public primitives.Vector getNormal(Point point) {
        {
            {
                Point p0 = _ray.getP0();
                Vector v = _ray.getDir();
                Vector p0_p = point.subtract(p0);

                double w = v.dotProduct(p0_p);

                Point startP = p0.add(v.scale(w));
                Vector n = point.subtract(startP);
                return n.normalize();
            }
        }
    }

    /**
     * findIntersections find intersections between the tube to ray
     * @param ray The Ray to intersect
     * @return list of point that intersections between the tube to ray
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
