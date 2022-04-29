package lighting;

import static primitives.Util.alignZero;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight {

    private Vector direction;


    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    public Color getIntensity(Point P) {
        double projection = direction.dotProduct(getL(P));
        if (alignZero(projection) < 0) {
            return Color.BLACK;
        }
        return super.getIntensity(P).scale(projection);
    }

    /*
        public Color getIntensity(Point P) {
        double projection = direction.dotProduct(getL(position));
        if (alignZero(projection) < 0)
        {
            return Color.BLACK;
        }
        Color pointIntensity = super.getIntensity(P);
        Vector l = P.subtract(position).normalize();

        double calc = Math.max(0, direction.dotProduct(l));
        return pointIntensity.scale(calc);
    }
     */


    private SpotLight setDirection(Vector dir) {
        direction = dir;
        return this;
    }

    public PointLight setNarrowBeam(int val) {
        return this;
    }
}
