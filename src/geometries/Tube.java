package geometries;


import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import java.util.List;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;


public class Tube extends Geometry {

    protected final Ray ray; //dur for tube
    protected final double radius; // radius for tube

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
        Point P0 = ray.getP0();
        Vector v = ray.getDir();

        Vector P0_P = point.subtract(P0);

        double t = alignZero(v.dotProduct(P0_P));

        if (isZero(t)) {
            return P0_P.normalize();
        }

        Point o = P0.add(v.scale(t));

        if (point.equals(o)) {
            throw new IllegalArgumentException("point cannot be on the tube axis");
        }

        Vector n = point.subtract(o).normalize();

        return n;
    }


    /**
     * @param ray the ray crossing the geometric object
     * @param maxDistance max distance for finding intersections
     * @return List of intersection points
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Vector vAxis = ray.getDir();
        Vector v = ray.getDir();
        Point p0 = ray.getP0();

        // At^2+Bt+C=0
        double a = 0;
        double b = 0;
        double c = 0;

        double vVa = alignZero(v.dotProduct(vAxis));
        Vector vVaVa;
        Vector vMinusVVaVa;
        if (vVa == 0) // the ray is orthogonal to the axis
        {
            vMinusVVaVa = v;
        } else {
            vVaVa = vAxis.scale(vVa);
            try {
                vMinusVVaVa = v.subtract(vVaVa);
            } catch (IllegalArgumentException e1) { // the rays is parallel to axis
                return null;
            }
        }
        // A = (v-(v*va)*va)^2
        a = vMinusVVaVa.lengthSquared();

        Vector deltaP = null;
        try {
            deltaP = p0.subtract(ray.getP0());
        } catch (IllegalArgumentException e1) { // the ray begins at axis P0
            if (vVa == 0 && alignZero(radius - maxDistance) <= 0) { // the ray is orthogonal to Axis
                return List.of(new GeoPoint(this, ray.getPoint(radius)));
            }
            double t = alignZero(Math.sqrt(radius * radius / vMinusVVaVa.lengthSquared()));
            return alignZero(t - maxDistance) >= 0 ? null
                                                   : List.of(new GeoPoint(this, ray.getPoint(t)));
        }

        double dPVAxis = alignZero(deltaP.dotProduct(vAxis));
        Vector dPVaVa;
        Vector dPMinusdPVaVa;
        if (dPVAxis == 0) {
            dPMinusdPVaVa = deltaP;
        } else {
            dPVaVa = vAxis.scale(dPVAxis);
            try {
                dPMinusdPVaVa = deltaP.subtract(dPVaVa);
            } catch (IllegalArgumentException e1) {
                double t = alignZero(Math.sqrt(radius * radius / a));
                return alignZero(t - maxDistance) >= 0 ? null : List.of(
                    new GeoPoint(this, ray.getPoint(t)));
            }
        }

        // B = 2(v - (v*va)*va) * (dp - (dp*va)*va))
        b = 2 * alignZero(vMinusVVaVa.dotProduct(dPMinusdPVaVa));
        c = dPMinusdPVaVa.lengthSquared() - radius * radius;

        // A*t^2 + B*t + C = 0 - lets resolve it
        double discr = alignZero(b * b - 4 * a * c);
        if (discr <= 0) {
            return null; // the ray is outside or tangent to the tube
        }

        double doubleA = 2 * a;
        double tm = alignZero(-b / doubleA);
        double th = Math.sqrt(discr) / doubleA;
        if (isZero(th)) {
            return null; // the ray is tangent to the tube
        }

        double t1 = alignZero(tm + th);
        if (t1 <= 0) // t1 is behind the head
        {
            return null; // since th must be positive (sqrt), t2 must be non-positive as t1
        }

        double t2 = alignZero(tm - th);

        // if both t1 and t2 are positive
        if (t2 > 0 && alignZero(t2 - maxDistance) < 0) {
            return List.of(new GeoPoint(this, ray.getPoint(t1)),
                           new GeoPoint(this, ray.getPoint(t2)));
        } else if (alignZero(t1 - maxDistance) < 0)// t2 is behind the head
        {
            return List.of(new GeoPoint(this, ray.getPoint(t1)));
        }
        return null;
    }
}
