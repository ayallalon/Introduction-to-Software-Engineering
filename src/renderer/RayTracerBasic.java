package renderer;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import geometries.Intersectable.GeoPoint;
import java.util.List;
import lighting.LightSource;
import primitives.Color;
import primitives.Double3;
import primitives.Material;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

/**
 * derivative class from RayTracer traces ray path in the scene noting intersections
 * with geometries in the scene
 */
public class RayTracerBasic extends RayTracer {
    private static final double EPS = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = Double3.ONE;

    /**
     * Constructor
     * @param scene that the ray cross
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }


    /**
     * find intersections of the scene geometries with the
     * received ray ad calculate the color of the intersection points
     * @param ray ray that was shot from the camera
     * @return color of the intersection point
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        if (closestPoint != null) {
            return calcColor(closestPoint, ray);
        }

        //ray did not intersect any geometrical object - return the background color
        return scene.background;
    }


    /**
     * Calculate the color intensity on the point
     * @param gp on the geometry
     * @return the color intensity
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
            .add(scene.ambientLight.getIntensity());
    }

    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Color color = geoPoint.geometry.getEmission();

        Vector v = ray.getDir();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);

        // check that ray is not parallel to geometry
        double nv = alignZero(n.dotProduct(v));

        if (isZero(nv)) {
            return color;
        }
        Material material = geoPoint.geometry.getMaterial();
        color = color.add(calcLocalEffects(geoPoint, material, n, v, nv, k));
        return 1 == level ? color
                          : color.add(calcGlobalEffects(geoPoint, material, n, v, nv, level, k));
    }


    private Color calcGlobalEffects(GeoPoint gp, Material material, Vector n, Vector v, double nv,
                                    int level, Double3 k) {
        Color color = Color.BLACK;
        Double3 kkr = material.kR.product(k);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
            color = color.add(
                calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.getKr(),
                                 kkr));
        }
        Double3 kkt = material.kT.product(k);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
            color = color.add(
                calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.getKt(),
                                 kkt));
        }
        return color;
    }


    private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.getBackground() : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }

    /**
     * Constructs refracted ray with point , ray and vector
     * @param point point of intersection
     * @param n normal vector to the surface at the intersection point
     * @return the ray
     */
    private Ray constructRefractedRay(Point point, Vector v, Vector n) {
        return new Ray(point, v, n);
    }

    private Ray constructReflectedRay(Point point, Vector v, Vector n) {
        Vector vn = n.scale(-2 * v.dotProduct(n));
        Vector r = v.add(vn);
        return new Ray(point, r, n);
    }

    private Color calcLocalEffects(GeoPoint gp, Material material, Vector n, Vector v, double nv,
                                   Double3 k) {
        Color color = Color.BLACK;

        Point point = gp.point;

        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sign(nv)
                Double3 ktr = transparency(gp, lightSource, l, n);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
//                if (unshaded(gp, lightSource, l, n,nv)) {
                    Color iL = lightSource.getIntensity(point).scale(ktr);
                    color = color.add(
                        calcDiffusive(material.kD, nl, iL),
                        calcSpecular(material.kS, n, l, nl, v, material.nShininess, iL));
                }
            }
        }
        return color;
    }

//    private Color calcLocalEffects(Intersectable.GeoPoint intersection, Ray ray, Double3 k) {
//        Vector v = ray.getDir();
//        Vector n = intersection.geometry.getNormal(intersection.point);
//        Color color = intersection.geometry.getEmission();
//
//        double nv = alignZero(n.dotProduct(v));
//        if (nv == 0) {
//            return Color.BLACK;
//        }
//        int nShininess = intersection.geometry.getMaterial().nShininess;
//        Double3 kd = intersection.geometry.getMaterial().kD;
//        Double3 ks = intersection.geometry.getMaterial().kS;
//
//        for (LightSource lightSource : scene.lights) {
//            Vector l = lightSource.getL(intersection.point);
//            double nl = alignZero(n.dotProduct(l));
//            if (nl * nv > 0) { // sign(nl) == sign(nv)
////                if (unshaded(intersection, lightSource, l, n, nv)) {
//                Double3 ktr = transparency(intersection, lightSource, l, n);
//                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) //if (ktr * k > MIN_CALC_COLOR_K)
//                {
//                    Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
//                    color = color.add(calcDiffusive(kd, nl, lightIntensity),
//                                      calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity));
//                }
//            }
//        }
//        return color;
//    }

    private Color calcSpecular(Double3 ks, Vector l, Vector n, double nl, Vector v, int nShininess,
                               Color lightIntensity) {
        l = l.normalize();
        Vector r = l.subtract(n.scale(2 * nl));
        double d = -alignZero(v.dotProduct(r));
        if (d <= 0) {
            return Color.BLACK;
        }

        return lightIntensity.scale(ks.scale(Math.pow(d, nShininess)));
    }

    private Color calcDiffusive(Double3 kd, double nl, Color lightIntensity) {
        if (nl < 0) {
            nl = -nl;
        }
        return lightIntensity.scale(kd).scale(nl);
    }

//    private boolean unshaded(GeoPoint gp, LightSource lightSource, Vector l, Vector n, double nv) {
//        Ray lightRay = new Ray(gp.point, l.scale(-1), n);
//
//        double maxDistance = lightSource.getDistance(gp.point);
//        List<GeoPoint> intersections = scene.geometries.findGeoIntersectionsHelper(lightRay,
//                                                                                   maxDistance);
//
//        if (intersections == null) {
//            return true;
//        }
//        for (var item : intersections) {
//            if (item.geometry.getMaterial().kT.lowerThan(MIN_CALC_COLOR_K)) {
//                return false;
//            }
//        }
//        return true;
//    }

    private Double3 transparency(GeoPoint gp, LightSource lightSource, Vector l, Vector n) {
        Ray lightRay = new Ray(gp.point, l.scale(-1), n);
        double maxDistance = lightSource.getDistance(gp.point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersectionsHelper(lightRay,
                                                                                   maxDistance);

        if (intersections == null) {
            return Double3.ONE;
        }

        Double3 transparency = Double3.ONE;
        for (var geo : intersections) {
            transparency = transparency.product(geo.geometry.getMaterial().kT);
            if (transparency.lowerThan(MIN_CALC_COLOR_K)) {
                return Double3.ZERO;
            }
        }
        return transparency;
    }

    /**
     * find and return the closest point that intersects with the ray
     * @return GeoPoint
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections != null) {
            return ray.findClosestGeoPoint(intersections);
        }
        return null;
    }
}
