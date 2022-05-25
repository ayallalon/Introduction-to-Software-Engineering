package lighting;

import static primitives.Util.alignZero;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight {

    private final Vector direction;


    /**
     * constructor
     * @param intensity -> strong of light
     * @param position -> Location of light
     * @param direction the light
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    /**
     * get Intensity
     * @return super of getIntensity
     */
    public Color getIntensity(Point p) {
        Vector l = getL(p);
        double projection = alignZero(direction.dotProduct(l));
        if (projection < 0) {
            return Color.BLACK;
        }
        return super.getIntensity(p).scale(projection);
    }

    /**
     * set NarrowBeam
     * @return this
     */
    public PointLight setNarrowBeam(int val) {
        return this;
    }
}
