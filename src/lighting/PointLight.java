package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource {

    public Point position;
    public double kC = 1.0;
    public double kL = 0d;
    public double kQ = 0d;

    /**
     * constructor
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * get Intensity
     * @param p point
     * @return Intensity of the light
     */
    public Color getIntensity(Point p) {
        double d = p.distance(position);
        double d2 = d * d;

        double calc = (kC + d * kL + d2 * kQ);
        return getIntensity().reduce(calc);
    }

    /**
     * get light
     * @return point's super
     */
    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point point) {
        return point.distance(this.position);
    }

    /**
     * set Kc
     * @param kC one of factors for attenuation with distance (d)
     * @return Kc
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * set Kl
     * @param kL one of factors for attenuation with distance (d)
     * @return Kl
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * set Kq
     * @param kQ one of factors for attenuation with distance (d)
     * @return Kq
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }
}