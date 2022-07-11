package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource{

    private final Vector direction;

    /**
     * constructor
     * @param intensity of the color
     * @param direction vector
     */
    protected DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    /**
     * getIntensity
     * @param point origin of the light
     * @return Intensity
     */
    @Override
    public Color getIntensity(Point point) {
        return getIntensity();
    }

    /**
     * getL
     * @param point  satrting point
     * @return L
     */
    @Override
    public Vector getL(Point point) {
        return direction;
    }

    /**
     * getDistance
     * @param point
     * @return Distance
     */
    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}
