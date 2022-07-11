package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * @author Ayala alon & Tehila Gabay
 */
public class SpotLight extends PointLight {

    private final Vector direction;
    private double narrowBeam = 0d;

    /**
     * SpotLight
     * @param intensity
     * @param position
     * @param direction
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    /**
     * setNarrowBeam
     * @param narrowBeam
     * @return narrowBeam
     */
    public SpotLight setNarrowBeam(double narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }

    /**
     * getIntensity
     * @param point origin of the light
     * @return Intensity
     */
    @Override
    public Color getIntensity(Point point) {
       Color Ic = super.getIntensity(point);
       double lv = getL(point).dotProduct(direction);
       double factor =Math.max(0,lv);

       return Ic.scale(factor);
    }

    /**
     * getNarrowBeam
     * @return narrowBeam
     */
    public double getNarrowBeam() {
        return narrowBeam;
    }
}

