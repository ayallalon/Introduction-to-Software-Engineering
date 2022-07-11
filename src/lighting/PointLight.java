package lighting;

import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource {
    private final Point position;
    private Double3 Kc = Double3.ONE;  //constant attenuation factor
    private Double3 Kl = Double3.ZERO; //linear attenuation factor
    private Double3 Kq = Double3.ZERO; //quadratic attenuation factor

    /**
     * constructor
     * @param intensity
     * @param position
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * getIntensity
     * @param point origin of the light
     * @return Intensity
     */
    @Override
    public Color getIntensity(Point point) {
        Color Ic = getIntensity();
        double distance = point.distance(position);
        double distancesquared = point.distanceSquared(position);

        Double3 factor = (Kc.add(Kl.scale(distance)).add(Kq.scale(distancesquared)));

        return Ic.reduce(factor);
    }

    /**
     * getL
     * @param point  satrting point
     * @return L
     */
    @Override
    public Vector getL(Point point) {
        return point.subtract(position).normalize();
    }

    /**
     * getDistance
     * @param point
     * @return Distance
     */
    @Override
    public double getDistance(Point point) {
        return point.distance(this.position);
    }

    /**
     * setKc
     * @param kc
     * @return kc
     */
    public PointLight setKc(Double3 kc) {
        this.Kc = kc;
        return this;
    }

    /**
     * setKc
     * @param kc
     * @return kc
     */
    public PointLight setKc(double kc) {
        this.Kc = new Double3(kc);
        return this;
    }

    /**
     * setKl
     * @param kl
     * @return Kl
     */
    public PointLight setKl(Double3 kl) {
        this.Kl = kl;
        return this;
    }

    /**
     * setKl
     * @param kl
     * @return kl
     */
    public PointLight setKl(double kl) {
        this.Kl = new Double3(kl);
        return this;
    }

    /**
     * setKq
     * @param kq
     * @return Kq
     */
    public PointLight setKq(Double3 kq) {
        this.Kq = kq;
        return this;
    }

    /**
     * setKq
     * @param kq
     * @return Kq
     */
    public PointLight setKq(double kq) {
        this.Kq = new Double3(kq);
        return this;
    }
}
