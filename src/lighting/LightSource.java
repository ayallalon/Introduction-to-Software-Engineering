package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public interface LightSource {
    /**
     * get intensity of the light coming from a Point
     * @param point origin of the light
     * @return
     */
    public Color getIntensity(Point point) ;

    /**
     * get Loght direction vector
     * @param point  satrting point
     * @return direction of light
     */
    public Vector getL(Point point);

    /**
     * getDistance
     * @param point
     * @return Distance
     */
    double getDistance(Point point);
}
