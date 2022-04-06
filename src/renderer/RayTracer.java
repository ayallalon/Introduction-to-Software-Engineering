package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * An abstract class for tracing the rays path through the scene
 */
public abstract class RayTracer {
    protected final Scene scene;

    /**
     * constructor
     */
    public RayTracer(Scene scene) {
        this.scene = scene;
    }

    /**
     * An abstract method
     * @param ray to consist the color
     * @return color
     */
    public abstract Color traceRay(Ray ray);
}
