package geometries;


import java.util.List;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;


public class Tube extends Geometry {

    protected final Ray ray; //dur for tube
    final double radius; // radius for tube

    /**
     * Constructor
     * @param radius type double
     * @param ray type ray
     */
    public Tube(double radius, Ray ray) {
        super();
        this.radius = radius;
        this.ray = ray;
    }

    /**
     * get Radius
     * @return Radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * get Ray
     * @return Ray
     */
    public Ray getRay() {
        return ray;
    }


    /**
     * Print tube's radius and ray
     */
    @Override
    public String toString() {
        return "Tube{" + "radius=" + radius + ", ray=" + ray + '}';
    }

    /**
     * @return normal from the point to geometrries
     */
    @Override
    public primitives.Vector getNormal(Point point) {
        {
            {
                Point p0 = ray.getP0();
                Vector v = ray.getDir();
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
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        return null;
    }
}
