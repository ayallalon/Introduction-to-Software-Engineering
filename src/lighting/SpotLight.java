package lighting;

import static primitives.Util.alignZero;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight {

    private Vector direction;


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
     * @param P
     * @return super of getIntensity
     */
    public Color getIntensity(Point P) {
        double projection = direction.dotProduct(getL(P));
        if (alignZero(projection) < 0) {
            return Color.BLACK;
        }
        return super.getIntensity(P).scale(projection);
    }

    /**
     * set Direction
     * @param dir
     * @return Direction
     */
    private SpotLight setDirection(Vector dir) {
        direction = dir;
        return this;
    }

    /**
     * set NarrowBeam
     * @param val
     * @return this
     */
    public PointLight setNarrowBeam(int val) {
        return this;
    }
}
