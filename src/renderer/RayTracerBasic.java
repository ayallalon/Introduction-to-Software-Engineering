package renderer;

import geometries.Geometries;
import java.util.List;

import geometries.Intersectable;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

/**
 * derivative class from RayTracer traces ray path in the scene noting intersections
 * with geometries in the scene
 */
public class RayTracerBasic extends RayTracer {

    private static final int max_calc_color_level = 10;
    private static final double min_calc_color_k = 0.001;
    private boolean bb;

    public RayTracer setBb(boolean bb) {
        this.bb = bb;
        return this;
    }

    public RayTracerBasic(Scene scene) {
        super(scene);
    }

   private Point findClosestIntersection(Ray ray){
        List<Point> intersection;
        intersection = scene.getGeometries().findIntersections(ray);
        if(intersection == null || intersection.size() == 0){
            return null;
        }
        else{
            return ray.findClosestPoint(intersection);
        }

   }

    /**
     * traces the ray and its intersections with geometries to find the closest point and return its
     * colour
     * @param ray the ray being traced
     * @return the calculated color of the closest point- to colour thus themathcing pixel
     */
    @Override
    public Color traceRay(Ray ray) {
        Geometries geometries = super.scene.getGeometries();
        List<Intersectable.GeoPoint> intersectionPoints = geometries.findGeoIntersections(ray);
        if(intersectionPoints==null) {
            return scene.getBackground();
        }
        Intersectable.GeoPoint closesPoint = ray.findClosestGeoPoint(intersectionPoints);
        return calcColor(closesPoint,ray);
    }

    private Color calcColor(Intersectable.GeoPoint point, Ray ray) {
        Color result = scene.getAmbientLight().getIntensity();
        result = result.add(point.geometry.getEmission());
        return result;
    }
}