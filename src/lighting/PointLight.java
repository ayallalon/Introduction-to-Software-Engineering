package lighting;

import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource {
    private final Point position;
    private Double3 Kc = Double3.ONE;
    private Double3 Kl = Double3.ZERO;
    private Double3 Kq = Double3.ZERO;

    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    @Override
    public Color getIntensity(Point point) {
        Color Ic = getIntensity();
        double distance = point.distance(position);
        double distancesquared = point.distanceSquared(position);

        Double3 factor = (Kc.add(Kl.scale(distance)).add(Kq.scale(distancesquared)));

        return Ic.reduce(factor);
    }

    @Override
    public Vector getL(Point point) {
        return point.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point point) {
        return point.distance(this.position);
    }

    public PointLight setKc(Double3 kc) {
        this.Kc = kc;
        return this;
    }

    public PointLight setKc(double kc) {
        this.Kc = new Double3(kc);
        return this;
    }


    public PointLight setKl(Double3 kl) {
        this.Kl = kl;
        return this;
    }

    public PointLight setKl(double kl) {
        this.Kl = new Double3(kl);
        return this;
    }


    public PointLight setKq(Double3 kq) {
        this.Kq = kq;
        return this;
    }

    public PointLight setKq(double kq) {
        this.Kq = new Double3(kq);
        return this;
    }
}
