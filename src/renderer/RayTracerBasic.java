package renderer;

import static primitives.Util.alignZero;

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
    private static final Double3 INITIAL_K = Double3.ONE;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final double DELTA = 0.1;
    private boolean bb;


    /**
     * constructor
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * setBb
     * @return bb
     */
    public RayTracer setBb(boolean bb) {
        this.bb = bb;
        return this;
    }

    /**
     * findClosestIntersection
     * @return list of the Closest Intersection point
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections;
        intersections = scene.getGeometries().findGeoIntersections(ray);
        if (intersections == null || intersections.size() == 0) {
            return null;
        }
        return ray.findClosestGeoPoint(intersections);
    }

    /**
     * traces the ray and its intersections with geometries to find the closest point and return its
     * colour
     * @param ray the ray being traced
     * @return the calculated color of the closest point- to colour thus themathcing pixel
     */
    @Override
//    public Color traceRay(Ray ray) {
//        Geometries geometries = scene.getGeometries();
//        List<GeoPoint> intersectionPoints = geometries.findGeoIntersections(ray);
//        if (intersectionPoints == null) {
//            return scene.getBackground();
//        }
//        Intersectable.GeoPoint closesPoint = ray.findClosestGeoPoint(intersectionPoints);
//        return calcColor(closesPoint, ray);
//    }


    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
    }


    private Color calcColor(GeoPoint geopoint, Ray ray) {
        return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
            .add(scene.ambientLight.getIntensity());
    }

    /**
     * calcColor
     * @return the color
     */
//    private Color calcColor(GeoPoint gp, Ray ray) {
//        Color result = scene.getAmbientLight().getIntensity();
//        result = result.add(calcLocalEffects(gp, ray)).add(gp.geometry.getEmission());
//        return result;
//    }
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Color color = geoPoint.geometry.getEmission().add(calcLocalEffects(geoPoint, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray.getDir(), level, k));
    }


    /**
     * calcLocalEffects
     * @return color effects
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Color color = Color.BLACK;
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) {
            return color;
        }
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) {
                // if(transparency(gp,lightSource,n,nl,nv,l)) {
                Double3 ktr = transparency(l, n, lightSource, gp, nv);
                if (!(ktr.product(k).lowerThan(MIN_CALC_COLOR_K))) {
                    Color iL = lightSource.getIntensity(gp.point);
                    color = color.add(
                        iL.scale(calcDiffusive(material, nl)),
                        iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }

    private Double3 transparency(Vector l, Vector n, LightSource lightSource, GeoPoint gp,
                                 double nv) {
        Vector lightDirection = l.scale(-1); // from point to light source//
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        double lightDistance = lightSource.getDistance(gp.point);
        var intersections = scene.getGeometries().findGeoIntersections(lightRay);
        if (intersections == null) {
            return Double3.ONE; //no intersection
        }
        for (var geo : intersections) {
            double dist = geo.point.distance(gp.point);
            if (dist >= lightDistance) {
                intersections.remove(geo);
            }
        }
        if (intersections.isEmpty()) {
            return Double3.ONE;
        }
        Double3 ktr = Double3.ONE;
        for (GeoPoint geopoint : intersections) {
            ktr = ktr.product(geopoint.geometry.getMaterial().kT);
            if (ktr.lowerThan(MIN_CALC_COLOR_K)) {
                return Double3.ZERO;
            }
        }
        return ktr;
//        Point point = gp.point;
//      //  Vector l = lightSource.getL(point);
//        Vector lightDirection  = l.scale(-1);
//        Vector delVector = n.scale(nv < 0 ? DELTA : -DELTA);
//        Point pointRay = point.add(delVector);
//        Ray lightRay = new Ray(pointRay, lightDirection);
//        double maxDistance = lightSource.getDistance(point);
//        double lightDistance = lightSource.getDistance(gp.point);
//        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(lightRay, maxDistance);
//        if (intersections == null)
//            return 1.1; //no intersection
//        Double ktr = 1.0;
//        for (GeoPoint geopoint : intersections) {
//            if (alignZero(geopoint.point.distance(gp.point) - lightDistance) <= 0) {
//                ktr = (geopoint.geometry.getMaterial().kT).scale(ktr);
//                if (ktr< MIN_CALC_COLOR_K)
//                    return 0.0;
//            }
//        }
//        return ktr;
//       // return intersections == null;
    }


//
//    /**
//     * unshaded
//     * @return if has unshaded
//     */
//    private boolean unshaded(GeoPoint gp, LightSource lightSource, Vector n, double nl, double nv) {
//        Point point = gp.point;
//        Vector l = lightSource.getL(point);
//        Vector lightDirection = l.scale(-1); // from point to light source
//        Vector delVector = n.scale(nv < 0 ? DELTA : -DELTA);
//        Point pointRay = (point.add(delVector));
//        Ray lightRay = new Ray(pointRay, lightDirection);
//        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(lightRay);
//        if (intersections == null) {
//            return true;
//        }
//        double maxDistance = lightSource.getDistance(point);
//        if (intersections == null) {
//            return true;
//        }
//        for (GeoPoint geoPoint : intersections) {
//            double distance = geoPoint.point.distance(point);
//            if (distance >= maxDistance || geoPoint.geometry.getMaterial().kT == Double3.ZERO) {
//                intersections.remove(geoPoint);
//            }
//        }
//        return intersections.isEmpty();
//    }


    /**
     * calcDiffusive
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }

    /**
     * calcSpecular
     * @param material of the geometry
     * @return scale of the geometry
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(l.dotProduct(n) * 2)).normalize();
        return material.kS.scale(
            Math.pow(Math.max(0, r.dotProduct(v.scale(-1d))), material.nShininess));
    }

    //    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, Double3 k) {
//        Color color = Color.BLACK;
//        Vector n = gp.geometry.getNormal(gp.point);
//        Material material = gp.geometry.getMaterial();
//        Double3 kkr = material.kR.product(k);
//        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
//            color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kR,
//                                     kkr);
//        }
//        Double3 kkt = material.kT.product(k);
//        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
//            color = color.add(
//                calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.kT, kkt));
//        }
//        return color;
//    }
    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        Double3 kkr = material.kR.product(k);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
            color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kR,
                                     kkr);
        }
        Double3 kkt = material.kT.product(k);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
            color = color.add(
                calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.kT, kkt));
        }
        return color;
    }

    private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }

    private Ray constructRefractedRay(Point point, Vector v, Vector n) {
        return new Ray(point, n, v);
    }

    private Ray constructReflectedRay(Point pointGeo, Vector v, Vector n) {

        double vn = v.dotProduct(n);
        if (vn == 0) {
            return null;
        }
        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(pointGeo, n, r);
    }
}
