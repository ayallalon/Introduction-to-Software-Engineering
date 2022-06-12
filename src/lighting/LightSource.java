package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;


public interface LightSource {

    /**
     * * get intensity of the light coming from a Point
     * @param p point
     * @return intensity of this point
     */
    Color getIntensity(Point p);

    /**
     * get Loght direction vector
     * @param point satrting point
     * @return direction of light
     */
    Vector getL(Point point);

    double getDistance(Point point);
}
