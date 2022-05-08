package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;


public interface LightSource {

    /**
     * @param p point
     * @return intensity of this point
     */
    Color getIntensity(Point p);

    Vector getL(Point p);

    double getDistance(Point point);
}
