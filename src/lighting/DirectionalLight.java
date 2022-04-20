package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light {

    final Vector Direction;

    /**
     * constructor
     * @param intensity
     * @param direction
     */
    protected DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        Direction = direction;
    }

    /**
     *
     * @return
     */
    @Override
    public Color getIntensity() {
        return super.getIntensity();
    }

    /**
     *
     * @param p
     * @return
     */
    public Vector getL(Point p)
    {
        return Direction;
        //TODO
    }
}
