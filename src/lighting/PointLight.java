package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource {

    public Point position;
    public double kC;
    public double kL;
    public double kQ;

    /**
     * constructor
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
        this.kC = 1d;
        this.kL = 0d;
        this.kQ = 0d;
    }

    public Color getIntensity(Point p) {
        double d = p.distance(position);
        double d2 = d * d;

        double calc = (kC + d * kL + d2 * kQ);
        return getIntensity().reduce(calc);
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }
}