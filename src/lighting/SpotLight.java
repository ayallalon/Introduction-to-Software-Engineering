package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight {

    private final Vector direction;
    private double narrowBeam = 0d;

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

    @Override
    public Color getIntensity(Point point) {
        Color Ic = super.getIntensity(point);
        double lv = getL(point).dotProduct(direction);
        double factor = Math.max(0, lv);

        return Ic.scale(factor);
    }

    public double getNarrowBeam() {
        return narrowBeam;
    }

    /**
     * set NarrowBeam
     * @return this
     */
    public SpotLight setNarrowBeam(double narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }
}
